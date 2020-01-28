//Craig Glassbrenner
//Generates Parts and performs lifecycle of part

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class CustomerGenerator extends SimProcess {

	public CustomerGenerator(Model model, String name, boolean showInTrace) {
		super(model, name, showInTrace);
		
	}

	public void lifeCycle() throws SuspendExecution {
		MachineShop model = (MachineShop) getModel();
		
		while(true) {
			double interArrSample = model.interarrivalTimes.sample();
			this.hold(new TimeSpan(interArrSample));
			
			Customer customer = new Customer(model, "Customer", true);
			customer.activate();
		}
	}

}
