import desmoj.core.simulator.*;

/**
 * This class represents the customer departure event in the single server
 * queueing system.
 * @author Jason Sauppe
 * Last Modified: 2019-09-25
 */
public class DepartureEvent extends Event<Customer> {

    /**
     * Event constructor.
     *
     * @param owner the model to which this event belongs
     * @param name the name of this event
     * @param showInTrace flag for event to produce output to trace file
     */
    public DepartureEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    /**
     * The event logic for this event.
     */
    public void eventRoutine(Customer customer) {
        // get a reference to the model
        SSQModel model = (SSQModel) getModel();

        // Print a message about the customer's departure to the trace
        model.sendTraceNote(customer + " leaves the system");

        // Check if there are other customers waiting
        if (!model.customerQueue.isEmpty()) {
            // remove the first waiting customer from the queue
            Customer nextCustomer = model.customerQueue.remove();

            long serviceTime = model.serviceTimes.sample();
            DepartureEvent nextDeparture =
                    new DepartureEvent(model, "Departure", true);
            nextDeparture.schedule(nextCustomer, new TimeSpan(serviceTime));
            // Update stats
            double curTime = model.presentTime().getTimeAsDouble();
            model.numberBegunService += 1;
            model.sumOfWaitingTimes += (curTime - nextCustomer.arrivalTime);
        } else {
            // No customers in queue to serve, so server idles
            model.serverIsIdle = true;
        }
    }

}

