package cycling;

import cycling.Rider;

public class Team {
    private int id;
    private String name;
    private Rider[] riders;
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
    public Rider []getRiders(){
        return riders;
    }
    public int [] getRiderIds(){
        int [] riderIds = new int[riders.length];
        index = 0;
        for(Rider rider: riders){
            ridersIds[index++] = rider.getId();
        }
        return riderIds;
    }
    public void addRider(Rider rider) {
        //change to array list?
        Rider[] newRiders = new Rider[riders.length + 1];
        System.arraycopy(riders, 0, newRiders, 0, riders.length);
        newRiders[riders.length] = rider;
        riders = newRiders;
    }

    public void removeRider(int riderId){
        //add error hadnling exception
        for (int i = 0; i < riders.length; i++) {
            if (riders[i].getRiderId() == riderId) {
                Rider[] newRiders = new Rider[riders.length - 1];
                System.arraycopy(riders, 0, newRiders, 0, i);
                System.arraycopy(riders, i + 1, newRiders, i, riders.length - i - 1);
                riders = newRiders;
                return;
            }
        }
    }
}
