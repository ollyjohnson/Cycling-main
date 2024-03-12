package cycling;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Results {
    private LocalTime[] checkpointTimes;
    private LocalTime adjustedElapsedTime;
    private int rank;
    private int points;
    private int mountainPoints;

    public Results(LocalTime[] checkpointTimes){
        this.checkpointTimes = checkpointTimes;
    }

    public LocalTime calculateAdjustedElapsedTime() {
        return adjustedElapsedTime;
    }

    
}
