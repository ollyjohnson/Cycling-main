package cycling;


import javax.naming.InvalidNameException;


class Test {
    private CyclingPortalImpl portal;

    @BeforeEach
    void setUp() {
        portal = new CyclingPortalImpl();
    }

    @Test
    void testCreateRaceWithValidName() throws IllegalNameException, InvalidNameException {
        String raceName = "Tour de France";
        String description = "An annual men's multiple stage bicycle race primarily held in France";
        int raceId = portal.createRace(raceName, description);
        assertTrue(raceId > 0, "Race ID should be positive indicating successful creation");
    }

    @Test
    void testCreateRaceWithNullName() {
        String raceName = null;
        String description = "Description for null name race";
        assertThrows(IllegalNameException.class, () -> portal.createRace(raceName, description), "Should throw IllegalNameException for null race name");
    }

    @Test
    void testCreateRaceWithEmptyName() {
        String raceName = " ";
        String description = "Description for empty name race";
        assertThrows(IllegalNameException.class, () -> portal.createRace(raceName, description), "Should throw IllegalNameException for empty race name");
    }

    @Test
    void testCreateRaceWithDuplicateName() throws IllegalNameException, InvalidNameException {
        String raceName = "Giro d'Italia";
        String description = "An annual multiple-stage bicycle race primarily held in Italy";
        portal.createRace(raceName, description); // First creation should succeed
        // Attempt to create a race with the same name should fail
        assertThrows(InvalidNameException.class, () -> portal.createRace(raceName, description), "Should throw InvalidNameException for duplicate race name");
    }
}
