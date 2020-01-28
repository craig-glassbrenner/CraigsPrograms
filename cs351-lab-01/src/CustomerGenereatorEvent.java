import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class CustomerGenereatorEvent extends ExternalEvent {

	public CustomerGenereatorEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
	}

	@Override
	public void eventRoutine() throws SuspendExecution {
		SSQModel model = (SSQModel) getModel();
		
		TimeSpan interarrSpan = new TimeSpan(model.interarrivalTimes.sample());
		
		Customer customer = new Customer(model, "Customer", true);
		ArrivalEvent arrival = new ArrivalEvent(model, "Arrival", true);
		arrival.schedule(customer, interarrSpan);
		
		CustomerGenereatorEvent nextGen = new CustomerGenereatorEvent(model, "Generator", true);
		nextGen.schedule(interarrSpan);
	}

}
