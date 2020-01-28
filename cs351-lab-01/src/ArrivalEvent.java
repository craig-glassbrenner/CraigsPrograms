import desmoj.core.simulator.*;

/**
 * This class represents the customer arrival event in the single server
 * queueing system.
 * @author Jason Sauppe
 * Last Modified: 2019-09-25
 */
public class ArrivalEvent extends Event<Customer> {

    /**
     * Event constructor.
     *
     * @param owner the model to which this event belongs
     * @param name the name of this event
     * @param showInTrace flag for event to produce output to trace file
     */
    public ArrivalEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    /**
     * The event logic for this event.
     */
    public void eventRoutine(Customer customer) {
        // get a reference to the model
        SSQModel model = (SSQModel) getModel();

        // Set customer's arrival time
        customer.arrivalTime = model.presentTime().getTimeAsDouble();

        if (model.serverIsIdle) {
            // Customer can begin service, so schedule their departure
            model.serverIsIdle = false;
            long serviceTime = model.serviceTimes.sample();
            DepartureEvent departure =
                    new DepartureEvent(model, "Departure", true);
            departure.schedule(customer, new TimeSpan(serviceTime));
            // Update stats
            model.numberBegunService += 1;
            model.sumOfWaitingTimes += 0; // No wait for this cust.
        } else {
            // Customer enters queue
            model.customerQueue.add(customer);
        }

        // Schedule the next arrival
        Customer nextCustomer = new Customer(model, "Customer", true);
        long interarrTime = model.interarrivalTimes.sample();
        ArrivalEvent nextArrival = new ArrivalEvent(model, "Arrival", true);
        nextArrival.schedule(nextCustomer, new TimeSpan(interarrTime));
    }

}

