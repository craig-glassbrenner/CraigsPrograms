import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class Generator extends SimProcess {

	public Generator(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
	}

	public void lifeCycle() throws SuspendExecution {
		SSQModel model = (SSQModel) getModel();
		
		while (true) {
			long interarrSample = model.interarrivalTimes.sample();
			
			this.hold(new TimeSpan(interarrSample));
			
			Customer customer = new Customer(model, "Customer", true);
			customer.activate();
		}
		
	}

}
