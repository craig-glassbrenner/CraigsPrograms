//Craig Glassbrenner, HW2

// Max Response Time = 39.1215 min. Min Response Time = 6.029 min Avg Response Time = 16.1187 min
// Max Length Machine = 2, Max Length Inspector = 3
// Avg Length Machine = .2383, Avg Length Inspector = 0.784
// Max Parts in system at once = 6

import desmoj.core.simulator.*;
import desmoj.core.statistic.Count;
import desmoj.core.statistic.Tally;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import desmoj.core.dist.*;

public class MachineShop extends Model {
	
	/** State Variables **/
	protected boolean machineIsIdle;
	protected boolean inspectorIsIdle;
	
	/** Structures. **/
	protected java.util.Queue<Customer> customerMachineQueue;
	protected java.util.Queue<Customer> customerInspectorQueue;
	
	
	/** Distributions **/
	protected ContDistUniform interarrivalTimes;
	protected ContDistUniform processingTimes;
	protected ContDistUniform inspectionTimes;
	protected ContDistUniform refiningTimes;
	protected BoolDistBernoulli needRefine;
	
	/** Stat Counters **/
	protected Count maxNumParts;
	double minResponseTime;
	double maxResponseTime;
	LinkedList<String> avgResponseTime;
	protected Tally avgLengthMachine;
	protected Tally avgLengthInspector;
	
	public static void main(java.lang.String[] args) {
		Experiment.setReferenceUnit(TimeUnit.MINUTES);
		
		MachineShop model = new MachineShop(null, "Machine Shop Queue with DESMO-J Events.", true, true);
		Experiment exp = new Experiment("MachineShopExperiment");
		
		model.connectToExperiment(exp);
		
		exp.setShowProgressBar(false);
		exp.stop(new TimeInstant(24, TimeUnit.HOURS));
		
		exp.tracePeriod(new TimeInstant(0, TimeUnit.MINUTES), new TimeInstant(5, TimeUnit.HOURS));
		exp.debugPeriod(new TimeInstant(0, TimeUnit.MINUTES), new TimeInstant(2, TimeUnit.HOURS));
		
		exp.start();
		
		exp.report();
		
		int toDivide = model.avgResponseTime.size();
		double sum = 0;
		while(model.avgResponseTime.size() > 0) {
			double toAdd = Double.parseDouble(model.avgResponseTime.poll());
			sum = sum + toAdd;
		}
		
		double avg = sum / (double) toDivide;
		
		//PRINT STATS
		System.out.println("Max Length of Machine Queue: " + model.avgLengthMachine.getMaximum());
		System.out.println("Max length of Inspector Queue: " + model.avgLengthInspector.getMaximum());
		System.out.println("Average Length of Machine Queue: " + model.avgLengthMachine.getMean());
		System.out.println("Average Length of Inspector Queue: " + model.avgLengthInspector.getMean());
		System.out.println("Max response time: " + model.maxResponseTime);
		System.out.println("Min response time: " + model.minResponseTime);
		System.out.println("Avg response time: " + avg);
		System.out.println("Max number of Parts in the System at once: " + model.maxNumParts.getMaximum());
		
		
		exp.finish();
	}
	
	public MachineShop(Model owner, String modelName, boolean showInReport, boolean showInTrace) {
		super(owner, modelName, showInReport, showInTrace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String description() {
		
		return "This model describes a simple single machine shop system.";
	}

	@Override
	public void doInitialSchedules() {
		Customer customer = new Customer(this, "Customer", true);
		double interarrTime = this.interarrivalTimes.sample();
		
		ArrivalEvent arrival = new ArrivalEvent(this, "Arrival", true);
		arrival.schedule(customer, new TimeSpan(interarrTime));
	}

	@Override
	public void init() {
		machineIsIdle = true;
		inspectorIsIdle = true;
		customerMachineQueue = new LinkedList<>();
		customerInspectorQueue = new LinkedList<>();
		
		//Stats HERE
		maxNumParts = new Count(this, "Number in sytem", true, false);
		minResponseTime = 1000;
		maxResponseTime = 0;
		avgResponseTime = new LinkedList<String>();
		avgLengthMachine = new Tally(this, "Machine Queue Length", true, false);
		avgLengthInspector = new Tally(this, "Inspector Queue Length", true, false);
		
		interarrivalTimes = new ContDistUniform(this, "Interarrival Times", 2, 12, true, false);
		processingTimes = new ContDistUniform(this, "Processing Times", 3, 9, true, false);
		inspectionTimes = new ContDistUniform(this, "Inspection Times", 1, 9, true, false);
		refiningTimes = new ContDistUniform(this, "Refining Times", 2, 4, true, false);
		needRefine = new BoolDistBernoulli(this, this.description(), .25, true, false);
		
	}

}








