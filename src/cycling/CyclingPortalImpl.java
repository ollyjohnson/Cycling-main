package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.io.*;


/**
 * Implementor of the CyclingPortal interface.
 *
 * @author Olly Johnson Laith Al-Qudah
 * @version 1.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {
	// Counter variables to generate unique IDs for teams, riders, stages and races.
	private int teamIdCounter = 1;
	private int riderIdCounter = 1;
	private int raceIdCounter = 1;
	private int stageIdCounter = 1;
	private int checkpointIdCounter = 1;
	// Hash maps to save lists of teams and races.
	private HashMap<Integer, Team> teams = new HashMap<>();
	private HashMap<Integer, Race> races = new HashMap<>();

	// Validates a race name ensuring it is not null, empty, or already used.
	private void validateTeamName(String name) throws IllegalNameException, InvalidNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Team name cannot be null or empty.");
        }
		if (name.length() > 30){
			throw new InvalidNameException("Team name cannot be greater than 30 characters");
		}
		if (name.matches(".*\\s.*")) {
			throw new InvalidNameException("Team name cannot contain whitespace.");
		}
		for (Team team : teams.values()) {
			if (team.getTeamName().equals(name)){
				throw new IllegalNameException("Team name already exists.");
			}
		}
    }

	// Validates a race name ensuring it is not null, empty, or already used.
	private void validateRaceName(String name) throws IllegalNameException, InvalidNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Race name cannot be null or empty.");
        }
		if (name.length() > 30){
			throw new InvalidNameException("Race name cannot be greater than 30 characters");
		}
		if (name.matches(".*\\s.*")) {
			throw new InvalidNameException("Race name cannot contain whitespace.");
		}
		for (Race race : races.values()) {
			if (race.getRaceName().equals(name)){
				throw new IllegalNameException("Race name already exists.");
			}
		}
    }
	
	// Validates a race Id, ensuring it exists in the given array
	private void validateId(int[] idArray, int id) throws IDNotRecognisedException {
		boolean idFound = false;
		for (int currentId : idArray) {
			if (currentId == id) {
				idFound = true;
				break;
			}
		}
		if (!idFound) {
			throw new IDNotRecognisedException("No entry found with ID: " + id);
		}
	}

	// Returns an array of all race IDs.
	@Override
	public int[] getRaceIds() {
		int[] raceIds = new int[races.size()];
		int index = 0;
		for (Integer key : races.keySet()) {
			raceIds[index++] = key;
		}
		return raceIds;
	}

	//  * Creates a new race with the given name and description, and adds it to the list of races.
	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		validateRaceName(name);
		int newRaceId = raceIdCounter++;
		Race newRace = new Race(newRaceId, name, description);
		races.put(newRaceId, newRace);
		return newRaceId;
	}

    //Provides details of a race identified by its ID.
	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		Race race = races.get(raceId);
		if (race != null) {
			return race.getRaceDetails();
		} else {
			throw new IDNotRecognisedException("No race found with ID: " + raceId);
		}
	}

	// Removes a race by its ID.
	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		validateId(getRaceIds(), raceId);
		races.remove(raceId);
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		Race race = races.get(raceId);
		return race.getNumberOfStages();
	}


	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type) 
		throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		// Validate the stage name.
		if (stageName == null || stageName.trim().isEmpty()) {
            throw new InvalidNameException("Stage name cannot be null or empty.");
        }
		if (stageName.length() > 30){
			throw new InvalidNameException("Stage name cannot be greater than 30 characters");
		}
		if (stageName.matches(".*\\s.*")) {
			throw new InvalidNameException("Stage name cannot contain whitespace.");
		}
		for (Race race : races.values()) {
			for (Stage stage : race.getStages()){
				if (stage.getStageName().equals(stageName)){
					throw new IllegalNameException("Stage name already exists.");
				}
			}
		}
		// Validate the length of the stage.
		if (length < 5) {
			throw new InvalidLengthException("Stage length must be at least 5km.");
		}
		//Validate the raceId
		validateId(getRaceIds(), raceId);
		// Find the race by ID.
		Race race = races.get(raceId);
		int newStageId = stageIdCounter++; //Generate unique stage id
		// Create a new Stage object.
		Stage newStage = new Stage(newStageId, stageName, race, description, length, startTime, type);
		race.addStage(newStageId, newStage);
		// Return the ID of the newly created Stage.
		return newStageId;
	}

	public Stage findStageById(int stageId) throws IDNotRecognisedException {
		//Looop through the races and within each race loop through the stage until we find one that matches the id
		for (Race race : races.values()) {
			Stage[] stages = race.getStages();
			for (Stage stage : stages) {
				if (stage.getStageId() == stageId) {
					//returns the stage object
					return stage;
				}
			}
		}
		
		// If no stage with the given stageId was found in any race, throw an exception
		throw new IDNotRecognisedException("The stage ID " + stageId + " was not recognised in any race.");
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// If the race with the given ID is not found, it will throw an exception.
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		// Extract and return the stage IDs for this race.
		return race.getStageIds();
	}

	public double getStageLength(int stageId) throws IDNotRecognisedException {
		Stage stage = findStageById(stageId);
    	return stage.getLength();
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		Stage stage = findStageById(stageId);
		Race race = stage.getRace();
		race.removeStage(stageId);
	}


	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		Stage stage = findStageById(stageId);
		Race race = stage.getRace();
		validateId(getRaceStages(race.getRaceId()), stageId);

		if (!stage.isValidLocation(location)) {
			throw new InvalidLocationException("Location is out of stage bounds.");
		}
	
		if (stage.getStageType() == StageType.TT) {
			throw new InvalidStageTypeException("Climbs cannot be added to a time-trial stage.");
		}

		if (stage.isStageWaitingForResults()) {
			throw new InvalidStageStateException("Cannot modify stage in this state.");
		}
	
		int checkpointId = checkpointIdCounter++;
		Climb climb = new Climb(checkpointId, location, stageId, type, averageGradient, length);
	
		stage.addCheckpointToStage(checkpointId, climb);
	
		return checkpointId;

		
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		Stage stage = findStageById(stageId);
		Race race = stage.getRace();
		validateId(getRaceStages(race.getRaceId()), stageId);

		if (!stage.isValidLocation(location)) {
			throw new InvalidLocationException("Location is out of stage bounds.");
		}
	
		if (stage.getStageType() == StageType.TT) {
			throw new InvalidStageTypeException("Sprints cannot be added to a time-trial stage.");
		}

		if (stage.isStageWaitingForResults()) {
			throw new InvalidStageStateException("Cannot modify stage in this state.");
		}
		CheckpointType type = CheckpointType.SPRINT;
		int checkpointId = checkpointIdCounter++;
		Checkpoint sprint = new Checkpoint(checkpointId, location, stageId, type);
	
		stage.addCheckpointToStage(checkpointId, sprint);
	
		return checkpointId;
	
		
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		boolean checkpointFound = false;
	
		for (Race race : races.values()) {
			for (Stage stage : race.getStages()) {
				if (stage.getCheckpoints().containsKey(checkpointId)) {
					if (stage.isStageWaitingForResults()) {
						throw new InvalidStageStateException("Cannot modify stage in this state.");
					}
					stage.removeCheckpointFromStage(checkpointId);
					checkpointFound = true;
					break;
				}
			}
			if (checkpointFound) {
				break;
			}
		}
	
		if (!checkpointFound) {
			throw new IDNotRecognisedException("Checkpoint ID not recognized: " + checkpointId);
		}
	}
	

	@Override
	//maybe make StageState an enum
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		Stage stage = findStageById(stageId);
		if(stage.isStageWaitingForResults()){
			throw new InvalidStageStateException("Cannot modify stage in this state");
		}
		stage.setWaitingForResults();

	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		Stage stage = findStageById(stageId);	
		return stage.getOrderedCheckpointIds();
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		validateTeamName(name);
		int newTeamId = teamIdCounter++;
		Team newTeam = new Team(newTeamId, name, description);
		teams.put(newTeamId, newTeam);
		return newTeamId;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		//check if the id is in the teams hash map
		if (teams.containsKey(teamId)) {
			//if it is remove all riders from the team and then remove it from the hash map
			Team team = teams.get(teamId);
			team.removeAllRiders();
			teams.remove(teamId);
		} else {
			//If not then throw the exception
			throw new IDNotRecognisedException("No team found with ID: " + teamId);
		}
}

	@Override
	public int[] getTeams() {
    int[] teamIds = new int[teams.size()];
    int index = 0;
    for (Team team : teams.values()) {
        teamIds[index++] = team.getTeamId();
    }
    return teamIds;
}
	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		validateId(getTeams(), teamId);
		Team team = teams.get(teamId);
		return team.getRiderIds();
		}


	@Override
	public int createRider(int teamId, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		//Validate name
		if (name == null){
			throw new IllegalArgumentException("Illegal argument, name cannot be null: " + name);
		}
		//Validate birth year
		if (yearOfBirth < 1900){
			throw new IllegalArgumentException("Illegal argument, year of birth must be at least 1900: " + yearOfBirth);
		}
		//Validate the team id
		validateId(getTeams(), teamId);
		//Get the team from the map using the id
		Team riderTeam = teams.get(teamId);

		// Create a new rider and add to the team
		int newRiderId = riderIdCounter++;
		Rider newRider = new Rider(newRiderId, name, yearOfBirth, riderTeam);
		riderTeam.addRider(newRiderId, newRider);
		
		// Return the new rider ID
		return newRiderId;
	}
	
	public Rider findRiderById(int riderId) throws IDNotRecognisedException{
		//loop through each team
		for(Team team : teams.values()){
			//loop through the riders in each team
			Rider [] riders = team.getRiders();
			for(Rider rider : riders){
				//if their rider id's match then remove the rider from the team
				if(rider.getRiderId() == riderId){
					return rider;
				}
			}
		}
		// If no rider with the given riderId was found in any race, throw an exception
		throw new IDNotRecognisedException("The rider ID " + riderId + " was not recognised in any team.");

	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		Rider rider = findRiderById(riderId);
		Team team = rider.getTeam();
		team.removeRider(riderId);
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		//find the rider stage and race and carry out validations
		Stage stage = findStageById(stageId);
		Race race = stage.getRace();
		Rider rider = findRiderById(riderId);
		Result raceResult;
		if(race.riderHasResult(riderId)){
			raceResult = race.getOverallResult(riderId);
		}
		else{
			raceResult = new Result(riderId);
		}
		StageResult stageResult = new StageResult(riderId, checkpoints);
		stage.addStageResult(riderId, stageResult);
		raceResult.addStageResult(stageId, stageResult);
		race.addOverallResult(riderId, raceResult);
	 }

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//find the rider and stage and carry out validations
		Rider rider = findRiderById(riderId);
		Stage stage = findStageById(stageId);
		LocalTime [] results  = stage.getRiderResults(riderId);
		return results;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//find the rider and stage and carry out validations
		Rider rider = findRiderById(riderId);
		Stage stage = findStageById(stageId);
		//this will updated the elapsed times if needed
		stage.adjustRiderElapsedTimes();
		LocalTime adjustedElapsedTimeInStage = stage.getRiderAdjustedElapsedTime(riderId);
		return adjustedElapsedTimeInStage;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//find the rider and stage and carry out validations
		Rider rider = findRiderById(riderId);
		Stage stage = findStageById(stageId);
		stage.removeRiderResults(riderId);
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		//find the stage and carry out validations
		Stage stage = findStageById(stageId);
		return stage.getRiderRanks();
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		//find the stage and carry out validations
		Stage stage = findStageById(stageId);
		return stage.getRankedAdjustedElapsedTimes();
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		//find the stage and carry out validations
		Stage stage = findStageById(stageId);
		return stage.getOrderedPoints();
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		Stage stage = findStageById(stageId); // Validate and retrieve the stage.
		return stage.getOrderedMountainPoints();
	}


	@Override
	public void eraseCyclingPortal() {
		// Reset all collections
		teams.clear();
		races.clear();

		// Reset all ID counters to their initial values.
		teamIdCounter = 1;
		riderIdCounter = 1;
		raceIdCounter = 1;
		stageIdCounter = 1;
		checkpointIdCounter = 1;
	}


	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(this);
		} catch (IOException e) {
			throw e;
		}
	}


	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			CyclingPortalImpl loadedPortal = (CyclingPortalImpl) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw e;
		}
	}


	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		//Boolean to track if a race with the given name has been found
		boolean raceFound = false;
		for(Race race:races.values()){
			if(race.getRaceName().equals(name)){
				races.remove(race.getRaceId());
				raceFound = true;
			}
		}
		if (!raceFound) {
			throw new NameNotRecognisedException("No race found with name: " + name);
		}
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException{
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getRiderIdsByTotalTime();
	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getSortedListOfTimes();
	}


	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getTotalPoints();
	}


	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getMountainPoints();
	}


	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getRiderIdsByPoints();
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getRiderIdsByMountianPoints();
	}

}
