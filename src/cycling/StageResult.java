package cycling;

import java.time.LocalTime;
import java.io.Serializable;

/**
 * Represents the result of a rider in a specific stage of a race.
 */
public class StageResult implements Comparable <StageResult>, Serializable {
    private LocalTime [] checkpointTimes;
    private LocalTime elapsedTime = LocalTime.MIN;
    private LocalTime adjustedElapsedTime = LocalTime.MIN;
    private LocalTime [] adjustedCheckpointTimes;
    private int riderId;
    private int rank = 0;
    private int points = 0;
    private int mountainPoints = 0;
    private int sprintPoints = 0;

    /**
     * Constructs a StageResult for a rider with times recorded at various checkpoints.
     *
     * @param riderId The ID of the rider.
     * @param checkpointTimes An array of LocalTime objects representing the times at each checkpoint.
     */
    public StageResult(int riderId, LocalTime[] checkpointTimes){
        this.riderId = riderId;
        this.checkpointTimes = checkpointTimes;
        this.adjustedCheckpointTimes = checkpointTimes;
        this.elapsedTime = calculateElapsedTime(checkpointTimes);
    }

    /**
     * Calculates the total elapsed time from the start to the end checkpoint.
     *
     * @return The total elapsed time as a LocalTime object.
     */
    public LocalTime calculateElapsedTime() {
        LocalTime start = checkpointTimes[0];
        LocalTime end = checkpointTimes[checkpointTimes.length - 1];
        long startNanos = start.toNanoOfDay();
        long endNanos = end.toNanoOfDay();
        long elapsedNanos = endNanos - startNanos;
        elapsedTime = LocalTime.ofNanoOfDay(elapsedNanos);
        return elapsedTime;
    }

    /**
     * Gets the total elapsed time.
     *
     * @return The total elapsed time as a LocalTime object.
     */
    public LocalTime getElapsedTime(){
        return elapsedTime;
    }

    /**
     * Compares this StageResult with another based on elapsed time.
     *
     * @param other The other StageResult to compare against.
     * @return A negative integer, zero, or a positive integer as this object
     *         is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(StageResult other) {
        return this.elapsedTime.compareTo(other.elapsedTime);
    }


    /**
     * Sets the adjusted elapsed time for the rider in this stage.
     *
     * @param adjustedElapsedTime The adjusted elapsed time to set.
     */
    public void setAdjustedElapsedTime(LocalTime adjustedElapsedTime) {
        this.adjustedElapsedTime = adjustedElapsedTime;
    }

    /**
     * Gets the adjusted elapsed time for the rider in this stage.
     *
     * @return The adjusted elapsed time.
     */
    public LocalTime getAdjustedElapsedTime() {
        return adjustedElapsedTime;
    }

    /**
     * Gets the ID of the rider associated with this stage result.
     *
     * @return The rider's ID.
     */
    public int getRiderId(){
        return riderId;
    }

    /**
     * Retrieves the checkpoint times for the stage.
     *
     * @return An array of LocalTime representing the times at each checkpoint.
     */
    public LocalTime[] getStageResult(){
        return checkpointTimes;
    }

    /**
     * Sets the rank of the rider in this stage.
     *
     * @param rank The rank to set.
     */
    public void setRank(int rank){
        this.rank = rank;
    }

    /**
     * Gets the rank of the rider in this stage.
     *
     * @return The rank of the rider.
     */
    public int getRank(){
        return rank;
    }

    /**
     * Sets the points the rider earned in this stage.
     *
     * @param points The points to set.
     */
    public void setPoints(int points){
        this.points = points;
    }

    /**
     * Gets the points the rider earned in this stage.
     *
     * @return The points of the rider.
     */
    public int getPoints(){
        return points;
    }

    /**
     * Gets the mountain points the rider earned in this stage.
     *
     * @return The mountain points of the rider.
     */
    public int getMountainPoints() {
        return mountainPoints;
    }

    /**
     * Adds mountain points to the rider's total for this stage.
     *
     * @param mountainPoints The mountain points to add.
     */
    public void addMountainPoints(int mountainPoints) {
        this.mountainPoints += mountainPoints;
    }

    /**
     * Retrieves the time at a specific checkpoint index.
     *
     * @param index The index of the checkpoint.
     * @return The time at the specified checkpoint.
     */
    public LocalTime getCheckpointTimeAtIndex(int index){
        return checkpointTimes[index];
    }

    /**
     * Adds sprint points to the rider's total for this stage.
     *
     * @param sprintPoints The sprint points to add.
     */
    public void addSprintPoints(int sprintPoints) {
        this.sprintPoints += sprintPoints;
    }

    /**
     * Gets the sprint points the rider earned in this stage.
     *
     * @return The sprint points of the rider.
     */
    public int getSprintPoints() {
        return sprintPoints;
    }

    /**
     * Retrieves all checkpoint times for the stage.
     *
     * @return An array of LocalTime objects representing the times at each checkpoint.
     */
    public LocalTime [] getCheckpointTimes(){
        return checkpointTimes;
    }

    public void setAdjustedCheckpointTime(int checkpointIndex, LocalTime timeDifference){
        adjustedCheckpointTimes[checkpointIndex] = timeDifference;
    }

    public LocalTime getAdjustedCheckpointTime(int checkpointIndex){
        LocalTime adjustedCheckpointTime = adjustedCheckpointTimes[checkpointIndex];
        return adjustedCheckpointTime;
    }
}
