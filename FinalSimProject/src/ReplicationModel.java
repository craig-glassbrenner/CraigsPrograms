//Craig Glassbrenner, Final Project, 12/11/2019

import java.util.concurrent.TimeUnit;
import desmoj.core.simulator.*;
import desmoj.core.statistic.ConfidenceCalculator;

public class ReplicationModel extends Model {

	public static final int NUM_REPLICATIONS = 100;
	
	/*Stat Totals*/
	protected ConfidenceCalculator ccDC;
	protected ConfidenceCalculator patientsTot;
	protected ConfidenceCalculator patientsBalk;
	protected ConfidenceCalculator patientsDiverted;
	protected ConfidenceCalculator patientsFullyTreated;
	protected ConfidenceCalculator responseTime;
	protected ConfidenceCalculator utilRateNurse;
	protected ConfidenceCalculator utilRateSpecialist;
	protected ConfidenceCalculator avgPatientsWaiting;
	
	public ReplicationModel(Model owner, String modelName, boolean showInReport, boolean showInTrace) {
		super(owner, modelName, showInReport, showInTrace);
	}

	public static void main(String[] args) {
		Experiment.setReferenceUnit(TimeUnit.MINUTES);
		
		ReplicationModel repModel = new ReplicationModel(null, "Replication Model for MedicalClinic", true, true);
		Experiment exp = new Experiment("MedicalClinicReplication");
		repModel.connectToExperiment(exp);
		
		exp.setShowProgressBar(false);
		exp.stop(new TimeInstant(0));
		exp.traceOff(new TimeInstant(0));
		exp.debugOff(new TimeInstant(0));
		exp.setSilent(true);
		
		exp.start();
		
		exp.report();
		exp.finish();
		
		System.out.println("Average/StdDev Daily Operating Costs: " + repModel.ccDC.getMean() + " " + repModel.ccDC.getStdDev());
		System.out.println("Average/StdDev number of Patients: " + repModel.patientsTot.getMean() + " " + repModel.patientsTot.getStdDev());
		System.out.println("Average/StdDev number of Patients Balk: " + repModel.patientsBalk.getMean() + " " + repModel.patientsBalk.getStdDev());
		System.out.println("Average/StdDev number of Patients Diverted: " + repModel.patientsDiverted.getMean() + " " + repModel.patientsDiverted.getStdDev());
		System.out.println("Average/StdDev number of Patients fully Treated: " + repModel.patientsFullyTreated.getMean() + " " + repModel.patientsFullyTreated.getStdDev());
		System.out.println("Average/StdDev Response Time: " + repModel.responseTime.getMean() + " " + repModel.responseTime.getStdDev());
		System.out.println("Average/StdDev Nures Utilization Rate: " + repModel.utilRateNurse.getMean() + " " + repModel.utilRateNurse.getStdDev());
		System.out.println("Average/StdDev Specialist Utilization Rate: " + repModel.utilRateSpecialist.getMean() + " " + repModel.utilRateSpecialist.getStdDev());
		System.out.println("Average/StdDev Number of Patients in Waiting Room: " + repModel.avgPatientsWaiting.getMean() + " " + repModel.avgPatientsWaiting.getStdDev());

	}

	public String description() {
		return "A model for running multiple experiments in DESMO-J.";
	}

	public void doInitialSchedules() {
		for(int i=1; i <= NUM_REPLICATIONS; i++) {
			boolean runSuccessful;
			do {
				runSuccessful = runSimulation(i);
				if(!runSuccessful) {
					System.out.format("Waiting for two seconds...\n");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						
					}
				}
			} while(!runSuccessful);
		}
	}

	public boolean runSimulation(int repNum) {
		
		MedicalClinic model = new MedicalClinic(null, "Medical Clinic with DESMO-J Events.", true, true);
		Experiment exp = new Experiment("MedicalClinicExperiment");
		exp.setSeedGenerator(979 + 2*repNum);
		
		model.connectToExperiment(exp);
		
		exp.setShowProgressBar(false);
		exp.stop(model.condition1);
		
		exp.tracePeriod(new TimeInstant(0, TimeUnit.MINUTES), new TimeInstant(5, TimeUnit.HOURS));
		exp.debugPeriod(new TimeInstant(0, TimeUnit.MINUTES), new TimeInstant(2, TimeUnit.HOURS));
		
		try {
			exp.start();
		} catch (Exception e) {
			System.out.format("WARNING: Rep %d: Exception during run." + "retrying...\n", repNum);
			exp.finish();
			return false;
		}
		
		if(exp.hasError() || exp.isAborted()) {
			System.out.format("WARNING: Rep %d: Error during run." + "retrying...\n", repNum);
			exp.finish();
			return false;
		}
		
		try {
			Thread.sleep(10);
		} catch(InterruptedException e) {
			
		}
		
		exp.report();
		exp.finish();
		model.dailyOperatingCost = (100*model.numPatientsNurse) + (200*model.numPatientsSpec) + (500*(model.numEmergencyNurse+model.numEmergencyRight));
		
		if(model.presentTime().getTimeAsDouble() < 720 || model.dailyOperatingCost < 0 || 
				model.numPatients < 0 || model.numEmergencyRight < 0 || model.numEmergencyNurse < 0 ||
				model.numPatientsNurse < 0 || model.numPatientsSpec < 0 || model.totalTimeBusyNurse < 0 ||
				model.totalTimeBusySpecialist < 0 || model.nurseWaitingLine.averageLength() < 0) {
			
			System.out.format("WARNING: Rep %d: Bad output values from run." + "retrying...\n", repNum);
			return false;
		}
		
		ccDC.update(model.dailyOperatingCost);
		patientsTot.update(model.numPatients);
		patientsBalk.update(model.numEmergencyRight);
		patientsDiverted.update(model.numEmergencyNurse);
		patientsFullyTreated.update(model.numPatientsSpec);
		
		double avg = model.avgResponseTime / model.numPatientsSpec;
		responseTime.update(avg);

		double RateNurse = model.totalTimeBusyNurse / model.presentTime().getTimeAsDouble();
		double RateSpec = model.totalTimeBusySpecialist / model.presentTime().getTimeAsDouble();
		utilRateNurse.update(RateNurse);
		utilRateSpecialist.update(RateSpec);
		
		double avgNumWaiting = model.nurseWaitingLine.averageLength();
		avgPatientsWaiting.update(avgNumWaiting);
		
		return true;
	}
	
	public void init() {
		ccDC = new ConfidenceCalculator(this, "Per Replication: Daily Cost", true, false);
		patientsTot = new ConfidenceCalculator(this, "Average Number of Patients", true, false);
		patientsBalk = new ConfidenceCalculator(this, "Average Number of Patients Balk", true, false);
		patientsDiverted = new ConfidenceCalculator(this, "Average Patients Diverted", true, false);
		patientsFullyTreated = new ConfidenceCalculator(this, "Patients Fully Treated", true, false);
		responseTime = new ConfidenceCalculator(this, "Response Time", true, false);
		utilRateNurse = new ConfidenceCalculator(this, "Utilization Rate Nurse", true, false);
		utilRateSpecialist = new ConfidenceCalculator(this, "Utilization Rate Specialist", true, false);
		avgPatientsWaiting = new ConfidenceCalculator(this, "Patients Waiting in Waiting Room", true, false);
		
	}

}
