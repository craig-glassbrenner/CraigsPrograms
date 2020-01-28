import desmoj.core.simulator.*;
import java.util.concurrent.TimeUnit;

/**
 * The restocking event.
 * @author Jason Sauppe
 * Last Modified: 2019-09-27
 */
public class RestockEvent extends ExternalEvent {

    private int productAmount;

    /**
     * Event constructor.
     *
     * @param owner the model this event belongs to
     * @param name this event's name
     * @param showInTrace flag for event to produce output in trace
     * @param amount The amount of product being ordered.
     */
    public RestockEvent(Model owner, String name, boolean showInTrace,
                        int amount) {
        super(owner, name, showInTrace);
        this.productAmount = amount;
    }

    /**
     * This eventRoutine() describes what happens when new product arrives
     * from the supplier.
     */
    public void eventRoutine() {
        // get a reference to the model
        InventoryModel model = (InventoryModel) getModel();

        // Update inventory with new stock (handle backorders first)
        if (model.pendingBackorders <= this.productAmount) {
            model.inventoryLevel +=
                    (this.productAmount - model.pendingBackorders);
            model.pendingBackorders = 0;
            // Update statistical trackers
            model.inventoryLevelAccum.update(model.inventoryLevel);
            model.series.update(model.inventoryLevel);
        } else {
            // Satisfy the backorders that we can
            model.pendingBackorders -= this.productAmount;
        }
    }

}

