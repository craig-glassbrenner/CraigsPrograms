//Craig Glassbrenner
import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.*;

public class MedicalClinic extends Model {

	/* State Variables*/
	protected boolean nurseIsIdle;
	protected boolean specialistIsIdle;
	
	/* Things Needed for Configuration#1*/
//	protected boolean nurse2IsIdle;
	
	/*Things Needed for Config#2*/
//	protected boolean specialist2IsIdle;
//	protected int roomsInUse;
	
	/*Optimal Config*/
//	protected boolean nurse2IsIdle;
//	protected boolean nurse3IsIdle;
//	protected boolean specialist2IsIdle;
//	protected boolean specialist3IsIdle;
//	protected boolean specialist4IsIdle;
	
	/*Structure*/
	protected desmoj.core.simulator.Queue<Patient> nurseWaitingLine;
	protected desmoj.core.simulator.Queue<Patient> specialistWaitingLine;
	
	/*Random Number Generators*/
	protected ContDistUniform patientBalks;
	protected ContDistUniform patientSeesSpec;
	
	/*Distributions*/
	protected ContDistExponential interArr810;
	protected ContDistExponential interArr104;
	protected ContDistExponential interArr48;
	protected ContDistExponential nurseTreatmentTimes;
	protected ContDistExponential specTreatmentTimes;
	
	/*Stat Counters*/
	protected int numPatients;
	protected int numPatientsNurse;
	protected int numPatientsSpec;
	protected int numEmergencyRight;
	protected int numEmergencyNurse;
	protected int dailyOperatingCost;
	protected double avgResponseTime;
	protected double totalTimeBusyNurse;
	protected double totalTimeBusySpecialist;
	
	/*Model Conditions*/
	protected EveryoneGoneCondition condition1;
	protected ClinicClosedCondition condition2;
	
	public MedicalClinic(Model owner, String modelName, boolean showInReport, boolean showInTrace) {
		super(owner, modelName, showInReport, showInTrace);
		
	}

	@Override
	public String description() {
		return "This model describes a walk-in medical clinic.";
	}

	@Override
	public void doInitialSchedules() {
		Patient patient = new Patient(this, "Patient", true);
		double interArrTime = this.interArr810.sample();
		
		ArrivalEvent arrival = new ArrivalEvent(this, "Arrival", true);
		arrival.schedule(patient, new TimeSpan(interArrTime));
		
	}

	public void init() {
		nurseIsIdle = true;
		specialistIsIdle = true;
		
		interArr810 = new ContDistExponential(this, "Arrival Times", 15, true, false);
		interArr104 = new ContDistExponential(this, "Arrival Times", 6, true, false);
		interArr48 = new ContDistExponential(this, "Arrival Times", 9, true, false);
		nurseTreatmentTimes = new ContDistExponential(this, "Nurse Treatment Time", 8, true, false);
		specTreatmentTimes = new ContDistExponential(this, "Specialist Treatment Times", 25, true, false);
		
		numPatientsNurse = 0;
		numPatientsSpec = 0;
		numEmergencyRight = 0;
		numEmergencyNurse = 0;
		dailyOperatingCost = 3900;
		numPatients = 0;
		avgResponseTime = 0;
		totalTimeBusyNurse = 0;
		totalTimeBusySpecialist = 0;
		
		nurseWaitingLine = new desmoj.core.simulator.Queue<Patient>(this, "Nurse Waiting Line", true, true);
		specialistWaitingLine = new desmoj.core.simulator.Queue<Patient>(this, "Specialist Wait Line", true, true);
		
		patientBalks = new ContDistUniform(this, "Patient Balks", 0, 1, true, true);
		patientSeesSpec = new ContDistUniform(this, "Patient Sees Specialist", 0, 1, true, true);
		
		condition1 = new EveryoneGoneCondition(this, "Everyone is Gone", true);
		condition2 = new ClinicClosedCondition(this, "Clinc is Closed", true);
		
		//OTHER CONFIG#1
//		nurse2IsIdle = true;
//		dailyOperatingCost = 5100;
		
		//OTHER CONFIG#2
//		specialist2IsIdle = true;
//		dailyOperatingCost = 5400;
//		roomsInUse = 0;
		
		//Optimal CONFIG
//		nurse2IsIdle = true;
//		nurse3IsIdle = true;
//		dailyOperatingCost = 11100;
//		specialist2IsIdle = true;
//		specialist3IsIdle = true;
	}

}
