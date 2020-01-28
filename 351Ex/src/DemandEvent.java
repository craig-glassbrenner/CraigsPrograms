import desmoj.core.simulator.*;
import java.util.concurrent.TimeUnit;

/**
 * A demand event.
 * @author Jason Sauppe
 * Last Modified: 2019-09-27
 */
public class DemandEvent extends ExternalEvent {

    /**
     * Event constructor.
     *
     * @param owner the model this event belongs to
     * @param name this event's name
     * @param showInTrace flag for event to produce output in trace
     */
    public DemandEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    /**
     * This eventRoutine() describes what happens when demand occurs.
     */
    public void eventRoutine() {
        // get a reference to the model
        InventoryModel model = (InventoryModel) getModel();

        // Satisfy demand if inv available, otherwise backorder
        if (model.inventoryLevel > 0) {
            model.inventoryLevel -= 1;
            // Update statistical trackers
            model.inventoryLevelAccum.update(model.inventoryLevel);
            model.series.update(model.inventoryLevel);
        } else {
            model.pendingBackorders += 1;   // Update state variable
            model.totalBackorders.update(); // Update statistical tracker
        }

        // Schedule the next demand
        double interarrTime = model.demandInterarrivalTimes.sample();
        DemandEvent nextOrder = new DemandEvent(model, "Demand", true);
        nextOrder.schedule(new TimeSpan(interarrTime));
    }

}

