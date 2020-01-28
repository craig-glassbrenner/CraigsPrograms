import desmoj.core.simulator.*;

/**
 * The Customer entity encapsulates all information associated with a customer
 * in our queueing system.
 * @author Jason Sauppe
 * Last Modified: 2018-10-03
 */
public class Customer extends Entity {

    /**
     * Entity attributes.
     */
    protected double arrivalTime;

    /**
     * Entity constructor.
     *
     * @param owner the model to which this entity belongs
     * @param name the name of this entity
     * @param showInTrace flag for entity to produce output to trace file
     */
    public Customer(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

}

