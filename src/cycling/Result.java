package cycling;

import java.time.LocalTime;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Represents the result of a rider in a race, storing stage times, total time, and points.
 * 
 * @author Olly Johnson and Laith Al Qudah
 * @version 1.0
 */
public class Result implements Comparable <Result>, Serializable {

    /** The sum of adjusted elapsed times across all stages */
    private LocalTime totalAdjustedElapsedTime = LocalTime.MIN;
    /** Unique identifier for the rider */
    private final int riderId;
    /** Total points accumulated by the rider across all stages */
    private int points = 0;
    /** Total mountain classification points accumulated by the rider */
    private int mountainPoints = 0;
    /** Total sprint classification points accumulated by the rider */
    private int sprintPoints = 0;
    /** Mapping of stage IDs to the rider's results in each stage */
    private HashMap<Integer, StageResult> stageResults = new HashMap<>();

    /**
     * Constructs a Result object for a rider.
     *
     * @param riderId The ID of the rider.
     */
    public Result(int riderId){
        this.riderId = riderId;
    }

    /**
     * Returns a string representation of the Result, including the rider it relates to.
     *
     * @return A string containing the results details.
     */
    @Override
    public String toString(){
        return("Overall result for rider id:" + riderId);
    }

    /**
     * Compares this result with another to order them by total adjusted elapsed time.
     *
     * @param other The other result to compare to.
     * @return A negative integer, zero, or a positive integer as this result is less than,
     *         equal to, or greater than the specified result.
     */
    @Override
    public int compareTo(Result other) {
        return this.totalAdjustedElapsedTime.compareTo(other.totalAdjustedElapsedTime);
    }

    /**
     * Adds a stage result to this result.
     *
     * @param stageId The ID of the stage.
     * @param stageResult The result from the stage.
     */
    public void addStageResult(int stageId, StageResult stageResult) {
        stageResults.put(stageId, stageResult);
    }

    /**
     * Removes a stage result from this result.
     *
     * @param stageId The ID of the stage.
     */
    public void removeStageResult(int stageId) {
        stageResults.remove(stageId);
    }

    /**
     * Checks if there is a result stored for a stage.
     *
     * @param stageId The ID of the stage.
     */
    public boolean hasStageResult(int stageId) {
        return stageResults.containsKey(stageId);
    }

    /**
     * Checks if this result has any stages associated with it.
     *
     * @return true if the Result doesn't have any StageResults, false otherwise.
     */
    public boolean isEmpty() {
        return stageResults.isEmpty();
    }

    /**
     * Adds two LocalTime objects together.
     *
     * @param time1 The first time.
     * @param time2 The second time.
     * @return The sum of the two times.
     */
    private LocalTime addLocalTimes(LocalTime time1, LocalTime time2) {
        long totalNanoseconds = time1.toNanoOfDay() + time2.toNanoOfDay();
        return LocalTime.ofNanoOfDay(totalNanoseconds);
    }

    /**
     * Calculates the total adjusted elapsed time for the rider by summing up the adjusted times from all stages.
     *
     * @return The total adjusted elapsed time for the rider across all stages.
     */
    public LocalTime getTotalAdjustedElapsedTime() {
        return totalAdjustedElapsedTime;
    }

    /**
     * Gets the unique ID of the rider associated with this result.
     *
     * @return The rider ID.
     */
    public int getRiderId(){
        return riderId;
    }

     /**
     * Adds points to the rider's total points.
     *
     * @param points The number of points to be added.
     */
    public void addPoints(int points){
        this.points += points;
    }

    /**
     * Gets the total points for the rider.
     *
     * @return The total points.
     */
    public int getPoints(){
        calculateTotalPoints();
        return points;
    }

    /**
     * Gets the total mountain points for the rider.
     *
     * @return The total mountain points.
     */
    public Integer getMountainPoints() {
        calculateMountainPoints();
        return mountainPoints;
    }

    /**
     * Adds mountain points to the rider's total mountain points.
     *
     * @param mountainPoints The number of mountain points to be added.
     */
    public void addMountainPoints(int mountainPoints) {
        this.mountainPoints += mountainPoints;
    }

    /**
     * Adds sprint points to the rider's total sprint points.
     *
     * @param sprintPoints The number of sprint points to be added.
     */
    public void addSprintPoints(int sprintPoints) {
        this.sprintPoints += sprintPoints;
    }

    /**
     * Gets the total sprint points for the rider.
     *
     * @return The total sprint points.
     */
    public Integer getSprintPoints() {
        calculateTotalPoints();
        return sprintPoints;
    }

    /**
     * Calculates the total points accumulated by the rider across all stages.
     */
    public void calculateTotalPoints(){
        this.points = 0;
        this.sprintPoints = 0;
        for(StageResult result: stageResults.values()){
            addPoints(result.getPoints());
            addSprintPoints(result.getSprintPoints());
        }
    }

    /**
     * Calculates the total mountain points accumulated by the rider across all stages.
     */
    public void calculateMountainPoints(){
        this.mountainPoints = 0;
        for(StageResult result: stageResults.values()){
            addMountainPoints(result.getMountainPoints());
        }
    }

    public void calculateAdjustedElapsedTime(){
        this.totalAdjustedElapsedTime = LocalTime.MIN;
        for (StageResult result : stageResults.values()) {
            LocalTime adjustedElapsedTime = result.getAdjustedElapsedTime();
            this.totalAdjustedElapsedTime = addLocalTimes(totalAdjustedElapsedTime, adjustedElapsedTime);
        }
    }

    /**
     * Clears the stageResults hash maps containing all the results for individual stages.
     */
    public void clearStageResults(){
        stageResults.clear();
    }
    
    /**
     * Clears the stageResults hash maps containing all the results for individual stages.
     */
    public void updateResult(){
        calculateAdjustedElapsedTime();
        calculateTotalPoints();
        calculateMountainPoints();
    }
}
