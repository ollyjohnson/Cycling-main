package cycling;

public class Climb extends Checkpoint {
    private double averageGradient;
    private double length;

    // Climb constructor
    public Climb(int checkpointId, double location, int stageId, CheckpointType type, double averageGradient, double length) {
        super(checkpointId, location, stageId, type);
        this.averageGradient = averageGradient;
        this.length = length;
    }

}
