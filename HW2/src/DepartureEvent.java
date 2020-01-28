//Craig Glassbrenner
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class DepartureEvent extends Event<Customer> {

	public DepartureEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eventRoutine(Customer customer) {
		MachineShop model = (MachineShop) getModel();
		
		model.sendTraceNote(customer + " leaves system.");
		
		double totalCustomerTime = model.presentTime().getTimeAsDouble() - customer.arrivalTime;
		String time = Double.toString(totalCustomerTime);
		model.avgResponseTime.add(time);
		model.maxNumParts.update(-1);
		
		if(model.maxResponseTime < totalCustomerTime) {
			model.maxResponseTime = totalCustomerTime;
		} if(model.minResponseTime > totalCustomerTime) {
			model.minResponseTime = totalCustomerTime;
		}
		
		if(!model.customerInspectorQueue.isEmpty()) {
			Customer nextCustomer = model.customerInspectorQueue.remove();
			
			model.avgLengthInspector.update(model.customerInspectorQueue.size());
			
			double inspectionTime = model.inspectionTimes.sample();
			DepartureEvent nextDeparture = new DepartureEvent(model, "Departure", true);
			nextDeparture.schedule(nextCustomer, new TimeSpan(inspectionTime));
			
		} else {
			model.inspectorIsIdle = true;
		}
	}
}
