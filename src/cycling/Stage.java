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
    private boolean waitingForResults;

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
    public void setWaitingForResults() {
        this.waitingForResults = true;
    }
    public boolean isStageWaitingForResults(){
        return waitingForResults;
    }
    public Race getRace(){
        return race;
    }
    public void removeCheckpointFromStage(int checkpointId) {
        this.checkpoints.remove(checkpointId);
    }
    public int[] getCheckpointIds(){
        int[] checkpointIds = new int [checkpoints.size()];
        int index = 0;
        for(int id: checkpoints.keys()){
            checkpointIds[index++] = id;
        }
        return checkpointIds;
    }

    public HashMap<Integer, Checkpoint> getCheckpoints(){
        return checkpoints;
    }


}

