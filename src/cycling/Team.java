package cycling;

import java.util.HashMap;

public class Team {
    private int id;
    private String name;
    private HashMap<Integer, Rider> riders;
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
        this.riders = new HashMap<Integer, Rider>();
    }

    /**
     * Provides a string representation of the team, including its ID, name, description, and riders.
     *
     * @return A string detailing the team's information.
     */
    @Override
    public String toString(){
        return("Team id = " + id + "n ame="+name+" description="+description+" riders="+ riders);
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
    }

    /**
     * Removes a rider from the team.
     *
     * @param riderId The ID of the rider to be removed.
     */
    public void removeRider(int riderId){
        riders.remove(riderId);
    }

    /**
     * Removes all riders from the team, clearing the riders' list.
     */
    public void removeAllRiders(){
        riders.clear();
    }
}
