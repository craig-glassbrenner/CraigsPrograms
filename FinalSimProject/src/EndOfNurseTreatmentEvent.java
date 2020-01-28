import desmoj.core.simulator.*;

public class EndOfNurseTreatmentEvent extends Event<Patient> {

	public EndOfNurseTreatmentEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
	}

	public void eventRoutine(Patient patient) {
		MedicalClinic model = (MedicalClinic) getModel();
		
		model.numPatientsNurse++;
		double referredToSpec = model.patientSeesSpec.sample();
		double time = model.presentTime().getTimeAsDouble();
		
		if(model.specialistIsIdle && (referredToSpec <= 0.4) && (time - patient.arrivalTime < 30)) {
			model.specialistIsIdle = false;
//			model.roomsInUse++;
			patient.specNum = 1;
			
			double treatmentTime = model.specTreatmentTimes.sample();
			model.totalTimeBusySpecialist = model.totalTimeBusySpecialist + treatmentTime;
			
			EndOfTreatmentSpecialist EOS = new EndOfTreatmentSpecialist(model, "Specialist Treatment", true);
			EOS.schedule(patient, new TimeSpan(treatmentTime));
			
		} 
		//CONFIG #2
//		else if(model.specialist2IsIdle && (referredToSpec <= 0.4) && (time-patient.arrivalTime < 30)) {
//			model.specialist2IsIdle = false;
//			model.roomsInUse++;
//			patient.specNum = 2;
//			
//			
//			double treatmentTime = model.specTreatmentTimes.sample();
//			model.totalTimeBusySpecialist = model.totalTimeBusySpecialist + treatmentTime;
//			
//			EndOfTreatmentSpecialist EOS = new EndOfTreatmentSpecialist(model, "Specialist Treatment", true);
//			EOS.schedule(patient, new TimeSpan(treatmentTime));
//		}
//		
//		else if(model.specialist3IsIdle && (referredToSpec <= 0.4) && (time-patient.arrivalTime < 30)) {
//			model.specialist3IsIdle = false;
//			model.roomsInUse++;
//			patient.specNum = 3;
//			
//			
//			double treatmentTime = model.specTreatmentTimes.sample();
//			model.totalTimeBusySpecialist = model.totalTimeBusySpecialist + treatmentTime;
//			
//			EndOfTreatmentSpecialist EOS = new EndOfTreatmentSpecialist(model, "Specialist Treatment", true);
//			EOS.schedule(patient, new TimeSpan(treatmentTime));
//			
//		}
		
		else if(model.specialistIsIdle == false &&/* model.specialist2IsIdle == false && model.specialist3IsIdle */
				(referredToSpec <= 0.4) && (time - patient.arrivalTime < 30)) {
			if(model.specialistWaitingLine.length() >= 3) {
				model.numEmergencyNurse++;
			} 
//			if(model.roomsInUse >= 3) {
//				model.numEmergencyNurse++;
//			}
			else {
//				model.roomsInUse++;
				model.specialistWaitingLine.insert(patient);
			}
		} else if((referredToSpec <= 0.4) && (time-patient.arrivalTime >= 30)) {
			model.numEmergencyNurse++;
		}
		
		if(!model.nurseWaitingLine.isEmpty()) {
			Patient nextPatient = model.nurseWaitingLine.removeFirst();
			
//			if(patient.nurseNum == 1) {
//				nextPatient.nurseNum = 1;
//			} 
//			else if(patient.nurseNum == 2) {
//				nextPatient.nurseNum = 2;
//			} else if(patient.nurseNum == 3) {
//				nextPatient.nurseNum = 3;
//			}
			
			double nurseTreatment = model.nurseTreatmentTimes.sample();
			model.totalTimeBusyNurse = model.totalTimeBusyNurse + nurseTreatment;
			
			EndOfNurseTreatmentEvent EOT = new EndOfNurseTreatmentEvent(model, "Nurse Treatment", true);
			EOT.schedule(nextPatient, new TimeSpan(nurseTreatment));
		} else {
//			if(patient.nurseNum == 1) {
			model.nurseIsIdle = true;
//			} 
//			else if(patient.nurseNum == 2) {
//				model.nurse2IsIdle = true; 
//			} 
//			else {
//				model.nurse3IsIdle = true;
//			}
		}
		
	}

}
