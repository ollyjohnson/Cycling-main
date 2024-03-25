package cycling;

import java.io.Serializable;

/**
 * Represents a rider in a cycling team, including their identification, name,
 * year of birth, and the team they belong to.
 */
public class Rider implements Serializable {
    private int id;
    private String name;
    private Team team;
    private int yearOfBirth;

    /**
     * Constructs a new Rider instance with the specified details.
     *
     * @param id The unique identifier for the rider.
     * @param name The name of the rider.
     * @param yearOfBirth The year the rider was born.
     * @param team The team to which the rider belongs.
     */
    public Rider(int id, String name, int yearOfBirth, Team team) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.team = team;
    }

    /**
     * Gets the unique identifier of the rider.
     *
     * @return The unique ID of the rider.
     */
    public int getRiderId(){
        return id;
    }

    /**
     * Gets the team to which the rider belongs.
     *
     * @return The team of the rider.
     */
    public Team getTeam(){
        return team;
    }
}


