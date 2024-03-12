package cycling;

import java.util.HashMap;

public class Team {
    private int id;
    private String name;
    private HashMap<Integer, Rider> riders;
    private String description;

    public Team(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.riders = new Rider[0];
    }
    @Override
    public String toString(){
        return("Team id = " + id + "n ame="+name+" description="+description+" riders="+ riders);
    }
    public int getTeamId(){
        return id;
    }
    public String getTeamName(){
        return name;
    }
    public Rider[] getRiders(){
        return riders.values().toArray(new Rider[0]);
    }
    public int [] getRiderIds(){
        int [] riderIds = new int[riders.length];
        int index = 0;
        for(Rider riderId: riders.keySet()){
            riderIds[index++] = riderId;
        }
        return riderIds;
    }

    public void addStage(Rider rider) {
        riders.add(rider);
    }

    public void removeRider(int riderId){
        riders.remove(riderId);
    }
    public void removeAllRiders(){
        riders.clear();
    }
}
