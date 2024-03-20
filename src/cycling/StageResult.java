package cycling;

import java.time.LocalTime;

import javax.naming.spi.DirStateFactory.Result;

import java.io.Serializable;
import java.time.Duration;

public class StageResult implements Comparable <StageResult>, Serializable {
    private LocalTime [] checkpointTimes;
    private LocalTime elapsedTime;
    private LocalTime adjustedElapsedTime;
    private int riderId;
    private int rank;
    private int points;
    private int mountainPoints;
    private int sprintPoints;

    public StageResult(int riderId, LocalTime[] checkpointTimes){
        this.riderId = riderId;
        this.rank = 0;
        this.points = 0;
        this.mountainPoints = 0;
        this.sprintPoints = 0;
        this.checkpointTimes = checkpointTimes;
    }

    public LocalTime calculateElapsedTime(){
        Duration duration = Duration.between(checkpointTimes[0], checkpointTimes[checkpointTimes.length - 1]);
        elapsedTime = LocalTime.ofNanoOfDay(duration.toNanos());
        return elapsedTime;
    }

    public LocalTime getElapsedTime(){
        return elapsedTime;
    }

    @Override
    public int compareTo(StageResult other) {
        return this.elapsedTime.compareTo(other.elapsedTime);
    }

    public void setAdjustedElapsedTime(LocalTime adjustedElapsedTime) {
        this.adjustedElapsedTime = adjustedElapsedTime;
    }

    public LocalTime getAdjustedElapsedTime() {
        return adjustedElapsedTime;
    }

    public int getRiderId(){
        return riderId;
    }

    public LocalTime[] getStageResult(){
        return checkpointTimes;
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

    public LocalTime getCheckpointTimeAtIndex(int index){
        return checkpointTimes[index];
    }

    public void addSprintPoints(int sprintPoints) {
        this.sprintPoints += sprintPoints;
    }

    public Integer getSprintPoints() {
        return sprintPoints;
    }
}
