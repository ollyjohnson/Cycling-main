package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Race implements Serializable {
    private int id;
    private String name;
    private String description;
    private HashMap<Integer, Stage> stages;
    private HashMap<Integer, Result> riderResults = new HashMap<>();


    /**
     * Constructor to initialize a race with an ID, name, and description.
     *
     * @param id The unique identifier for the race.
     * @param name The name of the race.
     * @param description A description of the race.
     */
    public Race(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stages = new HashMap<>();
    }

    /**
     * Provides a string representation of the race with its basic details.
     *
     * @return A string containing the race's ID, name, description, and stages.
     */
    @Override
    public String toString() {
        return("Race id = " + id + " name="+name+" description="+description+" stages=" + stages);
    }

    /**
     * Gets the race's unique ID.
     *
     * @return The ID of the race.
     */
    public int getRaceId() {
        return id;
    }

    /**
     * Gets the race's name.
     *
     * @return The name of the race.
     */
    public String getRaceName() {
        return name;
    }

    /**
     * Provides detailed information about the race, including stage details.
     *
     * @return A formatted string containing the detailed description of the race.
     */
    public String getRaceDetails() {
        String[] stageNames = new String[stages.size()];
        double totalLength = 0;
        int index = 0;
        for(Stage stage : stages.values()){
            totalLength += stage.getLength();
            stageNames[index++] = stage.getStageName();
        }
    
        String details = """
        Race ID = %d
        Race name = %s
        Race description = %s
        Stages in race = %s
        Length of race = %.2f
        """.formatted(id, name, description, String.join("\n", stageNames), totalLength);
        return details;
    }

    /**
     * Adds a stage to the race.
     *
     * @param stageId The ID of the stage to be added.
     * @param stage The stage object to be added to the race.
     */
    public void addStage(int stageId, Stage stage) {
        stages.put(stageId, stage);
    }

    /**
     * Removes a stage from the race.
     *
     * @param stageId The ID of the stage to be removed.
     */
    public void removeStage(int stageId){
        stages.remove(stageId);
        
        Iterator<Result> iterator = riderResults.values().iterator();
        while(iterator.hasNext()){
            Result result = iterator.next();
            if(result.hasStageResult(stageId)){
                result.removeStageResult(stageId);
            }
            //this will ensure that the HashMap is empty if there are no results
            if(result.isEmpty()){
                iterator.remove();
            }
        }
    }

    /**
     * Gets the total number of stages in the race.
     *
     * @return The number of stages in the race.
     */
    public int getNumberOfStages(){
        return stages.size();
    }

    /**
     * Retrieves the IDs of all stages in the race.
     *
     * @return An array of stage IDs.
     */
    public int [] getStageIds(){
        int [] stageIds = new int[stages.size()];
        int index = 0;
        for(Integer stageId : stages.keySet()){
            stageIds[index++] = stageId;
        }
        return stageIds;
    }

    /**
     * Retrieves all stages in the race as an array.
     *
     * @return An array of Stage objects in the race.
     */
    public Stage [] getStages(){
        return stages.values().toArray(new Stage[0]);
    }

    /**
     * Checks if a rider has results recorded in this race.
     *
     * @param riderId The ID of the rider to check.
     * @return true if the rider has results in the race, false otherwise.
     */
    public boolean riderHasResult(int riderId){
        return(riderResults.containsKey(riderId));
    }

    /**
     * Retrieves the overall result for a rider in this race.
     *
     * @param riderId The ID of the rider whose results are being queried.
     * @return The overall result object for the rider.
     */
    public Result getOverallResult(int riderId){
        return riderResults.get(riderId);
    }

    /**
     * Adds or updates the overall result of a rider in the race.
     *
     * @param riderId The ID of the rider whose result is being added or updated.
     * @param raceResult The result object containing the rider's performance data.
     * @return The previous result for the rider if it exists, null otherwise.
     */
    public Result addOverallResult(int riderId , Result raceResult){
        return riderResults.put(riderId, raceResult);
    }

    /**
     * Removes the overall result of a rider in the race.
     *
     * @param riderId The ID of the rider whose result is being added or updated.
     * @return The previous result for the rider if it exists, null otherwise.
     */
    public void removeRiderResults(int riderId){
        for(Stage stage : stages.values()){
            stage.removeRiderResults(riderId);
        }
        Result riderResult = riderResults.get(riderId);
        if(riderResult != null){
            riderResult.clearStageResults();
            riderResults.remove(riderId);
        }
    }


    /**
     * Retrieves the IDs of all riders who have participated in the race.
     *
     * @return An array of rider IDs.
     */
    public int[] getRiders(){
        int[] ridersInRace = new int[riderResults.size()];
        int index = 0;
        for (Integer key : riderResults.keySet()) {
            ridersInRace[index++] = key;
        }
        return ridersInRace;
    }

    /**
     * Gets a sorted list of results for all riders in the race, based on their performance.
     *
     * @return A sorted ArrayList of Result objects.
     */
    public ArrayList<Result> getSortedListOfResults() {
        ArrayList<Result> riderResultsByTime = new ArrayList<>(riderResults.values());
        Collections.sort(riderResultsByTime);
        return riderResultsByTime;
    }

    /**
     * Retrieves a sorted list of times for all riders in the race.
     *
     * @return An array of LocalTime objects representing the sorted times.
     */
    public LocalTime[] getSortedListOfTimes(){
        ArrayList<Result> riderResultsByTime = getSortedListOfResults();
        LocalTime[] sortedListOfTimes = new LocalTime[riderResultsByTime.size()];
        int index = 0;
        for(Result result: riderResultsByTime){
            sortedListOfTimes[index++] = result.getTotalAdjustedElapsedTime();
        }
        return sortedListOfTimes;
    }

    /**
     * Gets an array of rider IDs sorted by their total time in the race.
     *
     * @return An array of rider IDs, ordered by performance.
     */
    public int[] getRiderIdsByTotalTime(){
        //if there are no results, return an empty array
        if(riderResults.isEmpty()){
            return new int[0];
        }
        ArrayList<Result> riderResultsByTime = getSortedListOfResults();
        int[] sortedListOfIds = new int[riderResultsByTime.size()];
        int index = 0;
        for(Result result: riderResultsByTime){
            sortedListOfIds[index++] = result.getRiderId();
        }
        return sortedListOfIds;
    }

    /**
     * Calculates and returns the total points for all riders in the race.
     *
     * @return An array of points for each rider, sorted by their performance.
     */
    public int[] getTotalPoints(){
        ArrayList<Result> riderResultsByTime = getSortedListOfResults();
        int[] sortedListOfPoints= new int[riderResultsByTime.size()];
        int index = 0;
        for(Result result: riderResultsByTime){
            result.calculateTotalPoints();
            sortedListOfPoints[index++] = result.getPoints() + result.getSprintPoints();
        }
        return sortedListOfPoints;
    }

    /**
     * Retrieves the total mountain points for all riders in the race.
     *
     * @return An array of mountain points, sorted by the riders' performance.
     */
    public int[] getMountainPoints(){
        ArrayList<Result> riderResultsByTime = getSortedListOfResults();
        int[] sortedListOfMountainPoints= new int[riderResultsByTime.size()];
        int index = 0;
        for(Result result: riderResultsByTime){
            result.calculateMountainPoints();
            sortedListOfMountainPoints[index++] = result.getMountainPoints();
        }
        return sortedListOfMountainPoints;
    }

    /**
     * Gets an array of rider IDs sorted by their points in the race.
     *
     * @return An array of rider IDs, sorted by the total points they have earned.
     */
    public int[] getRiderIdsByPoints() {
        ArrayList<Result> sortedResultsByPoints = new ArrayList<>(riderResults.values());
        Collections.sort(sortedResultsByPoints, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                int totalPoints1 = r1.getPoints() + r1.getSprintPoints();
                int totalPoints2 = r2.getPoints() + r2.getSprintPoints();
                return Integer.compare(totalPoints2, totalPoints1);
            }
        });
        int[] riderIdsSortedByPoints = new int[sortedResultsByPoints.size()];
        for (int i = 0; i < sortedResultsByPoints.size(); i++) {
            riderIdsSortedByPoints[i] = sortedResultsByPoints.get(i).getRiderId();
        }
        return riderIdsSortedByPoints;
    }

    /**
     * Retrieves the rider IDs sorted by their mountain points in the race.
     *
     * @return An array of rider IDs, sorted by their mountain points.
     */
    public int[] getRiderIdsByMountianPoints() {
        ArrayList<Result> sortedResultsByMountainPoints = new ArrayList<>(riderResults.values());
        Collections.sort(sortedResultsByMountainPoints, new Comparator<Result>() {
            @Override
            public int compare(Result r1, Result r2) {
                int totalPoints1 = r1.getMountainPoints();
                int totalPoints2 = r2.getMountainPoints();
                return Integer.compare(totalPoints2, totalPoints1);
            }
        });
        int[] riderIdsSortedByMountainPoints = new int[sortedResultsByMountainPoints.size()];
        for (int i = 0; i < sortedResultsByMountainPoints.size(); i++) {
            riderIdsSortedByMountainPoints[i] = sortedResultsByMountainPoints.get(i).getRiderId();
        }
        return riderIdsSortedByMountainPoints;
    }


}




