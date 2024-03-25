package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Stage {
    private int id;
    private String name;
    private Race race;
    private String description;
    private double length;
    private LocalDateTime startTime;
    private StageType stageType;
    private HashMap<Integer, Checkpoint> checkpoints = new HashMap<>();
    private boolean waitingForResults;
    private boolean timesAdjusted;
    private boolean checkpointPointsUpdated;
    private HashMap<Integer, StageResult> riderResults = new HashMap<>();

    /**
     * Constructs a stage with specified attributes.
     *
     * @param id Unique identifier for the stage.
     * @param name Name of the stage.
     * @param race The race this stage is part of.
     * @param description Description of the stage.
     * @param length Length of the stage in kilometers.
     * @param startTime Start time of the stage.
     * @param stageType Type of the stage (e.g., flat, mountain, time trial).
     */
    public Stage(int id, String name, Race race, String description, double length, LocalDateTime startTime, StageType stageType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.race = race;
        this.length = length;
        this.startTime = startTime;
        this.stageType = stageType;
        this.timesAdjusted = true; //this is set to false when the adjusted rider elapsed times need to be updated
        this.checkpointPointsUpdated = true; //this is set to false when the adjusted rider elapsed times need to be updated
    }

    /**
     * Returns a string representation of the stage, detailing its properties.
     *
     * @return Formatted string containing stage details.
     */
    @Override
    public String toString(){
        return("Stage id = " + id + "name="+name+" description="+description+" race="+ race+" length="+ length+ " start time=" + startTime+" stage type="+stageType);
    }

    /**
     * Gets the unique identifier of the stage.
     *
     * @return The ID of the stage.
     */
    public int getStageId(){
        return id;
    }

    /**
     * Gets the name of the stage.
     *
     * @return The name of the stage.
     */
    public String getStageName(){
        return name;
    }

    /**
     * Gets the length of the stage in kilometers.
     *
     * @return The length of the stage.
     */
    public double getLength(){
        return length;
    }

    /**
     * Adds a checkpoint to the stage.
     *
     * @param checkpointId Unique identifier for the checkpoint.
     * @param checkpoint The checkpoint object to add to the stage.
     */
    public void addCheckpointToStage(int checkpointId, Checkpoint checkpoint) {
        this.checkpoints.put(checkpointId, checkpoint);
    }

    /**
     * Checks if a location is valid within the stage boundaries.
     *
     * @param location The location to check.
     * @return True if the location is within the stage's length, false otherwise.
     */
    public boolean isValidLocation(Double location){
        return location > 0 && location <= length;
    }

    /**
     * Sets the stage's status to waiting for results.
     */
    public void setWaitingForResults() {
        this.waitingForResults = true;
    }

    /**
     * Checks if the stage is currently waiting for results.
     *
     * @return True if the stage is waiting for results, false otherwise.
     */
    public boolean isStageWaitingForResults(){
        return waitingForResults;
    }

    /**
     * Gets the race associated with this stage.
     *
     * @return The race object to which the stage belongs.
     */
    public Race getRace(){
        return race;
    }

    /**
     * Removes a checkpoint from the stage.
     *
     * @param checkpointId The ID of the checkpoint to remove.
     */
    public void removeCheckpointFromStage(int checkpointId) {
        this.checkpoints.remove(checkpointId);
    }

    /**
     * Orders and returns the checkpoint IDs based on their location within the stage.
     *
     * @return An array of checkpoint IDs, ordered by their location.
     */
    public int[] getOrderedCheckpointIds() {
        int[] checkpointIds = new int[checkpoints.size()];
        int index = 0;
        //creates an array of the checkpoint ids
        for (Integer checkpointId : checkpoints.keySet()) {
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

    /**
     * Retrieves the checkpoints map for this stage.
     *
     * @return A map of checkpoint IDs to checkpoint objects.
     */
    public HashMap<Integer, Checkpoint> getCheckpoints(){
        return checkpoints;
    }

    /**
     * Checks if the rider has a result recorded for this stage.
     *
     * @param riderId The ID of the rider to check.
     * @return True if results exist for the rider, false otherwise.
     */
    public boolean riderHasResult(int riderId){
        return(riderResults.containsKey(riderId));
    }

    /**
     * Records the result of a rider for this stage.
     *
     * @param riderId The ID of the rider whose result is being recorded.
     * @param results The results object containing the rider's times and rankings.
     */
    public void addStageResult(int riderId, StageResult results){
        //stores the rider id with their results
        riderResults.put(riderId, results);
        timesAdjusted = false;
        checkpointPointsUpdated = false;
    }

    /**
     * Retrieves the results for a specified rider in this stage.
     *
     * @param riderId The ID of the rider whose results are requested.
     * @return An array of LocalTime objects representing the rider's times at checkpoints.
     */
    public LocalTime [] getRiderResults(int riderId){
        //get the results for the specific rider
        StageResult results = riderResults.get(riderId);
        LocalTime [] checkpointTimes = results.getCheckpointTimes();
        //create a new array for the result times
        LocalTime [] resultTimes = new LocalTime[checkpointTimes.length-1];
        //copy the contents of the checkpointTimes array
        System.arraycopy(checkpointTimes, 1, resultTimes, 0, checkpointTimes.length - 1);
        //add the elapsed time to the end
        resultTimes[resultTimes.length-1] = results.getElapsedTime();
        return resultTimes;
    }

    /**
     * Sorts and returns the list of rider results based on elapsed times.
     *
     * @return A sorted list of StageResults according to elapsed time.
     */
    public ArrayList<StageResult> getSortedListOfElapsedTimes() {
        ArrayList<StageResult> riderRanksByElapsedTime = new ArrayList<>(riderResults.values());
        Collections.sort(riderRanksByElapsedTime);
        return riderRanksByElapsedTime;
    }

    /**
     * Adjusts the elapsed times for all riders, considering various race conditions.
     */
    public void adjustRiderElapsedTimes() {
        //this if statement means the method will only be called if the times have been adjusted since the last call
        if(!timesAdjusted){
            ArrayList<StageResult> sortedElapsedTimes = getSortedListOfElapsedTimes();
            int [] pointsDistribution = getPointsDistributionByStageType();
            if (!sortedElapsedTimes.isEmpty()) {
                sortedElapsedTimes.get(0).setAdjustedElapsedTime(sortedElapsedTimes.get(0).getElapsedTime());
                sortedElapsedTimes.get(0).setRank(1);
                sortedElapsedTimes.get(0).setPoints(pointsDistribution[0]);
            }
            for (int i = 1; i < sortedElapsedTimes.size(); i++) {
                StageResult previousResult = sortedElapsedTimes.get(i - 1);
                StageResult currentResult = sortedElapsedTimes.get(i);
                LocalTime previousElapsedTime = previousResult.getElapsedTime();
                LocalTime currentElapsedTime = currentResult.getElapsedTime();
                long secondsBetween = ChronoUnit.SECONDS.between(previousElapsedTime, currentElapsedTime);
                currentResult.setRank(i+1);
                if(i < pointsDistribution.length){
                    currentResult.setPoints(pointsDistribution[i]);
                }
                else{
                    currentResult.setPoints(0);
                }
                if (secondsBetween < 1) {
                    currentResult.setAdjustedElapsedTime(previousResult.getAdjustedElapsedTime());
                } else {
                    currentResult.setAdjustedElapsedTime(currentElapsedTime);
                }
            }
            timesAdjusted = true; // resets timesAdjusted to be true until a new result is added
        }
    }

    /**
     * Retrieves the adjusted elapsed time for a specific rider in this stage.
     *
     * @param riderId The ID of the rider.
     * @return The adjusted elapsed time for the rider.
     */
    public LocalTime getRiderAdjustedElapsedTime(int riderId){
        //get the results for the specific rider
        StageResult results = riderResults.get(riderId);
        LocalTime adjustedElapsedTime = results.getAdjustedElapsedTime();
        return adjustedElapsedTime;
    }

    /**
     * Removes the results of a specific rider from this stage.
     *
     * @param riderId The ID of the rider whose results are to be removed.
     */
    public void removeRiderResults(int riderId){
        riderResults.remove(riderId);
    }

    /**
     * Gets the ranks of all riders in this stage based on their results.
     *
     * @return An array of rider IDs in the order of their rank.
     */
    public int [] getRiderRanks(){
        int [] ranks = new int [riderResults.size()];
        for(StageResult result : riderResults.values()){
            int riderRank = result.getRank();
            ranks[riderRank-1] = result.getRiderId();
        }
        return ranks;
    }

    /**
     * Retrieves the adjusted elapsed times for riders in the stage, sorted by rank.
     *
     * @return An array of LocalTime objects representing the sorted adjusted times.
     */
    public LocalTime [] getRankedAdjustedElapsedTimes(){
        int[] riderRanks = getRiderRanks();
        LocalTime [] rankedAdjustedElapsedTimes = new LocalTime [riderRanks.length];
        for(int i = 0; i < riderRanks.length; i++){
            rankedAdjustedElapsedTimes[i] = riderResults.get(riderRanks[i]).getAdjustedElapsedTime();
        }
        return rankedAdjustedElapsedTimes;
    }

    /**
     * Calculates the points distribution based on the type of the stage.
     *
     * @return An array of points corresponding to stage rankings.
     */
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
            default:
                return new int[0];
        }
    }

    /**
     * Calculates and returns the total points for each rider, ordered by their rank.
     *
     * @return An array of points corresponding to each rider's total in the stage.
     */
    public int[] getOrderedPoints(){
        if(!checkpointPointsUpdated){
            assignCheckpointPoints();
        }
        adjustRiderElapsedTimes();
        int[] orderedPoints = new int[riderResults.size()];
        for(StageResult result: riderResults.values()){
            int rank = result.getRank();
            int points = result.getPoints() + result.getSprintPoints();
            orderedPoints[rank-1] = points;
        }
        return orderedPoints;
    }

    /**
     * Determines the points distribution for different types of checkpoints.
     *
     * @param type The type of checkpoint (e.g., C4, C3, C2, C1, HC, SPRINT).
     * @return An array representing the points distribution for the specified checkpoint type.
     */
    private int[] getCheckpointPointsDistributionByType(CheckpointType type) {
        switch (type) {
            case C4:
                return new int[]{1};
            case C3:
                return new int[]{1, 1};
            case C2:
                return new int[]{2, 1};
            case C1:
                return new int[]{5, 3, 2, 1};
            case HC:
                return new int[]{20, 15, 12, 10, 8, 6, 4, 2};
            case SPRINT:
                return new int[]{20, 17, 15, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
            default:
                return new int[0];
        }
    }

    /**
     * Assigns points to riders based on their performance at checkpoints within the stage.
     */
    public void assignCheckpointPoints() {
    int checkpointIndex = 1;
    for (Checkpoint checkpoint : checkpoints.values()) {
            CheckpointType checkpointType = checkpoint.getType();
            int[] checkpointPointsDistribution = getCheckpointPointsDistributionByType(checkpointType);
            ArrayList<StageResult> riderResultsAtCheckpoint = new ArrayList<>();
            final int currentCheckpointIndex = checkpointIndex;
            for (StageResult result : riderResults.values()) {
                if (result.getCheckpointTimeAtIndex(currentCheckpointIndex)!= null) {
                    riderResultsAtCheckpoint.add(result);
                }
            }
            riderResultsAtCheckpoint.sort(Comparator.comparing(result -> result.getCheckpointTimeAtIndex(currentCheckpointIndex)));

            // Assign points to riders based on their order
            for (int i = 0; i < riderResultsAtCheckpoint.size() && i < checkpointPointsDistribution.length; i++) {
                StageResult result = riderResultsAtCheckpoint.get(i);
                if (checkpoint instanceof Climb) {
                    result.addMountainPoints(checkpointPointsDistribution[i]);
                } else if (checkpoint.getType() == CheckpointType.SPRINT) {
                    result.addSprintPoints(checkpointPointsDistribution[i]);
                }
            }
        checkpointIndex++;
    }
    checkpointPointsUpdated = true;
}

    /**
     * Returns an array of mountain points for riders, ordered by their finishing rank in the stage.
     *
     * @return An array of mountain points for each rider, ordered by their rank.
     */
    public int[] getOrderedMountainPoints(){
        if(!checkpointPointsUpdated){
            assignCheckpointPoints();
        }
        int[] orderedMountainPoints = new int[riderResults.size()];
        for(StageResult result: riderResults.values()){
            int rank = result.getRank();
            int mountainPoints = result.getMountainPoints();
            orderedMountainPoints[rank-1] = mountainPoints;
        }
        return orderedMountainPoints;
    }

    /**
     * Retrieves the stage type.
     *
     * @return The type of the stage (e.g., FLAT, MEDIUM_MOUNTAIN, HIGH_MOUNTAIN, TT).
     */
    public StageType getStageType(){
        return stageType;
    }

    /**
     * Retrieves all results recorded in the stage.
     *
     * @return A map containing rider IDs and their corresponding stage results.
     */
    public HashMap<Integer, StageResult> getAllRiderResultsInStage(){
        return riderResults;
    }

    /**
     * Gets an array of all rider IDs that have participated in the stage.
     *
     * @return An array of rider IDs for all riders who have results in this stage.
     */
    public int[] getAllRidersInStage(){
        int[] ridersInStage = new int[riderResults.size()];
        int index = 0;
        for (Integer key : riderResults.keySet()) {
            ridersInStage[index++] = key;
        }
        return ridersInStage;
    }








}

