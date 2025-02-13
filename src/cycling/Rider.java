package cycling;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a rider in a cycling team, including their identification, name,
 * year of birth, and the team they belong to.
 * 
 *@author Olly Johnson and Laith Al Qudah
 *@version 1.0
 */
public class Rider implements Serializable {
    /** Unique identifier for the rider */
    private final int id;
    /** Name of the rider */
    private String name;
    /** The team to which this rider belongs */
    private Team team;
    /** Year of birth of the rider */
    private int yearOfBirth;
    /** List of races in which the rider is participating */
    private ArrayList<Race> races = new ArrayList<>();

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
     * Returns a string representation of the Rider, including the id,
     * name, year of birth and team they belong to.
     * 
     * @return A string containing the results details.
     */
    @Override
    public String toString(){
        return("Rider id: " + id + " name: " + name + " year of birth: " + yearOfBirth + " team: " + team.getTeamName());
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

    /**
     * Adds a race that the rider will be racing in to the list of all the races the rider will  be competing in.
     *
     * @param race The race the rider will be racing.
     */
    public void addRace(Race race){
        races.add(race);
    }

    /**
     * Removes a race that the rider will no longer be racing in from the list of all the races the rider will  be competing in.
     *
     * @param race The race the rider will no longer be racing.
     */
    public void removeRace(Race race){
        races.remove(race);
    }

    /**
     * A check to see if a rider is competing in a specified race
     *
     * @param race The race in question.
     * @return true if the rider is competing in the race, false otherwise.
     */
    public boolean ridersInRace(Race race){
        return races.contains(race);
    }

    /**
     * Gets a list of all the races a rider is competing in.
     *
     * @return The array list of the races.
     */
    public ArrayList<Race> getRiderRaces(){
        return races;
    }



}


