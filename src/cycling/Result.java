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

    private LocalTime totalAdjustedElapsedTime = LocalTime.MIN;
    private final int riderId;
    private int points = 0;
    private int mountainPoints = 0;
    private int sprintPoints = 0;
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

    public LocalTime getTotalAdjustedElapsedTime() {
        for (StageResult result : stageResults.values()) {
            LocalTime adjustedElapsedTime = result.getAdjustedElapsedTime();
            totalAdjustedElapsedTime = addLocalTimes(totalAdjustedElapsedTime, adjustedElapsedTime);
        }
        return totalAdjustedElapsedTime;
    }

    public int getRiderId(){
        return riderId;
    }


    public void addPoints(int points){
        this.points += points;
    }

    public int getPoints(){
        return points;
    }

    public Integer getMountainPoints() {
        return mountainPoints;
    }

    public void addMountainPoints(int mountainPoints) {
        this.mountainPoints += mountainPoints;
    }

    public void addSprintPoints(int sprintPoints) {
        this.sprintPoints += sprintPoints;
    }

    public Integer getSprintPoints() {
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

    /**
     * Clears the stageResults hash maps containing all the results for individual stages.
     */
    public void clearStageResults(){
        stageResults.clear();
    }
}
