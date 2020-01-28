import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import java.util.concurrent.TimeUnit;
import java.util.LinkedList;

/**
 * A model for a single server queueing system based on the design model from
 * Lecture 03-1 in class. It utilizes some basic features in DESMO-J but does
 * not take full advantage of everything that DESMO-J provides.
 * @author Jason Sauppe
 * Last Modified: 2019-09-25
 */
public class SSQModel extends Model {

    /** State variables for our model. */
    protected boolean serverIsIdle;

    /** Structures used in our model. */
    protected java.util.Queue<Customer> customerQueue;

    /**
     * We'll use two distributions for random numbers, one for service times
     * and one for interarrival times. These will also be initialized in the
     * init() method.
     */
    protected DiscreteDistEmpirical<Integer> serviceTimes;
    protected DiscreteDistUniform interarrivalTimes;

    /**
     * Statistical counters for our model (ignoring DESMO-J's built-ins).
     */
    protected int numberBegunService;
    protected double sumOfWaitingTimes;

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
     * Schedules the initial events on the future events list.
     */
    public void doInitialSchedules() {
        // Create the first customer entity
        Customer customer = new Customer(this, "Customer", true);

        // Sample an interarrival time
        long interarrTime = this.interarrivalTimes.sample();

        // Create and schedule the first CustomerArrivalEvent
        ArrivalEvent arrival = new ArrivalEvent(this, "Arrival", true);
        arrival.schedule(customer, new TimeSpan(interarrTime));
    }

    /**
     * Initializes static model components like state variables, structures
     * (e.g., queues), statistical trackers, and distributions (sources of
     * randomness).
     */
    public void init() {
        // Initialize our state variables and structures
        serverIsIdle = true;
        customerQueue = new LinkedList<>();

        // Initialize our statistical counters
        numberBegunService = 0;
        sumOfWaitingTimes = 0;

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
    public static void main(java.lang.String[] args) {
        // Set reference units to be in minutes
        Experiment.setReferenceUnit(TimeUnit.MINUTES);

        // Create model and experiment
        SSQModel model = new SSQModel(null,
                "Single Server Queue with DESMO-J Events (v1)",
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

        // Manual printing of the statistics that were collected
        System.out.format("Statistics: N = %d, S = %f\n",
                model.numberBegunService, model.sumOfWaitingTimes);
        System.out.format("Avg. Waiting Time: %.3f\n",
                model.sumOfWaitingTimes / model.numberBegunService);

        // Stop all threads still alive and close all output files
        exp.finish();
    }

}

