package cycling;

import java.io.Serializable;
import java.util.HashMap;


/**
 * Represents a cycling team, including its unique identifier, name, description, and the
 * riders.
 * 
 * @author Olly Johnson and Laith Al Qudah
 * @version 1.0
 */
public class Team implements Serializable {
    /** Unique identifier for the team. */
    private final int id;
    /** Name of the team. */
    private String name;
    /** Collection of riders in this team, mapped by their unique IDs. */
    private HashMap<Integer, Rider> riders;
    /** Description of the team. */
    private String description;

    /**
     * Constructs a Team object with an ID, name, and description.
     *
     * @param id The unique identifier for the team.
     * @param name The name of the team.
     * @param description A description of the team.
     */
    public Team(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.riders = new HashMap<>();
    }

    /**
     * Provides a string representation of the team, including its ID, name, description, and riders.
     *
     * @return A string detailing the team's information.
     */
    @Override
    public String toString(){
        return("Team id = " + id + "name="+name+" description="+description);
    }

    /**
     * Gets the team's ID.
     *
     * @return The ID of the team.
     */
    public int getTeamId(){
        return id;
    }

    /**
     * Gets the team's name.
     *
     * @return The name of the team.
     */
    public String getTeamName(){
        return name;
    }

    /**
     * Retrieves an array of all riders in the team.
     *
     * @return An array of Rider objects representing the team's members.
     */
    public Rider[] getRiders(){
        return riders.values().toArray(new Rider[0]);
    }

    /**
     * Gets the IDs of all riders in the team.
     *
     * @return An array of integers representing the IDs of the team's riders.
     */
    public int [] getRiderIds(){
        int [] riderIds = new int[riders.size()];
        int index = 0;
        for(int riderId: riders.keySet()){
            riderIds[index++] = riderId;
        }
        return riderIds;
    }

    /**
     * Adds a rider to the team.
     *
     * @param riderId The ID of the rider to be added.
     * @param rider The Rider object to be added to the team.
     */
    public void addRider(int riderId, Rider rider) {
        riders.put(riderId, rider);
        assert riders.containsKey(riderId) : "Rider with ID " + riderId + " was not successfully added to the team.";

    }

    /**
     * Removes a rider from the team.
     *
     * @param riderId The ID of the rider to be removed.
     */
    public void removeRider(int riderId){
        riders.remove(riderId);
        assert !riders.containsKey(riderId) : "Rider with ID " + riderId + " was not successfully removed from the team.";

    }

    /**
     * Removes all riders from the team, clearing the riders' list.
     */
    public void removeAllRiders(){
        riders.clear();
    }
}
