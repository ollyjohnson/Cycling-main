package cycling;

import java.time.LocalDateTime; // For using LocalDateTime.now()
import java.time.LocalTime; // For using LocalTime.of()
import java.util.Arrays;

public class CyclingPortalTest {
    public static void main(String[] args) {
        try{
            CyclingPortalImpl portal = new CyclingPortalImpl();

            // Example of creating a race
            int raceId = portal.createRace("TourdeTest", "A test race");
            System.out.println("Created race with ID: " + raceId);
            
            // Add a stage to the race
            int stageId = portal.addStageToRace(raceId, "Stage1", "First stage", 120.0, LocalDateTime.now(), StageType.FLAT);
            System.out.println("Added stage with ID: " + stageId);

            int[] raceIds = portal.getRaceIds();
            System.out.println("Race ids: " + Arrays.toString(raceIds));

            int[] raceStageIds = portal.getRaceStages(raceId);
            System.out.println("Stage ids: " + Arrays.toString(raceStageIds));

            double stageLength = portal.getStageLength(stageId);
            System.out.println("Stage length: " + stageLength);

            int categorisedClimbId = portal.addCategorizedClimbToStage(stageId, 50.00, CheckpointType.C2, 10.00, 20.00);
            System.out.println("Categorised climb id: " + categorisedClimbId);

            int intermediateSprintId = portal.addIntermediateSprintToStage(stageId, 80.00);
            System.out.println("Categorised climb id: " + intermediateSprintId);

            portal.concludeStagePreparation(stageId);
            System.out.println("stage preparation concluded");

            int[] stageCheckpoints = portal.getStageCheckpoints(stageId);
            System.out.println("Stage checkpooints:" + Arrays.toString(stageCheckpoints));

            // View the race details
            String raceDetails = portal.viewRaceDetails(raceId);
            System.out.println("Race details: " + raceDetails);

            // Add a team
            int teamId = portal.createTeam("TeamTest", "A test team");
            System.out.println("Created team with ID: " + teamId);

            int[] teamIds = portal.getTeams();
            System.out.println("List of team IDs: " + Arrays.toString(teamIds));

            int riderId = portal.createRider(teamId, "Tony", 2000);
            System.out.println("Created rider with ID: " + riderId);

            int[] teamRiders = portal.getTeamRiders(teamId);
            System.out.println("List of rider IDs in team: " + teamId + " riders: " + Arrays.toString(teamRiders));

            // Register rider results for a stage
            LocalTime[] checkpointTimes = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 30),
                LocalTime.of(4, 30),
                LocalTime.of(5, 30)
            };
            portal.registerRiderResultsInStage(stageId, riderId, checkpointTimes);
            System.out.println("Registered rider results in stage" + riderId + " " + stageId);

            LocalTime[] riderResultsInStage = portal.getRiderResultsInStage(stageId, riderId);
            System.out.println("Rider results in stage: " + Arrays.toString(riderResultsInStage));

            LocalTime riderAdjustedElapsedTimeInStage = portal.getRiderAdjustedElapsedTimeInStage(stageId,riderId);
            System.out.println("Rider adjusted elapsed time in stage: " + riderAdjustedElapsedTimeInStage);

            int[] ridersRankInStage = portal.getRidersRankInStage(stageId);
            System.out.println("Riders ranks in stage: " + Arrays.toString(ridersRankInStage));

            LocalTime[] rankedAdjustedElapsedTime = portal.getRankedAdjustedElapsedTimesInStage(stageId);
            System.out.println("Ranked adjusted elapsed time: " + Arrays.toString(rankedAdjustedElapsedTime));

            int[] riderPointsInStage = portal.getRidersPointsInStage(stageId);
            System.out.println("Rider points in stage: " + Arrays.toString(riderPointsInStage));

            int[] riderMountainPointsInStage = portal.getRidersMountainPointsInStage(stageId);
            System.out.println("Rider Mountian points in stage: " + Arrays.toString(riderMountainPointsInStage));

            int[] riderGeneralClassificationRank = portal.getRidersGeneralClassificationRank(raceId);
            System.out.println("Rider general classification rank: " + Arrays.toString(riderGeneralClassificationRank));

            LocalTime[] generalClassificationTimesInRace = portal.getGeneralClassificationTimesInRace(raceId);
            System.out.println("General classfication times in race: " + Arrays.toString(generalClassificationTimesInRace));

            int[] riderPointsInRace = portal.getRidersPointsInRace(raceId);
            System.out.println("Rider points in race: " + Arrays.toString(riderPointsInRace));

            int[] riderMountainPointsInRace = portal.getRidersMountainPointsInRace(raceId);
            System.out.println("Rider Mountain points in race: " + Arrays.toString(riderMountainPointsInRace));

            int[] riderPointClassificationRank = portal.getRidersPointClassificationRank(raceId);
            System.out.println("Rider points classification rank: " + Arrays.toString(riderPointClassificationRank));

            int[] riderMountainPointClassificationRank = portal.getRidersMountainPointClassificationRank(raceId);
            System.out.println("Rider mountain points classification rank: " + Arrays.toString(riderMountainPointClassificationRank));

            // Finally, print a complete report or summary if needed
            System.out.println("Testing completed successfully.");

            // removeRaceById
            // getNumberOfStages
            // removeStageById(int stageId)
            // removeCheckpoint(int checkpointId)
            // removeTeam(int teamId)
            // removeRider(int riderId)
            // deleteRiderResultsInStage(int stageId, int riderId)
            // void eraseCyclingPortal()
            // void saveCyclingPortal(String filename)
            // void loadCyclingPortal(String filename)
            // void removeRaceByName(String name)
        } catch (Exception e) {
            // If any exception is caught, print its stack trace
            e.printStackTrace();
        }
    }
}