package cycling;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Stage {
    private int id;
    private String name;
    private Race race;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType stageType;
    private HashMap<Integer, Checkpoint> checkpoints = new HashMap<>();
    private boolean developmentComplete;

    public Stage(int id, String name, Race race, String description, int length, LocalDateTime startTime, StageType stageType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.race = race;
        this.length = length;
        this.startTime = startTime;
        this.stageType = stageType;
    }
    @Override
    public String toString(){
        return("Stage id = " + id + "name="+name+" description="+description+" race="+ race+" length="+ length+ " start time=" + startTime+" stage type="+stageType);
    }
    public int getStageId(){
        return id;
    }
    public String getStageName(){
        return name;
    }
    public double getLength(){
        return length;
    }
    public void addCheckpointToStage(int checkpointId, Checkpoint checkpoint) {
        this.checkpoints.put(checkpointId, checkpoint);
    }
    public boolean isValidLocation(Double location){
        return location > 0 && location <= length;
    }
    public void setDevelopmentComplete() {
        this.developmentComplete = true;
    }
    public boolean isStageDevelopmentComplete(){
        return developmentComplete;
    }
    public Race getRace(){
        return race;
    }



}

