package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.naming.InvalidNameException;


/**
 * Implementor of the CyclingPortal interface.
 * 
 * @author Olly Johnson Laith Al-Qudah
 * @version 1.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {
	// Counter variables to generate unique IDs for teams, riders, and races.
	private int teamIdCounter = 1;
	private int riderIdCounter = 1;
	private int raceIdCounter = 1;
	// Lists to store the teams and races.
	private ArrayList<Team> teams = new ArrayList<>();
	private ArrayList<Race> races = new ArrayList<>();

	// Validates a team name ensuring it is not null, empty, or already used.
	private void validateTeamName(String name) throws IllegalNameException, InvalidNameException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalNameException("Team name cannot be null or empty.");
        }
		for (Team team : teams) {
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
		for (Race race : races) {
			if (race.getRaceName().equals(name)){
				throw new InvalidNameException("Team name already exists.");
			}
		}
    }

	// Returns an array of all race IDs.
	@Override
	public int[] getRaceIds() {
		int[] raceIds = new int[races.size()];
		for (int i = 0; i < races.size(); i++) {
			raceIds[i] = races.get(i).getRaceId();
		}
		return raceIds;
	}

	//  * Creates a new race with the given name and description, and adds it to the list of races.
	@Override
	public int createRace(String name, String description) {
		int newRaceId = raceIdCounter;
		try {
			validateRaceName(name);
			raceIdCounter++;
			Race newRace = new Race(newRaceId, name, description);
			races.add(newRace);
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
		for(Race race:races){
			if(race.getRaceId() == raceId){
				return race.toString();
			}
		}
		throw new IDNotRecognisedException("No race found with ID: " + raceId);
	}

	// Removes a race by its ID.
	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		boolean found = false;
		for(Race race: races){
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
		// TODO Auto-generated method stub
		return 0;
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

		// Convert length from double to int, assuming length should be an integer value as per Stage class definition.
		int intLength = (int) Math.round(length);

		// Find the race by ID.
		Race targetRace = null;
		for (Race race : races) {
			if (race.getRaceId() == raceId) {
				targetRace = race;
				break;
			}
		}
		if (targetRace == null) {
			throw new IDNotRecognisedException("No race found with ID: " + raceId);
		}

		int newStageId = generateNewStageId(); // Implement this method to generate unique stage IDs.

		// Create a new Stage object.
		Stage newStage = new Stage(newStageId, stageName, targetRace, description, intLength, startTime, stageType);

		targetRace.addStage(newStage);

		// Return the ID of the newly created Stage.
		return newStage.getId();
	}

	private int stageIdCounter = 1;

	// Utility method to generate and return a new unique stage ID.
	private int generateNewStageId() {
		return stageIdCounter++; // Increment and return the stage ID counter.
	}



	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

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
		for (Team team : teams) {
			if (team.getTeamId() == teamId) {
				Rider[] riders = team.getRiders();
				int[] riderIds = new int[riders.length];
				for (int i = 0; i < riders.length; i++) {
					riderIds[i] = riders[i].getRiderId();
				}
				return riderIds;
			}
		}
		throw new IDNotRecognisedException("No team found with ID: " + teamId);
			
	}


	@Override
	public int createRider(int teamId, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		if (name.equals(null)){
			throw new IllegalArgumentException("Illegal argument, name cannot be null: " + name);
		}
		else if (yearOfBirth < 1900){
			throw new IllegalArgumentException("Illegal argument, year of birth must be at least 1900: " + yearOfBirth);
		}
		Team riderTeam = null;
		int newRiderId = riderIdCounter++;
		for (Team team : teams) {
			if (team.getTeamId() == teamId) {
				riderTeam = team;
				break;
			}
		}
		
		if (riderTeam != null) {
			Rider newRider = new Rider(newRiderId, name, yearOfBirth, riderTeam);
			riderTeam.addRider(newRider);
		} else {
			throw new IDNotRecognisedException("No team found with ID: " + teamId);
		}
				
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		for(Team team : teams){
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

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
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
