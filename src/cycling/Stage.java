package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Stage {
    private int id;
    private String name;
    private Race race;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType stageType;
    private Map<Integer, Checkpoint> checkpoints = new HashMap<>();
    private boolean waitingForResults;
    //a HashMap containing rider ids as keys which stores another hash map containing checkpoint ids and results
    private Map<Integer, Map<Integer, Results>> riderResults = new HashMap<>();

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
    public int[] getOrderedCheckpointIds() {
        int[] checkpointIds = new int[checkpoints.size()];
        int index = 0;
        //creates an array of the checkpoint ids
        for (Integer checkpointId : checkpoints.keys()) {
            checkpointIds[index++] = checkpointId;
        }
        //orders the array by the location
        for (int i = 0; i < checkpointIds.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < checkpointIds.length; j++) {
                if (checkpoints.get(checkpointIds[j]).getLocation() < checkpoints.get(checkpointIds[minIndex]).getLocation()) {
                    minIndex = j;
                }
            }
            //if the location that is stored in i is greater than the location of the checkpoint in j then swap them around
            if (minIndex != i) {
                int temp = checkpointIds[i];
                checkpointIds[i] = checkpointIds[minIndex];
                checkpointIds[minIndex] = temp;
            }
        }
        return checkpointIds;
    }

    public HashMap<Integer, Checkpoint> getCheckpoints(){
        return checkpoints;
    }

    public boolean riderHasResult(int riderId){
        return(riderResults.containsKey(riderId));
    }

    public void recordRiderCheckpointTimes(int riderId, LocalTime[] checkpoints){
        Results results = new Results(checkpoints);
        Map<Integer, LocalTime> timesMap = new HashMap<>();
        int[] checkpointIds = getOrderedCheckpointIds();
        for (int i = 0; i < checkpointIds.length; i++) {
            timesMap.put(checkpointIds[i], checkpointTimes[i]);
        }

    }


}

