//Craig Glassbrenner
//Condition to not take anymore customers after certain time
import desmoj.core.simulator.*;

public class ClinicClosedCondition extends ModelCondition {

	public ClinicClosedCondition(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
	}

	public boolean check() {
		MedicalClinic model = (MedicalClinic) getModel();
		
		if(model.presentTime().getTimeAsDouble() >= 720) {
			return true;
		}
		return false;
	}

}
