//Craig Glassbrenner
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class RefineEvent extends Event<Customer> {

	public RefineEvent(Model model, String name, boolean showInTrace) {
		super(model, name, showInTrace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eventRoutine(Customer customer) {
		MachineShop model = (MachineShop) getModel();
		model.sendTraceNote(customer + " enters refinement");
		
		double refineTime = model.refiningTimes.sample();
		DepartureEvent departure = new DepartureEvent(model, "Departed", true);
		
		departure.schedule(customer, new TimeSpan(refineTime));
			
		
	}
}
