import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import desmoj.core.statistic.*;
import java.util.concurrent.TimeUnit;

/**
 * A model for a simple inventory system with one product.
 * @author Jason Sauppe
 * Last Modified: 2019-09-27
 */
public class InventoryModel extends Model {

    /** Policy constants */
    public static final int MAX_INVENTORY = 80;
    public static final int REORDER_THRESHOLD = 20;
    public static final int REVIEW_PERIOD = 30;

    /** Data constants */
    public static final double MEAN_DAYS_BETWEEN_ORDERS = 1.0;
    public static final int MIN_LEAD_TIME = 15;
    public static final int MAX_LEAD_TIME = 29;

    /**
     * State variables
     */
    protected int inventoryLevel;
    protected int pendingBackorders;

    /**
     * Distributions
     */
    protected ContDistExponential demandInterarrivalTimes;
    protected DiscreteDistUniform restockLeadTimes;

    /**
     * Statistical counters for our model (using DESMO-J's built-ins).
     */
    protected Count totalBackorders;
    protected Accumulate inventoryLevelAccum;

    /**
     * We can use DESMO-J's TimeSeries to generate a plot of the inventory
     * level over time. The series will be updated just like the Accumulate.
     */
    protected TimeSeries series;

    /**
     * Model constructor, which creates a new model by passing parameters
     * to the superclass constructor.
     *
     * @param owner the model that manages this one (null if no such model)
     * @param modelName this model's name
     * @param showInReport flag to produce output to report file
     * @param showInTrace flag to produce output to trace file
     */
    public InventoryModel(Model owner, String modelName,
                          boolean showInReport, boolean showInTrace) {
        super(owner, modelName, showInReport, showInTrace);
    }

    /**
     * Returns a description of the model to be used in the report.
     * @return model description as a string
     */
    public String description() {
        return "This model describes a simple inventory system.";
    }

    /**
     * Creates the initial events on the future events list.
     */
    public void doInitialSchedules() {
        // Create the recurring inventory review event
        InventoryReviewEvent review =
                new InventoryReviewEvent(this, "Review", true);
        review.schedule();
        // NOTE: Scheduled for time 0 to update the Accumulate initially

        // Sample an order interarrival time
        double interarrTime = this.demandInterarrivalTimes.sample();
        DemandEvent order = new DemandEvent(this, "Demand", true);
        order.schedule(new TimeSpan(interarrTime));
    }

    /**
     * Initializes static model components like state variables, structures,
     * distributions, and statistical trackers.
     */
    public void init() {
        // Initialize our state variables
        inventoryLevel = InventoryModel.MAX_INVENTORY;
        pendingBackorders = 0;

        // Initialize our statistical counters
        totalBackorders = new Count(this, "Total Backorders", true, false);
        inventoryLevelAccum = new Accumulate(this, "Accumulate Inv Level",
                inventoryLevel, true, false);

        // Initialize the random number streams
        demandInterarrivalTimes = new ContDistExponential(this,
                "Demand Interarrival Times", MEAN_DAYS_BETWEEN_ORDERS,
                true, false);
        restockLeadTimes = new DiscreteDistUniform(this,
                "Lead Time Stream", MIN_LEAD_TIME, MAX_LEAD_TIME, true, false);

        // Useful for plotting the inventory levels (here for four months)
        series = new TimeSeries(this, "TimeSeries", "inv-data.dat",
                                new TimeInstant(0), new TimeInstant(120),
                                true, false);
    }

    /**
     * Runs the model.
     *
     * @param args is an array of command-line arguments (ignored here)
     */
    public static void main(java.lang.String[] args) {
        // Set reference units to be in minutes
        Experiment.setReferenceUnit(TimeUnit.DAYS);

        // Create model and experiment
        InventoryModel model = new InventoryModel(null,
                "Inventory System with DESMO-J Events (v2)",
                true, true);
        Experiment exp = new Experiment("InventoryExperiment");

        // connect both
        model.connectToExperiment(exp);

        // Set experiment parameters
        exp.setShowProgressBar(false);  // display a progress bar (or not)
        exp.stop(new TimeInstant(360, TimeUnit.DAYS));
        // Set the period of the trace and debug
        exp.tracePeriod(new TimeInstant(0, TimeUnit.DAYS),
                        new TimeInstant(40, TimeUnit.DAYS));
        exp.debugPeriod(new TimeInstant(0, TimeUnit.DAYS),
                        new TimeInstant(40, TimeUnit.DAYS));

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

