package cycling;

import java.time.LocalDate;
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
            int stageId = portal.addStageToRace(raceId, "Stage1", "First stage", 120.0, LocalDateTime.of(LocalDate.of(2024, 03, 23), LocalTime.of(02, 30)), StageType.FLAT);
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
            System.out.println("Sprint id: " + intermediateSprintId);

            portal.concludeStagePreparation(stageId);
            System.out.println("stage preparation concluded");

            int[] stageCheckpoints = portal.getStageCheckpoints(stageId);
            System.out.println("Stage checkpooints:" + Arrays.toString(stageCheckpoints));

            // View the race details
            String raceDetails = portal.viewRaceDetails(raceId);
            System.out.println("Race details: " + raceDetails);

            // Add a stage to the race
            int stageId2 = portal.addStageToRace(raceId, "Stage2", "Second stage", 70.0, LocalDateTime.of(LocalDate.of(2024, 03, 24), LocalTime.of(03, 00)), StageType.HIGH_MOUNTAIN);
            System.out.println("Added stage with ID: " + stageId2);

            int[] raceIds2 = portal.getRaceIds();
            System.out.println("Race ids: " + Arrays.toString(raceIds2));

            int[] raceStageIds2 = portal.getRaceStages(raceId);
            System.out.println("Stage ids: " + Arrays.toString(raceStageIds2));

            double stageLength2 = portal.getStageLength(stageId2);
            System.out.println("Stage length: " + stageLength2);

            int categorisedClimbId2 = portal.addCategorizedClimbToStage(stageId2, 20.00, CheckpointType.C3, 15.00, 20.00);
            System.out.println("Categorised climb id: " + categorisedClimbId2);

            int categorisedClimbId3 = portal.addCategorizedClimbToStage(stageId2, 40.00, CheckpointType.C1, 5.00, 20.00);
            System.out.println("Categorised climb id: " + categorisedClimbId3);

            int categorisedClimbId4 = portal.addCategorizedClimbToStage(stageId2, 60.00, CheckpointType.HC, 20.00, 10.00);
            System.out.println("Categorised climb id: " + categorisedClimbId4);

            portal.concludeStagePreparation(stageId);
            System.out.println("stage preparation concluded");

            int[] stageCheckpoints2 = portal.getStageCheckpoints(stageId2);
            System.out.println("Stage checkpooints:" + Arrays.toString(stageCheckpoints2));

            // Add a team
            int teamId = portal.createTeam("TeamTest", "A test team");
            System.out.println("Created team with ID: " + teamId);

            int riderId = portal.createRider(teamId, "Tony", 2000);
            System.out.println("Created rider with ID: " + riderId);

            int riderId2 = portal.createRider(teamId, "Phil", 2000);
            System.out.println("Created rider with ID: " + riderId2);

            int[] teamRiders = portal.getTeamRiders(teamId);
            System.out.println("List of rider IDs in team: " + teamId + " riders: " + Arrays.toString(teamRiders));

            // Add a team
            int teamId2 = portal.createTeam("TeamBlue", "2nd test team");
            System.out.println("Created team with ID: " + teamId2);

            int riderId3 = portal.createRider(teamId2, "Burt", 2004);
            System.out.println("Created rider with ID: " + riderId3);
            
            int[] teamIds = portal.getTeams();
            System.out.println("List of team IDs: " + Arrays.toString(teamIds));

            // Register rider results for a stage
            LocalTime[] checkpointTimes = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 30),
                LocalTime.of(4, 30),
                LocalTime.of(5, 30)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes2 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 15),
                LocalTime.of(4, 45),
                LocalTime.of(5, 45)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes3 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 31),
                LocalTime.of(4, 20),
                LocalTime.of(5, 30,00,04)
            };

            portal.registerRiderResultsInStage(stageId, riderId, checkpointTimes);
            System.out.println("Registered rider " + riderId + "results in stage " + stageId);
            
            portal.registerRiderResultsInStage(stageId, riderId2, checkpointTimes2);
            System.out.println("Registered rider " + riderId2 + "results in stage " + stageId);

            portal.registerRiderResultsInStage(stageId, riderId3, checkpointTimes3);
            System.out.println("Registered rider " + riderId3 + "results in stage " + stageId);

            // Register rider results for a stage
            LocalTime[] checkpointTimes4 = {
                LocalTime.of(3, 00),
                LocalTime.of(3, 45),
                LocalTime.of(4, 12),
                LocalTime.of(4, 51),
                LocalTime.of(5, 00,05,58)
                
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes5 = {
                LocalTime.of(3, 15),
                LocalTime.of(4, 2),
                LocalTime.of(4, 27),
                LocalTime.of(5, 9),
                LocalTime.of(5, 15,05,24)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes6 = {
                LocalTime.of(3, 30),
                LocalTime.of(4, 11),
                LocalTime.of(4, 49),
                LocalTime.of(5, 24),
                LocalTime.of(5, 33,06,18)
            };

            portal.registerRiderResultsInStage(stageId2, riderId, checkpointTimes4);
            System.out.println("Registered rider " + riderId + "results in stage " + stageId2);
            
            portal.registerRiderResultsInStage(stageId2, riderId2, checkpointTimes5);
            System.out.println("Registered rider " + riderId2 + "results in stage " + stageId2);

            portal.registerRiderResultsInStage(stageId2, riderId3, checkpointTimes6);
            System.out.println("Registered rider " + riderId3 + "results in stage " + stageId2);

            LocalTime[] riderResultsInStage = portal.getRiderResultsInStage(stageId, riderId);
            System.out.println("Rider results in stage: " + Arrays.toString(riderResultsInStage));

            LocalTime[] riderResultsInStage2 = portal.getRiderResultsInStage(stageId2, riderId);
            System.out.println("Rider results in stage 2: " + Arrays.toString(riderResultsInStage2));

            LocalTime riderAdjustedElapsedTimeInStage = portal.getRiderAdjustedElapsedTimeInStage(stageId,riderId);
            System.out.println("Rider adjusted elapsed time in stage: " + riderAdjustedElapsedTimeInStage);

            LocalTime riderAdjustedElapsedTimeInStage2 = portal.getRiderAdjustedElapsedTimeInStage(stageId,riderId2);
            System.out.println("Rider adjusted elapsed time in stage: " + riderAdjustedElapsedTimeInStage2);
            
            LocalTime riderAdjustedElapsedTimeInStage3 = portal.getRiderAdjustedElapsedTimeInStage(stageId,riderId3);
            System.out.println("Rider adjusted elapsed time in stage: " + riderAdjustedElapsedTimeInStage3);

            LocalTime riderAdjustedElapsedTimeInStage4 = portal.getRiderAdjustedElapsedTimeInStage(stageId2,riderId);
            System.out.println("Rider adjusted elapsed time in stage 2: " + riderAdjustedElapsedTimeInStage4);

            LocalTime riderAdjustedElapsedTimeInStage5 = portal.getRiderAdjustedElapsedTimeInStage(stageId2,riderId2);
            System.out.println("Rider adjusted elapsed time in stage 2: " + riderAdjustedElapsedTimeInStage5);
            
            LocalTime riderAdjustedElapsedTimeInStage6 = portal.getRiderAdjustedElapsedTimeInStage(stageId2,riderId3);
            System.out.println("Rider adjusted elapsed time in stage 2: " + riderAdjustedElapsedTimeInStage6);

            int[] ridersRankInStage = portal.getRidersRankInStage(stageId);
            System.out.println("Riders ranks in stage: " + Arrays.toString(ridersRankInStage));

            int[] ridersRankInStage2 = portal.getRidersRankInStage(stageId2);
            System.out.println("Riders ranks in stage 2: " + Arrays.toString(ridersRankInStage2));

            LocalTime[] rankedAdjustedElapsedTime = portal.getRankedAdjustedElapsedTimesInStage(stageId);
            System.out.println("Ranked adjusted elapsed time: " + Arrays.toString(rankedAdjustedElapsedTime));

            LocalTime[] rankedAdjustedElapsedTime2 = portal.getRankedAdjustedElapsedTimesInStage(stageId2);
            System.out.println("Ranked adjusted elapsed time stage 2: " + Arrays.toString(rankedAdjustedElapsedTime2));

            int[] riderPointsInStage = portal.getRidersPointsInStage(stageId);
            System.out.println("Rider points in stage: " + Arrays.toString(riderPointsInStage));

            int[] riderMountainPointsInStage = portal.getRidersMountainPointsInStage(stageId);
            System.out.println("Rider Mountian points in stage: " + Arrays.toString(riderMountainPointsInStage));

            int[] riderPointsInStage2 = portal.getRidersPointsInStage(stageId2);
            System.out.println("Rider points in stage 2: " + Arrays.toString(riderPointsInStage2));

            int[] riderMountainPointsInStage2 = portal.getRidersMountainPointsInStage(stageId2);
            System.out.println("Rider Mountian points in stage 2: " + Arrays.toString(riderMountainPointsInStage2));

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