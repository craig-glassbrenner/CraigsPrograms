//Craig Glassbrenner
// Condition to make sure no one is left in the Medical Clinic
import desmoj.core.simulator.*;
public class EveryoneGoneCondition extends ModelCondition {

	public EveryoneGoneCondition(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
	}

	public boolean check() {
		MedicalClinic model = (MedicalClinic) getModel();
		
		if(model.nurseIsIdle &&/* model.nurse2IsIdle && model.nurse3IsIdle &&
				model.specialistIsIdle && model.specialist2IsIdle && model.specialist3IsIdle 
				&&*/ model.nurseWaitingLine.isEmpty() && model.specialistWaitingLine.isEmpty() 
				&& (model.presentTime().getTimeAsDouble() >= 720)) {
			return true;
		}
		return false;
	}

}
