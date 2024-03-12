package cycling;

import cycling.Team;
public class Rider {
    private int id;
    private String name;
    private Team team;
    private int yearOfBirth;

    public Rider(int id, String name, int yearOfBirth, Team team) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.team = team;
    }

    public int getRiderId(){
        return id;
    }
    
    public Team getTeam(){
        return team;
    }
}


