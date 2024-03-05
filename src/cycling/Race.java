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
        // Assuming stages and race_length are intended to be part of this class, declare and initialize them properly.
        // For demonstration, removing the reference to undeclared variables.
        return("Team id = " + id + " name="+name+" description="+description);
    }

    public int getRaceId() {
        return id;
    }

    public String getRaceName() {
        return name;
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
}




