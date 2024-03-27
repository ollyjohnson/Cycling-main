package cycling;

import java.io.IOException;
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

            portal.concludeStagePreparation(stageId2);
            System.out.println("stage 2 preparation concluded");

            int[] stageCheckpoints2 = portal.getStageCheckpoints(stageId2);
            System.out.println("Stage checkpooints:" + Arrays.toString(stageCheckpoints2));

            int noOfStages = portal.getNumberOfStages(raceId);
            System.out.println("No. of stages:" + noOfStages);

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

            int teamId5 = portal.createTeam("TeamYellow", "5 test team");
            System.out.println("Created team with ID: " + teamId5);

            int riderId7 = portal.createRider(teamId5, "Ernie", 2004);
            System.out.println("Created rider with ID: " + riderId7);

            int riderId8 = portal.createRider(teamId5, "Will", 2004);
            System.out.println("Created rider with ID: " + riderId8);

            int riderId9 = portal.createRider(teamId5, "Simon", 2004);
            System.out.println("Created rider with ID: " + riderId9);

            int teamId6 = portal.createTeam("TeamPurple", "6 test team");
            System.out.println("Created team with ID: " + teamId6);

            int riderId10 = portal.createRider(teamId6, "Phil", 2004);
            System.out.println("Created rider with ID: " + riderId10);

            int riderId11 = portal.createRider(teamId6, "Wolfie", 2004);
            System.out.println("Created rider with ID: " + riderId11);

            int riderId12 = portal.createRider(teamId6, "Jay", 2004);
            System.out.println("Created rider with ID: " + riderId12);

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

            // Register rider results for a stage
            LocalTime[] checkpointTimes13 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 32),
                LocalTime.of(4, 43),
                LocalTime.of(5, 30,00,15)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes14 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 33),
                LocalTime.of(4, 44),
                LocalTime.of(5, 31,00,15)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes15 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 34),
                LocalTime.of(4, 44,01),
                LocalTime.of(5, 32,00,15)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes10 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 35),
                LocalTime.of(4, 44,10),
                LocalTime.of(5, 33,00,15)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes11 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 36),
                LocalTime.of(4, 44,20),
                LocalTime.of(5, 34,00,15)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes12 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 37),
                LocalTime.of(4, 44,30),
                LocalTime.of(5, 35,00,15)
            };

            portal.registerRiderResultsInStage(stageId, riderId, checkpointTimes);
            System.out.println("Registered rider " + riderId + "results in stage " + stageId);
            
            portal.registerRiderResultsInStage(stageId, riderId2, checkpointTimes2);
            System.out.println("Registered rider " + riderId2 + "results in stage " + stageId);

            portal.registerRiderResultsInStage(stageId, riderId3, checkpointTimes3);
            System.out.println("Registered rider " + riderId3 + "results in stage " + stageId);

            portal.registerRiderResultsInStage(stageId, riderId7, checkpointTimes13);
            System.out.println("Registered rider " + riderId7 + "results in stage " + stageId);
            
            portal.registerRiderResultsInStage(stageId, riderId8, checkpointTimes14);
            System.out.println("Registered rider " + riderId8 + "results in stage " + stageId);

            portal.registerRiderResultsInStage(stageId, riderId9, checkpointTimes15);
            System.out.println("Registered rider " + riderId9 + "results in stage " + stageId);

            portal.registerRiderResultsInStage(stageId, riderId10, checkpointTimes10);
            System.out.println("Registered rider " + riderId10 + "results in stage " + stageId);
            
            portal.registerRiderResultsInStage(stageId, riderId11, checkpointTimes11);
            System.out.println("Registered rider " + riderId11 + "results in stage " + stageId);

            portal.registerRiderResultsInStage(stageId, riderId12, checkpointTimes12);
            System.out.println("Registered rider " + riderId12 + "results in stage " + stageId);

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
                LocalTime.of(3, 00),
                LocalTime.of(3, 47),
                LocalTime.of(4, 9),
                LocalTime.of(4, 53),
                LocalTime.of(5, 00,05,24)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes6 = {
                LocalTime.of(3, 00),
                LocalTime.of(3, 41),
                LocalTime.of(4, 8),
                LocalTime.of(4, 56),
                LocalTime.of(5, 03,06,18)
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
            System.out.println("Ranked adjusted elapsed time stage1: " + Arrays.toString(rankedAdjustedElapsedTime));

            LocalTime[] rankedAdjustedElapsedTime2 = portal.getRankedAdjustedElapsedTimesInStage(stageId2);
            System.out.println("Ranked adjusted elapsed time stage 2: " + Arrays.toString(rankedAdjustedElapsedTime2));

            int[] riderPointsInStage = portal.getRidersPointsInStage(stageId);
            System.out.println("Rider points in stage 1: " + Arrays.toString(riderPointsInStage));

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

            // Example of creating a race
            int raceId2 = portal.createRace("DeletedeTest", "A test race");
            System.out.println("Created race with ID: " + raceId2);
            
            int stageId3 = portal.addStageToRace(raceId2, "Stage3", "3 stage", 120.0, LocalDateTime.of(LocalDate.of(2024, 03, 23), LocalTime.of(02, 30)), StageType.FLAT);
            System.out.println("Added stage with ID: " + stageId3);

            int categorisedClimbId5 = portal.addCategorizedClimbToStage(stageId3, 50.00, CheckpointType.C2, 10.00, 20.00);
            System.out.println("Categorised climb id: " + categorisedClimbId5);

            int intermediateSprintId2 = portal.addIntermediateSprintToStage(stageId3, 80.00);
            System.out.println("Sprint id: " + intermediateSprintId2);

            int[] stageCheckpoints3 = portal.getStageCheckpoints(stageId3);
            System.out.println("Stage checkpoints before removal: " + Arrays.toString(stageCheckpoints3));

            portal.removeCheckpoint(intermediateSprintId2);
            System.out.println("Removed checkpoint " + intermediateSprintId2);

            int[] stageCheckpoints4 = portal.getStageCheckpoints(stageId3);
            System.out.println("Stage checkpoints after removal: " + Arrays.toString(stageCheckpoints4));

            portal.concludeStagePreparation(stageId3);
            System.out.println("stage preparation 3 concluded");

            int teamId3 = portal.createTeam("TeamDelete", "3 test team");
            System.out.println("Created team with ID: " + teamId3);

            int riderId4 = portal.createRider(teamId3, "Alan", 2000);
            System.out.println("Created rider with ID: " + riderId4);

            int riderId5 = portal.createRider(teamId3, "Stu", 2000);
            
            System.out.println("Created rider with ID: " + riderId5);

            int[] teamRiders2 = portal.getTeamRiders(teamId3);
            System.out.println("List of rider IDs in team: " + teamId3 + " riders: " + Arrays.toString(teamRiders2));

            // Add a team
            int teamId4 = portal.createTeam("TeamRed", "4 test team");
            System.out.println("Created team with ID: " + teamId4);

            int riderId6 = portal.createRider(teamId4, "LESLIE", 2004);
            System.out.println("Created rider with ID: " + riderId6);
            
            int[] teamIds2 = portal.getTeams();
            System.out.println("List of team IDs: " + Arrays.toString(teamIds2));

            // Register rider results for a stage
            LocalTime[] checkpointTimes7 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 30),
                LocalTime.of(4, 30),
                LocalTime.of(5, 30)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes8 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 15),
                LocalTime.of(4, 45),
                LocalTime.of(5, 45)
            };

            // Register rider results for a stage
            LocalTime[] checkpointTimes9 = {
                LocalTime.of(2, 30),
                LocalTime.of(3, 31),
                LocalTime.of(4, 20),
                LocalTime.of(5, 30,00,04)
            };

            portal.registerRiderResultsInStage(stageId3, riderId4, checkpointTimes7);
            System.out.println("Registered rider " + riderId4 + "results in stage " + stageId3);
            
            portal.registerRiderResultsInStage(stageId3, riderId5, checkpointTimes8);
            System.out.println("Registered rider " + riderId5 + "results in stage " + stageId3);

            portal.registerRiderResultsInStage(stageId3, riderId6, checkpointTimes9);
            System.out.println("Registered rider " + riderId6 + "results in stage " + stageId3);

            portal.removeStageById(stageId3);
            System.out.println("Removed stage " + stageId3);

            int[] raceStageIds3 = portal.getRaceStages(raceId2);
            System.out.println("Race stages after removal: " + Arrays.toString(raceStageIds3));

            int[] riderResultsInStage3 = portal.getRidersGeneralClassificationRank(raceId2);
            System.out.println("Riders general classification rank in race (should return an empty array as there are no results): " + Arrays.toString(riderResultsInStage3));

            portal.removeRaceById(raceId2);
            System.out.println("Removed race " + raceId2);

            int[] raceIds3 = portal.getRaceIds();
            System.out.println("Races after removal: " + Arrays.toString(raceIds3));

            portal.removeRider(riderId2);
            System.out.println("Removed rider " + riderId2);

            int[] teamIds4 = portal.getTeamRiders(teamId);
            System.out.println("Riders in team after removal: " + Arrays.toString(teamIds4));

            int[] riderResultsInRace = portal.getRidersGeneralClassificationRank(raceId);
            System.out.println("Rider ranks in race after rider 2 was removed: " + Arrays.toString(riderResultsInRace));

            portal.deleteRiderResultsInStage(stageId2, riderId3);
            System.out.println("Rider " + riderId3 + " results deleted in stage " + stageId2);

            int[] ridersRankInStage3 = portal.getRidersRankInStage(stageId2);
            System.out.println("Riders ranks in stage 2 after delete: " + Arrays.toString(ridersRankInStage3));

            portal.removeTeam(teamId2);
            System.out.println("Team deleted " + teamId2);

            int[] teamsInPortal = portal.getTeams();
            System.out.println("Teams after delete: " + Arrays.toString(teamsInPortal));

            portal.removeStageById(stageId2);
            System.out.println("Removed stage " + stageId2);
            
            int[] riderResultsInRace2 = portal.getRidersGeneralClassificationRank(raceId);
            System.out.println("Rider ranks in race after stage 2 was removed: " + Arrays.toString(riderResultsInRace2));

            int [] riderPointsInRace3 = portal.getRidersPointsInRace(raceId);
            System.out.println("Rider points in race after stage 2 was removed: " + Arrays.toString(riderPointsInRace3));

            int[] ridersRankInStage4 = portal.getRidersRankInStage(stageId);
            System.out.println("Riders ranks in stage 1 after team delete (should no longer have rider 3): " + Arrays.toString(ridersRankInStage4));

            // Example of creating a race
            int raceId3 = portal.createRace("DeletedeTest2", "A test race");
            System.out.println("Created race with ID: " + raceId3);

            int [] raceIds4 = portal.getRaceIds();
            System.out.println("Races in system: " + Arrays.toString(raceIds4));

            portal.removeRaceByName("DeletedeTest2");
            System.out.println("Removed race with name: DeletedeTest2");

            int [] raceIds5 = portal.getRaceIds();
            System.out.println("Races in system: " + Arrays.toString(raceIds5));

            int[] teamsInPortal2 = portal.getTeams();
            System.out.println("Teams in System: " + Arrays.toString(teamsInPortal2));

            // Define a filename to save the portal state
            String filename = "cycling_portal_data.ser";

            // Save the current state of the portal to a file
            try {
                portal.saveCyclingPortal(filename);
                System.out.println("Cycling portal data saved to " + filename);
            } catch (IOException ex) {
                System.err.println("Failed to save cycling portal data: " + ex.getMessage());
                ex.printStackTrace();
            }

            // Erase all data from the cycling portal
            portal.eraseCyclingPortal();
            System.out.println("Cycling portal data erased.");

            // Verify that all data has been erased
            int[] racesAfterErase = portal.getRaceIds();
            System.out.println("Races in system after erase: " + Arrays.toString(racesAfterErase)); // Should be empty
            int[] teamsAfterErase = portal.getTeams();
            System.out.println("Teams in system after erase: " + Arrays.toString(teamsAfterErase)); // Should be empty

            // Load the portal state from the saved file
            try {
                portal.loadCyclingPortal(filename);
                System.out.println("Cycling portal data loaded from " + filename);
                
                // Verify that the data has been restored
                int[] racesAfterLoad = portal.getRaceIds();
                System.out.println("Races in system after load: " + Arrays.toString(racesAfterLoad)); // Should show the races before erase
                int[] teamsInPortal3 = portal.getTeams();
            System.out.println("Teams in System after load: " + Arrays.toString(teamsInPortal3));
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Failed to load cycling portal data: " + ex.getMessage());
            }
            // Finally, print a complete report or summary if needed
            System.out.println("Testing completed successfully.");

            // void eraseCyclingPortal()
            // void saveCyclingPortal(String filename)
            // void loadCyclingPortal(String filename)

        } catch (Exception e) {
            // If any exception is caught, print its stack trace
            e.printStackTrace();
        }

        
    }
}
