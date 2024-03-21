package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;

public class Result implements Comparable <Result>, Serializable {

    private LocalTime [] stageTimes;
    private LocalTime totalAdjustedElapsedTime;
    private int riderId;
    private int rank;
    private int points;
    private int mountainPoints;
    private int sprintPoints;
    private HashMap<Integer, StageResult> stageResults = new HashMap<>();

    public Result(int riderId){
        this.riderId = riderId;
        this.rank = 0;
        this.points = 0;
        this.mountainPoints = 0;
        this.sprintPoints = 0;
    }

    @Override
    public int compareTo(Result other) {
        return this.totalAdjustedElapsedTime.compareTo(other.totalAdjustedElapsedTime);
    }

    public void addStageResult(int stageId, StageResult stageResult) {
        stageResults.put(stageId, stageResult);
    }

    public void setAdjustedElapsedTime(LocalTime totalAdjustedElapsedTime) {
        this.totalAdjustedElapsedTime = totalAdjustedElapsedTime;
    }

    public LocalTime getTotalAdjustedElapsedTime() {
        long totalNanoseconds = 0;
        for (StageResult result : stageResults.values()) {
            LocalTime adjustedElapsedTime = result.getAdjustedElapsedTime();
            totalNanoseconds += adjustedElapsedTime.toNanoOfDay();
        }
        LocalTime totalAdjustedElapsedTime = LocalTime.ofNanoOfDay(totalNanoseconds);
    
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

    public void setPoints(int points){
        this.points = points;
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
}
