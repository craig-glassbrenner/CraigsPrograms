import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

/**
 * The Customer process encapsulates all information and activities associated
 * with a customer in our queueing system.
 * @author Jason Sauppe
 * Last Modified: 2019-10-17
 */
public class Customer extends SimProcess {

    /** No attributes needed here for the Customer class */

    /**
     * Process constructor.
     *
     * @param owner the model to which this process belongs
     * @param name the name of this process
     * @param showInTrace flag for process to produce output to trace file
     */
    public Customer(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    /**
     * A customer's life cycle consists of arrival, a possible delay while
     * waiting for service, the activity of service itself, and then a
     * departure. In this model, each customer is also responsible for creating
     * and activating the next customer (to bootstrap the arrivals).
     */
    public void lifeCycle() throws SuspendExecution {
        // get a reference to the model
        SSQModel model = (SSQModel) getModel();
        
        model.numberInSystem.update();
        
        model.customerQueue.insert(this);
        
        if(!model.idleServerQueue.isEmpty()) {
        	model.idleServerQueue.removeFirst().activate();
        }
        
        this.passivate();
        
        model.sendTraceNote(this + " leaves the system.");
        model.numberInSystem.update(-1);
        
    }

}

