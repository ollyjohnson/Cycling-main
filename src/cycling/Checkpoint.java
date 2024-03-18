package cycling;

public class Checkpoint {
    protected int id;
    protected double location;
    protected int stageId;
    protected CheckpointType type;

    // Constructor
    public Checkpoint(int id, double location, int stageId, CheckpointType type) {
        this.id = id;
        this.location = location;
        this.stageId = stageId;
        this.type = type;
    }

    @Override
    public String toString(){
        return("Checkpoint id = " + id + "location="+location+" type="+type+" stage id="+ stageId);
    }
    public int getStageId(){
        return stageId;
    }
    public double getLocation(){
        return location;
    }
    public int getId(){
        return id;
    }
    public CheckpointType getType(){
        return type;
    }
}

