package cycling;

import java.io.Serializable;
/**
 * Checkpoint class that represents a checkpoint within a stage of a race.
 * Each checkpoint is identified by an ID, has a specific location measured
 * in kilometers from the start of the stage, is associated with a stage,
 * and has a type that categorises the checkpoint.
 * 
 * @author Olly Johnson and Laith Al Qudah
 * @version 1.0
 */
public class Checkpoint implements Serializable {
    /** The unique identifier for this checkpoint */
    protected int id;
    
    /** The location of the checkpoint along the stage route in kilometers */
    protected double location;
    
    /** The ID of the stage that this checkpoint is part of */
    protected int stageId;
    
    /** The type of checkpoint it is (e.g. Sprint) */
    protected CheckpointType type;

    /**
     * Constructs a new Checkpoint with the specified details.
     *
     * @param id        The unique identifier for the checkpoint.
     * @param location  The distance from the start of the stage to the checkpoint in kilometers.
     * @param stageId   The identifier of the stage that this checkpoint belongs to.
     * @param type      The type of the checkpoint.
     */
    public Checkpoint(int id, double location, int stageId, CheckpointType type) {
        this.id = id;
        this.location = location;
        this.stageId = stageId;
        this.type = type;
    }

    /**
     * Returns a string representation of the Checkpoint, including its ID,
     * location, type, and associated stage ID.
     *
     * @return A string containing the checkpoint's details.
     */
    @Override
    public String toString(){
        return("Checkpoint id = " + id + "location="+location+" type="+type+" stage id="+ stageId);
    }

    /**
     * Gets the location of the checkpoint.
     *
     * @return The distance in kilometers from the start of the stage to the checkpoint.
     */
    public double getLocation(){
        return location;
    }

    /**
     * Gets the unique identifier of the checkpoint.
     *
     * @return The checkpoint ID.
     */
    public int getId(){
        return id;
    }

    /**
     * Gets the type of the checkpoint.
     *
     * @return The checkpoint type.
     */
    public CheckpointType getType(){
        return type;
    }
}

