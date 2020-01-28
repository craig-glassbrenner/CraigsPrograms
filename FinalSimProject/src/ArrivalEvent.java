//Craig Glassbrenner
import desmoj.core.simulator.*;

public class ArrivalEvent extends Event<Patient> {

	public ArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
	}

	public void eventRoutine(Patient patient) {
		MedicalClinic model = (MedicalClinic) getModel();
		
		patient.arrivalTime = model.presentTime().getTimeAsDouble();
		
		if(model.condition2.check()) {
			return;
		}
		model.numPatients++;
		
		if(model.nurseIsIdle) {
			patient.nurseNum = 1;
			
			model.nurseIsIdle = false;
			double treatmentTime = model.nurseTreatmentTimes.sample();
			model.totalTimeBusyNurse = model.totalTimeBusyNurse + treatmentTime;
			
			EndOfNurseTreatmentEvent nurse = new EndOfNurseTreatmentEvent(model, "Nurse Treatment", true);
			nurse.schedule(patient, new TimeSpan(treatmentTime));
		} 
		//OTHER CONFIG#1
//		else if(model.nurse2IsIdle) {
//			patient.nurseNum = 2;
//			
//			model.nurse2IsIdle = false;
//			double treatmentTime = model.nurseTreatmentTimes.sample();
//			
//			EndOfNurseTreatmentEvent nurse = new EndOfNurseTreatmentEvent(model, "Nurse Treatment", true);
//			nurse.schedule(patient, new TimeSpan(treatmentTime));
//		} 
//		else if(model.nurse3IsIdle) {
//			patient.nurseNum = 3;
//			
//			model.nurse3IsIdle = false;
//			double treatmentTime = model.nurseTreatmentTimes.sample();
//			
//			EndOfNurseTreatmentEvent nurse = new EndOfNurseTreatmentEvent(model, "Nurse Treatment", true);
//			nurse.schedule(patient, new TimeSpan(treatmentTime));
//		}
		else {
			int k = model.nurseWaitingLine.length();
			double rand = model.patientBalks.sample();
			
			if(rand <= (k/8)) {
				model.numEmergencyRight++;
			} else {
				model.nurseWaitingLine.insert(patient);
			}
		}
		
		Patient nextPatient = new Patient(model, "Patient", true);
		double interArrTime;
		if(model.presentTime().getTimeAsDouble() <= 120) {
			interArrTime = model.interArr810.sample();
			
		} else if(model.presentTime().getTimeAsDouble() > 120 && model.presentTime().getTimeAsDouble() <= 480) {
			interArrTime = model.interArr104.sample();
			
		} else {
			interArrTime = model.interArr48.sample();
		}
		
		ArrivalEvent nextArrival = new ArrivalEvent(model, "Arrival Time", true);
		nextArrival.schedule(nextPatient, new TimeSpan(interArrTime));
		
	}

}
