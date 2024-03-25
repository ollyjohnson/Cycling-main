package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
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

	/**
	 * This helper method checks if the provided team name is valid.
	 * It ensures that the name is not null, not empty, does not exceed 30 characters,
	 * does not contain any whitespace, and is not already in use by another team.
	 *
	 * @param name The team name to be validated.
	 * @throws IllegalNameException If the team name already exists within the system.
	 * @throws InvalidNameException If the team name is null, empty, too long, or contains whitespace.
	 */
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

	/**
	 * This helper method verifies the validity of a race name.
	 * It checks for the name being non-null, non-empty, within a 30-character limit,
	 * free of whitespace, and ensures it is not already assigned to an existing race.
	 *
	 * @param name The race name to be validated.
	 * @throws IllegalNameException If the race name already exists within the system.
	 * @throws InvalidNameException If the race name is null, empty, exceeds the character limit, or contains whitespace.
	 */
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

	/**
	 * This helper method validates the existence of an ID within a provided array of IDs.
	 * It throws an exception if the ID is not found, indicating it is not recognized within the system.
	 *
	 * @param idArray An array of IDs to be searched.
	 * @param id The ID to be validated against the array.
	 * @throws IDNotRecognisedException If the ID is not present in the provided array.
	 */
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

	/**
	 * This implementation retrieves all race IDs currently stored in the system.
	 * It compiles these IDs into an array and returns it.
	 *
	 * @return An array of all the race IDs.
	 */
	@Override
	public int[] getRaceIds() {
		int[] raceIds = new int[races.size()];
		int index = 0;
		for (Integer key : races.keySet()) {
			raceIds[index++] = key;
		}
		return raceIds;
	}

	/**
	 * This implementation creates a new race with the specified name and description.
	 * Upon successful creation, it assigns a unique ID to the race and stores it within the system.
	 *
	 * @param name The name of the new race.
	 * @param description A brief description of the race.
	 * @return The unique ID assigned to the newly created race.
	 * @throws IllegalNameException If the race name provided is invalid, such as being empty or containing illegal characters.
	 * @throws InvalidNameException If the race name already exists within the system.
	 */
	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		validateRaceName(name);
		int newRaceId = raceIdCounter++;
		Race newRace = new Race(newRaceId, name, description);
		races.put(newRaceId, newRace);
		return newRaceId;
	}

	/**
	 * This implementation provides detailed information about a race identified by its ID.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return A string containing the details of the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the system.
	 */
	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		Race race = races.get(raceId);
		if (race != null) {
			return race.getRaceDetails();
		} else {
			throw new IDNotRecognisedException("No race found with ID: " + raceId);
		}
	}

	/**
	 * This implementation removes a race from the system using its ID.
	 *
	 * @param raceId The ID of the race to be removed.
	 * @throws IDNotRecognisedException If the ID does not match any race in the system.
	 */
	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		validateId(getRaceIds(), raceId);
		races.remove(raceId);
	}

	/**
	 * This implementation counts the number of stages in a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return The number of stages in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the system.
	 */
	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		Race race = races.get(raceId);
		return race.getNumberOfStages();
	}

	/**
	 * This implementation adds a new stage to an existing race.
	 * It requires a stage name, description, length, start time, and type. A unique ID is generated for the new stage.
	 *
	 * @param raceId The ID of the race to which the stage will be added.
	 * @param stageName The name of the stage.
	 * @param description A description of the stage.
	 * @param length The length of the stage in kilometers.
	 * @param startTime The start time of the stage.
	 * @param stageType The type of the stage (e.g., flat, mountain).
	 * @return The unique ID of the newly added stage.
	 * @throws IDNotRecognisedException If the race ID does not correspond to any existing race.
	 * @throws IllegalNameException If the stage name provided is invalid.
	 * @throws InvalidNameException If the stage name is not unique within the race.
	 * @throws InvalidLengthException If the length provided for the stage is non-positive.
	 */
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

	/**
	 * This helper method locates a stage by its ID across all races.
	 *
	 * @param stageId The ID of the stage to find.
	 * @return The stage object if found.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 */
	public Stage findStageById(int stageId) throws IDNotRecognisedException {
		for (Race race : races.values()) {
			Stage[] stages = race.getStages();
			for (Stage stage : stages) {
				if (stage.getStageId() == stageId) {
					return stage;
				}
			}
		}
		
		// If no stage with the given stageId was found in any race, throw an exception
		throw new IDNotRecognisedException("The stage ID " + stageId + " was not recognised in any race.");
	}

	/**
	 * This implementation retrieves all the stage IDs associated with a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return An array of stage IDs for the specified race.
	 * @throws IDNotRecognisedException If the race ID does not match any race in the system.
	 */
	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// If the race with the given ID is not found, it will throw an exception.
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		// Extract and return the stage IDs for this race.
		return race.getStageIds();
	}

	/**
	 * This implementation retrieves the length of a specific stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return The length of the stage in kilometers.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 */
	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		Stage stage = findStageById(stageId);
    	return stage.getLength();
	}

	/**
	 * This implementation removes a stage from a race by its ID.
	 *
	 * @param stageId The ID of the stage to be removed.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 */
	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		Stage stage = findStageById(stageId);
		Race race = stage.getRace();
		race.removeStage(stageId);
	}

	/**
	 * This implementation adds a categorized climb checkpoint to a stage.
	 *
	 * @param stageId The ID of the stage where the climb is added.
	 * @param location The location of the climb within the stage.
	 * @param type The type of climb.
	 * @param averageGradient The average gradient of the climb.
	 * @param length The length of the climb.
	 * @return The ID of the newly added climb checkpoint.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 * @throws InvalidLocationException If the location is not valid for the stage.
	 * @throws InvalidStageStateException If the stage is not in the correct state to add a checkpoint.
	 * @throws InvalidStageTypeException If the stage type does not allow for climbs.
	 */
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

	/**
	 * This implementation adds an intermediate sprint checkpoint to a stage.
	 *
	 * @param stageId The ID of the stage where the sprint is added.
	 * @param location The location of the sprint within the stage.
	 * @return The ID of the newly added sprint checkpoint.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 * @throws InvalidLocationException If the location is out of stage bounds.
	 * @throws InvalidStageStateException If the stage is not in a state to add checkpoints.
	 * @throws InvalidStageTypeException If the stage type is not appropriate for sprints.
	 */
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

	/**
	 * This implementation removes a checkpoint from a stage.
	 *
	 * @param checkpointId The ID of the checkpoint to be removed.
	 * @throws IDNotRecognisedException If the checkpoint ID is not recognised.
	 * @throws InvalidStageStateException If the stage is not in a correct state to remove a checkpoint.
	 */
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

	/**
	 * This implementation finalizes the preparation of a stage by setting it to a state where it awaits results.
	 *
	 * @param stageId The ID of the stage to conclude preparation for.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 * @throws InvalidStageStateException
	 */
	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		Stage stage = findStageById(stageId);
		if(stage.isStageWaitingForResults()){
			throw new InvalidStageStateException("Cannot modify stage in this state");
		}
		stage.setWaitingForResults();
		assert stage.isStageWaitingForResults() : "Stage state was not changed to waiting for results";

	}

	/**
	 * This implementation fetches the checkpoint IDs for a given stage in their order of occurrence within the stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return An array of checkpoint IDs for the specified stage.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 */
	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		//find a validate the stage id
		Stage stage = findStageById(stageId);	
		return stage.getOrderedCheckpointIds();
	}

	/**
	 * This implementation creates a new team with the provided name and description and assigns it a unique ID.
	 *
	 * @param name The name of the new team.
	 * @param description The description of the new team.
	 * @return The unique ID assigned to the newly created team.
	 * @throws IllegalNameException If the team name is invalid or violates any naming rules.
	 * @throws InvalidNameException If the team name already exists.
	 */
	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		validateTeamName(name);
		int newTeamId = teamIdCounter++;
		assert !teams.containsKey(newTeamId) : "Team with new team id " + newTeamId + " already exists";
		Team newTeam = new Team(newTeamId, name, description);
		teams.put(newTeamId, newTeam);
		return newTeamId;
	}

	/**
	 * This implementation removes a team from the system using its ID, along with all associated riders.
	 *
	 * @param teamId The ID of the team to be removed.
	 * @throws IDNotRecognisedException If the team ID does not match any team in the system.
	 */
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

	/**
	 * This implementation retrieves all team IDs currently registered in the system.
	 *
	 * @return An array containing all the team IDs.
	 */
	@Override
	public int[] getTeams() {
    int[] teamIds = new int[teams.size()];
    int index = 0;
    for (Team team : teams.values()) {
        teamIds[index++] = team.getTeamId();
    }
    return teamIds;
}
	/**
	 * This implementation obtains a list of rider IDs associated with a specific team.
	 *
	 * @param teamId The ID of the team whose riders are being queried.
	 * @return An array of rider IDs belonging to the specified team.
	 * @throws IDNotRecognisedException If the team ID does not correspond to an existing team.
	 */
	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		validateId(getTeams(), teamId);
		Team team = teams.get(teamId);
		return team.getRiderIds();
		}

	/**
	 * This implementation adds a new rider to a team within the system.
	 *
	 * @param teamId The ID of the team to which the rider will be added.
	 * @param name The name of the rider.
	 * @param yearOfBirth The year of birth of the rider.
	 * @return The unique ID generated for the newly added rider.
	 * @throws IDNotRecognisedException If the team ID does not match any existing team.
	 * @throws IllegalArgumentException If the provided name is null or the year of birth is earlier than 1900.
	 */
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

	/**
	 * This helper method searches for a rider by their ID across all teams.
	 *
	 * @param riderId The ID of the rider to find.
	 * @return The rider object if they are found within any team.
	 * @throws IDNotRecognisedException If no rider with the given ID is found in any team.
	 */
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

	/**
	 * This implementation removes a rider from a team and the system.
	 *
	 * @param riderId The ID of the rider to be removed.
	 * @throws IDNotRecognisedException If the rider ID does not match any rider in the system.
	 */
	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		Rider rider = findRiderById(riderId);
		Team team = rider.getTeam();
		team.removeRider(riderId);
	}

	/**
	 * This implementation registers the checkpoint times for a rider in a given stage.
	 * It updates the stage's results with the times provided, which include both checkpoint
	 * times and the total elapsed time for the rider in this particular stage.
	 *
	 * @param stageId The ID of the stage to which the results should be added.
	 * @param riderId The ID of the rider whose results are being recorded.
	 * @param checkpoints An array of LocalTime objects representing the rider's times at each checkpoint.
	 * @throws IDNotRecognisedException If the stage or rider ID does not exist within the system.
	 * @throws DuplicatedResultException If a result for the given rider and stage already exists.
	 * @throws InvalidCheckpointTimesException If the number of times provided does not match the number of checkpoints in the stage.
	 * @throws InvalidStageStateException If the stage is not in a state that allows result registration.
	 */
	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
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
		if(!rider.ridersInRace(race.getRaceId())){
			rider.addRaceId(race.getRaceId());
		}
	 }

	/**
	 * This implementation retrieves the times a rider recorded at each checkpoint in a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @param riderId The ID of the rider whose results are being requested.
	 * @return An array of LocalTime objects representing the times the rider passed each checkpoint.
	 * @throws IDNotRecognisedException If the stage ID or rider ID does not match any stage or rider in the system.
	 */
	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//find the rider and stage and carry out validations
		Rider rider = findRiderById(riderId);
		Stage stage = findStageById(stageId);
		LocalTime [] results  = stage.getRiderResults(riderId);
		return results;
	}

	/**
	 * This implementation calculates a rider's adjusted elapsed time in a stage, accounting for various factors.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @param riderId The ID of the rider.
	 * @return The adjusted elapsed time of the rider in the stage.
	 * @throws IDNotRecognisedException If the stage ID or rider ID does not match any stage or rider in the system.
	 */
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

	/**
	 * This implementation deletes the results of a rider in a stage.
	 *
	 * @param stageId The ID of the stage from which results should be removed.
	 * @param riderId The ID of the rider whose results are to be deleted.
	 * @throws IDNotRecognisedException If the stage ID or rider ID does not match any stage or rider in the system.
	 */
	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//find the rider and stage and carry out validations
		Rider rider = findRiderById(riderId);
		Stage stage = findStageById(stageId);
		stage.removeRiderResults(riderId);
	}

	/**
	 * This implementation ranks riders based on their results in a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return An array of rider IDs ranked according to their performance in the stage.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 */
	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		//find the stage and carry out validations
		Stage stage = findStageById(stageId);
		return stage.getRiderRanks();
	}

	/**
	 * This implementation provides the ranked adjusted elapsed times for all riders in a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return An array of LocalTime objects representing the ranked adjusted elapsed times.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 */
	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		//find the stage and carry out validations
		Stage stage = findStageById(stageId);
		return stage.getRankedAdjustedElapsedTimes();
	}

	/**
	 * This implementation lists the points scored by riders in a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return An array of points corresponding to the riders' achievements in the stage.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 */
	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		//find the stage and carry out validations
		Stage stage = findStageById(stageId);
		return stage.getOrderedPoints();
	}

	/**
	 * This implementation lists the mountain points scored by riders in a stage.
	 *
	 * @param stageId The ID of the stage being queried.
	 * @return An array of mountain points corresponding to the riders' achievements in the stage.
	 * @throws IDNotRecognisedException If the stage ID does not match any stage in the system.
	 */
	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		Stage stage = findStageById(stageId); // Validate and retrieve the stage.
		return stage.getOrderedMountainPoints();
	}

	/**
	 * This implementation resets the cycling portal, clearing all stored data.
	 * All teams, races, riders, and checkpoints are removed, and ID counters are reset.
	 */
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

	/**
	 * This implementation saves the current state of the cycling portal to a file.
	 * The portal's entire state is serialized and written to the specified file.
	 *
	 * @param filename The path and name of the file where data will be saved.
	 * @throws IOException If an I/O error occurs during writing to the file.
	 */
	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(this);
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * This implementation loads the state of the cycling portal from a file.
	 * The state previously saved to the file is deserialized and used to populate the portal.
	 *
	 * @param filename The path and name of the file from which to load the data.
	 * @throws IOException If an I/O error occurs during reading from the file.
	 * @throws ClassNotFoundException If the class of the serialized object cannot be found.
	 */
	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			CyclingPortalImpl loadedPortal = (CyclingPortalImpl) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw e;
		}
	}

	/**
	 * This implementation removes a race and all related data, including stages and results, from the system based on the race name.
	 * If the race name is found, the race and its associated data are completely deleted.
	 *
	 * @param name The name of the race to be removed.
	 * @throws NameNotRecognisedException If no race with the given name exists within the system.
	 */
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

	/**
	 * This implementation determines the general classification rank of riders in a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return An array of rider IDs in the order of their general classification.
	 * @throws IDNotRecognisedException If the race ID does not match any existing race.
	 */
	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException{
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getRiderIdsByTotalTime();
	}

	/**
	 * This implementation retrieves the general classification times for a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return An array of LocalTime objects, sorted according to general classification.
	 * @throws IDNotRecognisedException If the race ID does not match any existing race.
	 */
	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getSortedListOfTimes();
	}

	/**
	 * This implementation compiles the total points accumulated by riders in a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return An array of points corresponding to each rider's total.
	 * @throws IDNotRecognisedException If the race ID does not match any existing race.
	 */
	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getTotalPoints();
	}

	/**
	 * This implementation compiles the total mountain points earned by riders in a race.
	 *
	 * @param raceId The ID of the race being queried.
	 * @return An array of mountain points corresponding to each rider's total.
	 * @throws IDNotRecognisedException If the race ID does not match any existing race.
	 */
	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getMountainPoints();
	}

	/**
	 * This implementation retrieves the ranking of riders within a race based on the points classification.
	 * It considers the points each rider has accumulated throughout the race and orders them accordingly.
	 *
	 * @param raceId The ID of the race for which the points classification is to be determined.
	 * @return An array of rider IDs ranked according to their total points in the race.
	 * @throws IDNotRecognisedException If the race ID does not correspond to any existing race.
	 */
	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getRiderIdsByPoints();
	}

	/**
	 * This implementation fetches the mountain points classification for riders within a race.
	 * Riders are ranked based on the total mountain points they have collected across all stages.
	 *
	 * @param raceId The ID of the race for which the mountain points classification is to be determined.
	 * @return An array of rider IDs ranked by their mountain points total.
	 * @throws IDNotRecognisedException If the race ID does not match any existing race.
	 */
	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		//validate and retrieve race
		validateId(getRaceIds(), raceId);
		Race race = races.get(raceId);
		return race.getRiderIdsByMountianPoints();
	}

}
