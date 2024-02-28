import cycling.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestPortal {

    private static CyclingPortalImpl portal = new CyclingPortalImpl();

    public static void main(String[] args) {
        // Call all the test methods
        testCreateRace();
        testCreateTeam();
        testCreateRider();
        testAddStageToRace();
        // ... add calls to other test methods here
    }

    private static void testCreateRace() {
        try {
            int raceId = portal.createRace("Tour de Test", "Test Description");
            assert raceId >= 0 : "testCreateRace failed - Race ID should be non-negative";
            System.out.println("testCreateRace passed");
        } catch (Exception e) {
            System.err.println("testCreateRace failed: " + e.getMessage());
        }
    }

    private static void testCreateTeam() {
        try {
            int teamId = portal.createTeam("Team Test", "Test Team Description");
            assert teamId >= 0 : "testCreateTeam failed - Team ID should be non-negative";
            System.out.println("testCreateTeam passed");
        } catch (Exception e) {
            System.err.println("testCreateTeam failed: " + e.getMessage());
        }
    }

    private static void testCreateRider() {
        try {
            int teamId = portal.createTeam("Team for Rider", "Test Team for Rider");
            int riderId = portal.createRider(teamId, "Rider Test", 1990);
            assert riderId >= 0 : "testCreateRider failed - Rider ID should be non-negative";
            System.out.println("testCreateRider passed");
        } catch (Exception e) {
            System.err.println("testCreateRider failed: " + e.getMessage());
        }
    }

    private static void testAddStageToRace() {
        try {
            int raceId = portal.createRace("Race for Stage", "Race Description for Stage");
            int stageId = portal.addStageToRace(raceId, "Stage Test", "Stage Description", 120.0, LocalDateTime.now(), StageType.FLAT);
            assert stageId >= 0 : "testAddStageToRace failed - Stage ID should be non-negative";
            System.out.println("testAddStageToRace passed");
        } catch (Exception e) {
            System.err.println("testAddStageToRace failed: " + e.getMessage());
        }
    }

}
