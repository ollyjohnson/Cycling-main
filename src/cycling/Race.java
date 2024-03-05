package cycling;

import java.util.ArrayList;

public class Race {
    private int id;
    private String name;
    private String description;
    public ArrayList<Stage> stages;

    public Race(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stages = new ArrayList<>();
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
        for(Stage stage : stages){
            totalLength += stage.getLength();
            stageNames[index++] = stage.getStageName();
        }

        String details = """
        Race ID = %d
        Race name = %s
        Race description = %s
        Stages in race = %s
        Length of race = %.2f
        """.formatted(id, name, description, String.join("\n", stageNames), totalLength);
        return details;
    }

    public void addStage(Stage stage) {
        this.stages.add(stage);
    }

    public void removeStage(int stageId) {
        // add error handling
        stages.removeIf(stage -> stage.getStageId() == stageId);
    }

    public ArrayList<Stage> stages() {
        return null;
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public void setStages(ArrayList<Stage> stages) {
        this.stages = stages;
    }

    public int getNumberOfStages(){
        return stages.size();
    }

}




