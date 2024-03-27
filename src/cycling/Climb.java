package cycling;

/**
 * Represents a climb within a stage of a cycling race. A climb is a type of
 * checkpoint with additional attributes to describe its difficulty, such as
 * average gradient and length.
 * Inherits from the Checkpoint class and adds specific characteristics
 * that define a climb.
 * 
 * @author Olly Johnson and Laith Al Qudah
 * @version 1.0
 */
public class Climb extends Checkpoint {
    private double averageGradient;
    private double length;

    /**
     * Constructs a Climb checkpoint with the specified details.
     * Calls the superclass constructor to set checkpoint attributes,
     * and initializes climb-specific attributes with the provided values.
     *
     * @param checkpointId    The unique identifier for the checkpoint.
     * @param location        The distance from the start of the stage to the checkpoint in kilometers.
     * @param stageId         The identifier of the stage that this checkpoint belongs to.
     * @param type            The type of the checkpoint, which categorizes the climb (e.g., Category 1, HC).
     * @param averageGradient The average gradient of the climb in percentage.
     * @param length          The length of the climb in kilometers.
     */
    public Climb(int checkpointId, double location, int stageId, CheckpointType type, double averageGradient, double length) {
        super(checkpointId, location, stageId, type);
        this.averageGradient = averageGradient;
        this.length = length;
    }

    @Override
    public String toString(){
        return super.toString() + " average gradient=" + averageGradient + " length=" + length;
    }

}
