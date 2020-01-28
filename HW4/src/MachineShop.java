// Craig Glassbrenner, HW4
// Max # of Parts = 13
// Max. Response for Parts = 84.115, Avg. Response Time = 16.19
// Max Length of MachineQueue = 1, Avg. Length of Machine Queue = 0.082
// Max Length of InspectorQueue = 1, Avg. Length of Inspector Queue = 0.80
// Utilization Rate for Machine = 74.7% , Utilization Rate for Inspector = 17.1%

import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import desmoj.core.statistic.*;
import java.util.concurrent.TimeUnit;

public class MachineShop extends Model {

	protected ContDistExponential interarrivalTimes;
	protected ContDistExponential processingTimes;
	protected ContDistNormal inspectionTimes;
	protected ContDistExponential refiningTimes;
	protected BoolDistBernoulli needRefine;
	
	protected Count maxParts;
	protected double utilMachine = 0;
	protected double utilInspector = 0;
	protected double minResponseTime = 10000;
	
	protected ProcessQueue<Customer> customerQueue;
	protected ProcessQueue<Machine> machineQueue;
	protected ProcessQueue<Inspector> inspectionQueue;
	
	public MachineShop(Model owner, String modelName, boolean showInReport, boolean showInTrace) {
		super(owner, modelName, showInReport, showInTrace);
		
	}

	@Override
	public String description() {
		
		return "This model describes a simple single machine shop.";
	}

	public void doInitialSchedules() {
		CustomerGenerator generator = new CustomerGenerator(this, "Generator", true);
		generator.activate();
		
		Machine machine = new Machine(this, "Machine", true);
		machine.activate();
		
		Inspector inspector = new Inspector(this, "Inspector", true);
		inspector.activate();
	}

	public void init() {
		interarrivalTimes = new ContDistExponential(this, "Arrival Times", 7, true, false);
		processingTimes = new ContDistExponential(this, "Process Times", 4.5, true, false);
		inspectionTimes = new ContDistNormal(this, "Inspection Times", 5, 1, true, false);
		refiningTimes = new ContDistExponential(this, "Refine Times", 3, true, false);
		needRefine = new BoolDistBernoulli(this, this.description(), .25, true, false);
		
		maxParts = new Count(this, "Number in system", true, false);
		
		machineQueue = new ProcessQueue<Machine>(this, "Machine Queue", true, false);
		inspectionQueue = new ProcessQueue<Inspector>(this, "Inspection Queue", true, false);
		customerQueue = new ProcessQueue<Customer>(this, "Customer Queue", true, false);
		
	}
	
	public static void main(String[] args) {
		Experiment.setReferenceUnit(TimeUnit.MINUTES);
		
		MachineShop model = new MachineShop(null, "Machine Shop Queue with DESMO-J Events.", true, true);
		Experiment exp = new Experiment("MachineShopExperiment");
		
		model.connectToExperiment(exp);
		
		exp.setShowProgressBar(false);
		exp.stop(new TimeInstant(24, TimeUnit.HOURS));
		
		exp.tracePeriod(new TimeInstant(0, TimeUnit.MINUTES), new TimeInstant(60, TimeUnit.MINUTES));
		exp.debugPeriod(new TimeInstant(0, TimeUnit.MINUTES), new TimeInstant(60, TimeUnit.MINUTES));
		
		exp.start();
		exp.report();
		
		double totalTime = 24*60;
		model.utilMachine = (model.utilMachine / totalTime);
		model.utilInspector = (model.utilInspector / totalTime);
		System.out.println("Utilization rate of Process Queue: " + model.utilMachine);
		System.out.println("Utilization rate of Inspector Queue: " + model.utilInspector);
		
		exp.finish();
		
	}

}
