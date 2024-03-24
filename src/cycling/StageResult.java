package cycling;

import java.time.LocalTime;
import java.io.Serializable;

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

    public StageResult(int riderId, LocalTime[] checkpointTimes){
        this.riderId = riderId;
        this.checkpointTimes = checkpointTimes;
        this.adjustedCheckpointTimes = checkpointTimes;
        this.elapsedTime = calculateElapsedTime(checkpointTimes);
    }

    public LocalTime calculateElapsedTime(LocalTime[] checkpointTimes) {
        LocalTime start = checkpointTimes[0];
        LocalTime end = checkpointTimes[checkpointTimes.length - 1];
        long startNanos = start.toNanoOfDay();
        long endNanos = end.toNanoOfDay();
        long elapsedNanos = endNanos - startNanos;
        elapsedTime = LocalTime.ofNanoOfDay(elapsedNanos);
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

    public int getMountainPoints() {
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

    public int getSprintPoints() {
        return sprintPoints;
    }

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
