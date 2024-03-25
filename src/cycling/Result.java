package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;

/**
 * Represents the result of a rider in a race, encapsulating stage times, total time, and points.
 */
public class Result implements Comparable <Result>, Serializable {

    private LocalTime [] stageTimes;
    private LocalTime totalAdjustedElapsedTime;
    private int riderId;
    private int rank;
    private int points;
    private int mountainPoints;
    private int sprintPoints;
    private HashMap<Integer, StageResult> stageResults = new HashMap<>();

    /**
     * Constructs a Result object for a rider.
     *
     * @param riderId The ID of the rider.
     */
    public Result(int riderId){
        this.riderId = riderId;
        this.rank = 0;
        this.points = 0;
        this.mountainPoints = 0;
        this.sprintPoints = 0;
        this.totalAdjustedElapsedTime = LocalTime.MIN;
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
     * Adds a stage result to this result, updating the total adjusted elapsed time.
     *
     * @param stageId The ID of the stage.
     * @param stageResult The result from the stage.
     */
    public void addStageResult(int stageId, StageResult stageResult) {
        stageResults.put(stageId, stageResult);
        //this updates the totalAdjustedElapsedTime to ensure it is up to date
        LocalTime adjustedElapsedTime = stageResult.getAdjustedElapsedTime();
        totalAdjustedElapsedTime = addLocalTimes(totalAdjustedElapsedTime, adjustedElapsedTime);
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
        return totalAdjustedElapsedTime;
    }

    public int getRiderId(){
        return riderId;
    }

    public LocalTime[] getResults(){
        return stageTimes;
    }

    public void setRank(int rank){
        this.rank = rank;
    }
    
    public int getRank(){
        return rank;
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
}
