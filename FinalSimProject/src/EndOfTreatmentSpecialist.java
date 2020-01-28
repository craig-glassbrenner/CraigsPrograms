//Craig Glassbrenner
import desmoj.core.simulator.*;
public class EndOfTreatmentSpecialist extends Event<Patient> {

	public EndOfTreatmentSpecialist(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
	}

	public void eventRoutine(Patient patient) {
		MedicalClinic model = (MedicalClinic) getModel();
//		model.roomsInUse--;
		
		if(!model.specialistWaitingLine.isEmpty()) {
			Patient nextPatient = model.specialistWaitingLine.removeFirst();
			
//			model.roomsInUse++;
//			if(patient.specNum == 1) {
//				nextPatient.specNum = 1;
//			} 
//			else if(patient.specNum == 2) {
//				nextPatient.specNum = 2;
//			} else if(patient.specNum == 3) {
//				nextPatient.specNum = 3;
//			} 
			
			double treatmentTime = model.specTreatmentTimes.sample();
			model.totalTimeBusySpecialist = model.totalTimeBusySpecialist + treatmentTime;
			
			EndOfTreatmentSpecialist eos = new EndOfTreatmentSpecialist(model, "Specialist Treatment", true);
			eos.schedule(nextPatient, new TimeSpan(treatmentTime));
		} else {
//			if(patient.specNum == 1) {
			model.specialistIsIdle = true;
//			} 
//			else if(patient.specNum == 2) {
//				model.specialist2IsIdle = true;
//			} else if(patient.specNum == 3) {
//				model.specialist3IsIdle = true;
//			} 
		}
		
		model.numPatientsSpec++;	
		model.avgResponseTime = model.avgResponseTime + (model.presentTime().getTimeAsDouble() - patient.arrivalTime);
		
	}


}
