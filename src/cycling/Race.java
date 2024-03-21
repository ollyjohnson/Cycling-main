package cycling;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Race implements Serializable {
    private int id;
    private String name;
    private String description;
    private HashMap<Integer, Stage> stages;
    private HashMap<Integer, Result> riderResults = new HashMap<>();

    public Race(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stages = new HashMap<>();
    }

    @Override
    public String toString() {
        return("Race id = " + id + " name="+name+" description="+description+" stages=" + stages);
    }

    public int getRaceId() {
        return id;
    }

    public String getRaceName() {
        return name;
    }

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
        Stages in race = %d
        Length of race = %.2f
        """.formatted(id, name, description, String.join("\n", stageNames), totalLength);
        return details;
    }

    public void addStage(int stageId, Stage stage) {
        stages.put(stageId, stage);
    }

    public void removeStage(int stageId){
        stages.remove(stageId);
    }

    public int getNumberOfStages(){
        return stages.size();
    }

    public int [] getStageIds(){
        int [] stageIds = new int[stages.size()];
        int index = 0;
        for(Integer stageId : stages.keySet()){
            stageIds[index++] = stageId;
        }
        return stageIds;
    }

    public Stage [] getStages(){
        return stages.values().toArray(new Stage[0]);
    }

    public boolean riderHasResult(int riderId){
        return(riderResults.containsKey(riderId));
    }


    public Result getOverallResult(int riderId){
        return riderResults.get(riderId);
    }

    public Result addOverallResult(int riderId , Result raceResult){
        return riderResults.put(riderId, raceResult);
    }

    public int[] getRiders(){
        int[] ridersInRace = new int[riderResults.size()];
        int index = 0;
        for (Integer key : riderResults.keySet()) {
            ridersInRace[index++] = key;
        }
        return ridersInRace;
    }

    public ArrayList<Result> getSortedListOfResults() {
        ArrayList<Result> riderResultsByTime = new ArrayList<>(riderResults.values());
        Collections.sort(riderResultsByTime);
        return riderResultsByTime;
    }

    public LocalTime[] getSortedListOfTimes(){
        ArrayList<Result> riderResultsByTime = getSortedListOfResults();
        LocalTime[] sortedListOfTimes = new LocalTime[riderResultsByTime.size()];
        int index = 0;
        for(Result result: riderResultsByTime){
            sortedListOfTimes[index++] = result.getTotalAdjustedElapsedTime();
        }
        return sortedListOfTimes;
    }

    public int[] getRiderIdsByTotalTime(){
        ArrayList<Result> riderResultsByTime = getSortedListOfResults();
        int[] sortedListOfIds = new int[riderResultsByTime.size()];
        int index = 0;
        for(Result result: riderResultsByTime){
            sortedListOfIds[index++] = result.getRiderId();
        }
        return sortedListOfIds;
    }

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




