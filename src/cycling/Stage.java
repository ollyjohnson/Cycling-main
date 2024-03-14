package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stage {
    private int id;
    private String name;
    private Race race;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType stageType;
    private Map<Integer, Checkpoint> checkpoints = new HashMap<>();
    private boolean waitingForResults;
    private boolean timesAdjusted;
    private Map<Integer, Results> riderResults = new HashMap<>();;

    public Stage(int id, String name, Race race, String description, int length, LocalDateTime startTime, StageType stageType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.race = race;
        this.length = length;
        this.startTime = startTime;
        this.stageType = stageType;
        this.timesAdjusted = true; //this is set to false when the adjusted rider elapsed times need to be updated
    }
    @Override
    public String toString(){
        return("Stage id = " + id + "name="+name+" description="+description+" race="+ race+" length="+ length+ " start time=" + startTime+" stage type="+stageType);
    }
    public int getStageId(){
        return id;
    }
    public String getStageName(){
        return name;
    }
    public double getLength(){
        return length;
    }
    public void addCheckpointToStage(int checkpointId, Checkpoint checkpoint) {
        this.checkpoints.put(checkpointId, checkpoint);
    }
    public boolean isValidLocation(Double location){
        return location > 0 && location <= length;
    }
    public void setWaitingForResults() {
        this.waitingForResults = true;
    }
    public boolean isStageWaitingForResults(){
        return waitingForResults;
    }
    public Race getRace(){
        return race;
    }
    public void removeCheckpointFromStage(int checkpointId) {
        this.checkpoints.remove(checkpointId);
    }
    public int[] getOrderedCheckpointIds() {
        int[] checkpointIds = new int[checkpoints.size()];
        int index = 0;
        //creates an array of the checkpoint ids
        for (Integer checkpointId : checkpoints.keys()) {
            checkpointIds[index++] = checkpointId;
        }
        //orders the array by the location
        for (int i = 0; i < checkpointIds.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < checkpointIds.length; j++) {
                if (checkpoints.get(checkpointIds[j]).getLocation() < checkpoints.get(checkpointIds[minIndex]).getLocation()) {
                    minIndex = j;
                }
            }
            //if the location that is stored in i is greater than the location of the checkpoint in j then swap them around
            if (minIndex != i) {
                int temp = checkpointIds[i];
                checkpointIds[i] = checkpointIds[minIndex];
                checkpointIds[minIndex] = temp;
            }
        }
        return checkpointIds;
    }

    public HashMap<Integer, Checkpoint> getCheckpoints(){
        return checkpoints;
    }

    public boolean riderHasResult(int riderId){
        return(riderResults.containsKey(riderId));
    }

    public void recordRiderCheckpointTimes(int riderId, LocalTime[] checkpoints){
        Results results = new Results(riderId, checkpoints);
        //stores the rider id with their results
        riderResults.put(riderId, results);
        timesAdjusted = false;
    }

    public LocalTime [] getRiderResults(int riderId){
        //get the results for the specific rider
        Results results = riderResults.get(riderId);
        LocalTime [] checkpointTimes = results.getResults();
        //create a new array with an additional space for the elapsed time
        LocalTime [] resultTimes = new LocalTime[checkpointTimes.length+1];
        //copy the contents of the checkpointTimes array
        System.arraycopy(checkpointTimes, 0, resultTimes, 0, checkpointTimes.length);
        //add the elapsed time to the end
        resultTimes[resultTimes.length-1] = results.getElapsedTime();
        return resultTimes;
    }

    public ArrayList<Results> getSortedListOfElapsedTimes() {
        List<Results> riderRanksByElapsedTime = new ArrayList<>(riderResults.values());
        Collections.sort(riderRanksByElapsedTime);
        return riderRanksByElapsedTime;
    }

    public void adjustRiderElapsedTimes() {
        //this if statement means the method will only be called if the times have been adjusted since the last call
        if(!timesAdjusted){
            List<Results> sortedElapsedTimes = getSortedListOfElapsedTimes();
            if (!sortedElapsedTimes.isEmpty()) {
                sortedElapsedTimes.get(0).setAdjustedElapsedTime(sortedElapsedTimes.get(0).getElapsedTime());
                sortedElapsedTimes.get(0).setRank(1);
            }
            for (int i = 1; i < sortedElapsedTimes.size(); i++) {
                Results previousResult = sortedElapsedTimes.get(i - 1);
                Results currentResult = sortedElapsedTimes.get(i);
                LocalTime previousElapsedTime = previousResult.getElapsedTime();
                LocalTime currentElapsedTime = currentResult.getElapsedTime();
                int previousRank = previousResult.getRank();
                long secondsBetween = ChronoUnit.SECONDS.between(previousElapsedTime, currentElapsedTime);
                currentResult.setRank(i+1);
                if (secondsBetween < 1) {
                    currentResult.setAdjustedElapsedTime(previousResult.getAdjustedElapsedTime());
                } else {
                    currentResult.setAdjustedElapsedTime(currentElapsedTime);
                }
            }
            timesAdjusted = true; // resets timesAdjusted to be true until a new result is added
        }
    }

    public LocalTime getRiderAdjustedElapsedTime(int riderId){
        //get the results for the specific rider
        Results results = riderResults.get(riderId);
        LocalTime adjustedElapsedTime = results.getAdjustedElapsedTime();
        return adjustedElapsedTime;
    }

    public void removeRiderResults(int riderId){
        riderResults.remove(riderId);
    }

    public int [] getRiderRanks(){
        int [] ranks = new int [riderResults.size()];
        for(Result result : riderResults.values()){
            int riderRank = result.getRank();
            ranks[riderRank-1] = result.getRiderId();
        }
        return ranks;
    }

    public LocalTime [] getRankedAdjustedElapsedTimes(){
        int[] riderRanks = getRiderRanks();
        LocalTime [] rankedAdjustedElapsedTimes = new LocalTime [riderRanks.length];
        for(int i = 0; i < riderRanks.length; i++){
            rankedAdjustedElapsedTimes[i] = riderResults.get(riderRanks[i]).getAdjustedElapsedTime();
        }
        return rankedAdjustedElapsedTimes;
    }

    private int[] getPointsDistributionByStageType() {
        switch (stageType) {
            case FLAT:
                return new int[]{50, 30, 20, 18, 16, 14, 12, 10, 8, 7, 6, 5, 4, 3, 2};
            case MEDIUM_MOUNTAIN:
                return new int[]{30, 25, 22, 19, 17, 15, 13, 11, 9, 7, 6, 5, 4, 3, 2};
            case HIGH_MOUNTAIN:
                return new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
            case TT:
                return new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
            // Include cases for other types of stages
            default:
                return new int[0]; // Default to no points for unrecognized stage types
        }
    }






}

