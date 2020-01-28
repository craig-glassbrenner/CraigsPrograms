// Craig Glassbrenner
import desmoj.core.simulator.*;

public class Patient extends Entity {
	
	// Saves when they show up, what nurse they see, and what specialist they see if they see one
	protected double arrivalTime;
	protected int nurseNum;
	protected int specNum;

	public Patient(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
	}

}
