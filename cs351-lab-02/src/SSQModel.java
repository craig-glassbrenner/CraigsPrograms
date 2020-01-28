import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import desmoj.core.statistic.*;
import java.util.concurrent.TimeUnit;

/**
 * A model for a single server queueing system that uses a process-oriented
 * modeling perspective.
 * @author Jason Sauppe
 * Last Modified: 2019-10-17
 */
public class SSQModel extends Model {

    /** State variables for our model. */
	protected ProcessQueue<Server> idleServerQueue;

    /** Structures used in our model. */
    protected ProcessQueue<Customer> customerQueue;

    /**
     * We'll use two distributions for random numbers, one for service times
     * and one for interarrival times.
     */
    protected DiscreteDistEmpirical<Integer> serviceTimes;
    protected DiscreteDistUniform interarrivalTimes;

    /**
     * Statistical trackers using DESMO-J's provided classes.
     */
    protected Count numberInSystem;

    /**
     * Model constructor.
     *
     * @param owner the model that manages this one (null if no such model)
     * @param modelName this model's name
     * @param showInReport flag to produce output to report file
     * @param showInTrace flag to produce output to trace file
     */
    public SSQModel(Model owner, String modelName,
                    boolean showInReport, boolean showInTrace) {
        super(owner, modelName, showInReport, showInTrace);
    }

    /**
     * Returns a description of the model to be used in the report.
     * @return model description as a string
     */
    public String description() {
        return "This model describes a simple single-server queueing system.";
    }

    /**
     * Creates the initial processes in the system and activates them.
     */
    public void doInitialSchedules() {
    	//Create and activate the customer generator
    	Generator generator = new Generator(this, "Generator", true);
    	generator.activate();
    	
    	Server server = new Server(this, "Server", true);
    	server.activate();
    }

    /**
     * Initializes static model components like state variables, structures
     * (e.g., queues), statistical trackers, and distributions (sources of
     * randomness).
     */
    public void init() {
        // Initialize our state variables and structures
        customerQueue = new ProcessQueue<>(this, "Customer Queue",
                                           true, false);

        idleServerQueue = new ProcessQueue<>(this, "Idle Server Queue", true, false);
        // Initialize the DESMO-J trackers
        numberInSystem = new Count(this, "Number in System", true, false);

        // Initialize the random number streams
        serviceTimes = new DiscreteDistEmpirical<>(this,
                "Service Times", true, false);
        serviceTimes.addEntry(1, 0.10);
        serviceTimes.addEntry(2, 0.20);
        serviceTimes.addEntry(3, 0.30);
        serviceTimes.addEntry(4, 0.25);
        serviceTimes.addEntry(5, 0.10);
        serviceTimes.addEntry(6, 0.05);
        interarrivalTimes = new DiscreteDistUniform(this,
                "Interarrival Times", 1, 8, true, false);
    }

    /**
     * Runs the model.
     *
     * @param args is an array of command-line arguments (ignored here)
     */
    public static void main(String[] args) {
        // Set reference units to be in minutes
        Experiment.setReferenceUnit(TimeUnit.MINUTES);

        // Create model and experiment
        SSQModel model = new SSQModel(null,
                "Single Server Queue with DESMO-J Processes (v1)",
                true, true);
        Experiment exp = new Experiment("SSQExperiment");

        // connect both
        model.connectToExperiment(exp);

        // Set experiment parameters
        exp.setShowProgressBar(false);  // display a progress bar (or not)
        exp.stop(new TimeInstant(8, TimeUnit.HOURS));
        // Set the period of the trace and debug
        exp.tracePeriod(new TimeInstant(0, TimeUnit.MINUTES),
                        new TimeInstant(60, TimeUnit.MINUTES));
        exp.debugPeriod(new TimeInstant(0, TimeUnit.MINUTES),
                        new TimeInstant(60, TimeUnit.MINUTES));

        // Start the experiment at simulation time 0.0
        exp.start();

        // --> now the simulation is running until it reaches its end criterion
        // ...
        // ...
        // <-- afterwards, the main thread returns here

        // Generate the report (and other output files)
        exp.report();

        // Stop all threads still alive and close all output files
        exp.finish();
    }

}

