import desmoj.core.simulator.*;
import java.util.concurrent.TimeUnit;

/**
 * An inventory review event.
 * @author Jason Sauppe
 * Last Modified: 2019-09-27
 */
public class InventoryReviewEvent extends ExternalEvent {

    /**
     * Event constructor.
     *
     * @param owner the model this event belongs to
     * @param name this event's name
     * @param showInTrace flag for event to produce output in trace
     */
    public InventoryReviewEvent(Model owner, String name,
                                boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    /**
     * This eventRoutine() describes what happens when inventory gets reviewed.
     */
    public void eventRoutine() {
        // get a reference to the model
        InventoryModel model = (InventoryModel) getModel();

        // Update the Accumulate; this ensures that it gets initialized at
        // the start of the simulation and therefore has the correct period
        // when computing the time-weighted average. This does lead to
        // redundant updates as the simulation progresses, but these have no
        // impact on the computed values.
        model.inventoryLevelAccum.update(model.inventoryLevel);
        model.series.update(model.inventoryLevel);

        if (model.inventoryLevel < InventoryModel.REORDER_THRESHOLD) {
            // Determine reorder amount
            int reorderAmount = InventoryModel.MAX_INVENTORY
                              - model.inventoryLevel + model.pendingBackorders;

            // Place restocking order with supplier
            RestockEvent restock = new RestockEvent(model,
                "Restocking", true, reorderAmount);

            // Schedule delivery of new product
            double leadTime = model.restockLeadTimes.sample();
            restock.schedule(new TimeSpan(leadTime));
        }

        // Reschedule the next inventory review after the review period
        InventoryReviewEvent nextReview =
                new InventoryReviewEvent(model, "Inventory Review", true);
        nextReview.schedule(new TimeSpan(InventoryModel.REVIEW_PERIOD));
    }

}

