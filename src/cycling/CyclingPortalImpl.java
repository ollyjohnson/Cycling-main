package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InvalidNameException;


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
	// Hash maps to save lists of teams and races.
	private HashMap<Integer, Team> teams = new HashMap<>();
	private HashMap<Integer, Race> races = new HashMap<>();

	// Validates a race name ensuring it is not null, empty, or already used.
	private void validateTeamName(String name) throws IllegalNameException, InvalidNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalNameException("Team name cannot be null or empty.");
        }
		for (Team team : teams.values()) {
			if (team.getTeamName().equals(name)){
				throw new InvalidNameException("Team name already exists.");
			}
		}
    }

	// Validates a race name ensuring it is not null, empty, or already used.
	private void validateRaceName(String name) throws IllegalNameException, InvalidNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalNameException("Team name cannot be null or empty.");
        }
		for (Race race : races.values()) {
			if (race.getRaceName().equals(name)){
				throw new InvalidNameException("Team name already exists.");
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
	public int createRace(String name, String description) {
		int newRaceId = raceIdCounter;
		try {
			validateRaceName(name);
			newRaceId = raceIdCounter++;
			Race newRace = new Race(newRaceId, name, description);
			races.put(newRaceId, newRace);
			return newRaceId;
		} catch (IllegalNameException e) {
			System.err.println("Illegal race name provided: " + e.getMessage());
			return 0;
		} catch (InvalidNameException e) {
			System.err.println("Invalid race name format: " + e.getMessage());
			return 0;
		}
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
		boolean found = false;
		for(Race race: races.values()){
			if(race.getRaceId()==raceId){
				races.remove(race);
				found = true;
			}
		}
		if(!found){
			throw new IDNotRecognisedException("No team found with ID: " + raceId);
		}

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		Race race = races.get(raceId);
		return race.getNumberOfStages();
	}


	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, String stageType)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		// Validate the stage name.
		if (stageName == null || stageName.trim().isEmpty()) {
			throw new IllegalNameException("Stage name cannot be null or empty.");
		}

		// Validate the length of the stage.
		if (length <= 0) {
			throw new InvalidLengthException("Stage length must be positive.");
		}

		// Find the race by ID.
		Race race = races.get(raceId);
		if (race == null) {
			throw new IDNotRecognisedException("No race found with ID: " + raceId);
		}

		int newStageId = stageIdCounter++; //Generate unique stage id

		// Create a new Stage object.
		Stage newStage = new Stage(newStageId, stageName, race, description, length, startTime, stageType);
		race.addStage(newStage);
		// Return the ID of the newly created Stage.
		return newStageId;
	}



	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// Find the race with the specified ID.
		Race race = races.get(raceId);
		// If the race with the given ID is not found, it will throw an exception.
		validateId(getRaceIds(), raceId);
		// Extract and return the stage IDs for this race.
		ArrayList<Stage> stages = race.stages();
		int[] stageIds = new int[stages.size()];
		for (int i = 0; i < stages.size(); i++) {
			stageIds[i] = stages.get(i).getId(); // Assuming Stage has a getId() method.
		}
		return stageIds;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// Iterate through all races.
		for (Race race : races.values()) {
			ArrayList<Stage> stages = race.getStages();
			for (Stage stage : stages) {
				// Check if the current stage's ID matches the given stageId.
				if (stage.getId() == stageId) {
					// If a match is found, return the length of this stage as double.
					return (double) stage.getLength();
				}
			}
		}
		// If no stage with the given ID is found, throw an exception.
		throw new IDNotRecognisedException("No stage found with ID: " + stageId);
	}


	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		boolean found = false;
		// Iterate through all races to find the stage by ID and remove it.
		for (Race race : races.values()) {
			for (Stage stage : race.getStages()) {
				if (stage.getId() == stageId) {
					race.removeStage(stageId); // Remove the stage from the current race.
					found = true;
					break;
				}
			}
			// Break the outer loop if the stage has been found and removed.
			if (found) {
				break;
			}
		}
		if (!found) {
			// If the stage was not found in any race, throw an exception.
			throw new IDNotRecognisedException("No stage found with ID: " + stageId);
		}
	}


	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		validateTeamName(name);
		int newTeamId = teamIdCounter++;
		Team newTeam = new Team(newTeamId, name, description);
		teams.add(newTeam);
		return newTeamId;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		boolean found = false;
		for (Team team : teams) {
			if(team.getTeamId() == teamId){
				teams.remove(team);
				found = true;
			}
		}
		if(!found){
			throw new IDNotRecognisedException("No team found with ID: " + teamId);
		}

	}

	@Override
	public int[] getTeams() {
		int[] teamIds = new int[teams.size()];
		for (int i = 0; i < teams.size(); i++) {
			teamIds[i] = teams.get(i).getTeamId();
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
		else if (yearOfBirth < 1900){
			throw new IllegalArgumentException("Illegal argument, year of birth must be at least 1900: " + yearOfBirth);
		}
		//Validate the team id
		validateId(getTeams(), teamId);
		//Get the team from the map using the id
		Team riderTeam = teams.get(teamId);

		// Create a new rider and add to the team
		int newRiderId = riderIdCounter++;
		Rider newRider = new Rider(newRiderId, name, yearOfBirth, riderTeam);
		riderTeam.addRider(newRider);
		
		// Return the new rider ID
		return newRiderId;
	}
	

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		//loop through each team
		for(Team team : teams.values()){
			//loop through the riders in each team
			Rider [] riders = team.getRiders();
			for(Rider rider : riders){
				//if their rider id's match then remove the rider from the team
				if(rider.getRiderId() == riderId){
					team.removeRider(riderId);
				}
			}
		}

	}
	/**
	 * Record the times of a rider in a stage.
	 * <p>
	 * The state of this MiniCyclingPortal must be unchanged if any
	 * exceptions are thrown.
	 * 
	 * @param stageId     The ID of the stage the result refers to.
	 * @param riderId     The ID of the rider.
	 * @param checkpointTimes An array of times at which the rider reached each of the
	 *                    checkpoints of the stage, including the start time and the
	 *                    finish line.
	 * @throws IDNotRecognisedException    If the ID does not match to any rider or
	 *                                     stage in the system.
	 * @throws DuplicatedResultException   Thrown if the rider has already a result
	 *                                     for the stage. Each rider can have only
	 *                                     one result per stage.
	 * @throws InvalidCheckpointTimesException Thrown if the length of checkpointTimes is
	 *                                     not equal to n+2, where n is the number
	 *                                     of checkpoints in the stage; +2 represents
	 *                                     the start time and the finish time of the
	 *                                     stage.
	 * @throws InvalidStageStateException  Thrown if the stage is not "waiting for
	 *                                     results". Results can only be added to a
	 *                                     stage while it is "waiting for results".
	 */
	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
				validateId(team.getRiders(), riderId);
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}
