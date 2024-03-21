package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;

public class Race implements Serializable {
    private int id;
    private String name;
    private String description;
    private HashMap<Integer, Stage> stages;
    private HashMap<Integer, Result> riderResults = new HashMap<>();

    public Race(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stages = new HashMap<>();
    }

    @Override
    public String toString() {
        return("Race id = " + id + " name="+name+" description="+description+" stages=" + stages);
    }

    public int getRaceId() {
        return id;
    }

    public String getRaceName() {
        return name;
    }

    public String getRaceDetails() {
        String[] stageNames = new String[stages.size()];
        double totalLength = 0;
        int index = 0;
        for(Stage stage : stages.values()){
            totalLength += stage.getLength();
            stageNames[index++] = stage.getStageName();
        }

        String details = """
        Race ID = %d
        Race name = %s
        Race description = %s
        Stages in race = %d
        Length of race = %.2f
        """.formatted(id, name, description, String.join("\n", stageNames), totalLength);
        return details;
    }

    public void addStage(int stageId, Stage stage) {
        stages.put(stageId, stage);
    }

    public void removeStage(int stageId){
        stages.remove(stageId);
    }

    public int getNumberOfStages(){
        return stages.size();
    }

    public int [] getStageIds(){
        int [] stageIds = new int[stages.length];
        int index = 0;
        for(Stage stageId : stages.keySet()){
            stageIds[index++] = stageId;
        }
        return stageIds;
    }

    public Stage [] getStages(){
        return stages.values().toArray(new Stage[0]);
    }

    public boolean riderHasResult(int riderId){
        return(riderResults.containsKey(riderId));
    }


    public Result getOverallResult(int riderId){
        return riderResults.get(riderId);
    }

    public Result addOverallResult(int riderId , Result raceResult){
        return riderResults.put(riderId, raceResult);
    }

}




