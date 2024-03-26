package cycling;

/**
 * This enum is used to represent the states that a stage can be in.
 * 
 * @author Olly Johnson, Laith Al-Qudah
 * @version 1.0
 *
 */
public enum StageState {
	
	/**
	 * Used when the stage is under construction and checkpoints can still be added.
	 */
	CONSTRUCTING,
	
	/**
	 * Used when the construction has finished and results can be added.
	 */
	WAITING_FOR_RESULTS,
}
