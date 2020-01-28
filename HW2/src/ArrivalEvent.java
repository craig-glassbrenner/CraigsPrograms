//Craig Glassbrenner
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class ArrivalEvent extends Event<Customer> {

	public ArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eventRoutine(Customer customer) {
		MachineShop model = (MachineShop) getModel();
		
		customer.arrivalTime = model.presentTime().getTimeAsDouble();
		
		if(model.machineIsIdle) {
			model.machineIsIdle = false;
			double processTime = model.processingTimes.sample();
			InspectionEvent inspection = new InspectionEvent(model, "Inspection", true);
			
			inspection.schedule(customer, new TimeSpan(processTime));
			
			
			
		} else {
			model.customerMachineQueue.add(customer);
			model.avgLengthMachine.update(model.customerInspectorQueue.size());
		}
		
		model.maxNumParts.update();
		
		Customer nextCustomer = new Customer(model, "Customer", true);
		double interarrTime = model.interarrivalTimes.sample();
		ArrivalEvent nextArrival = new ArrivalEvent(model, "Arrival Time", true);
		nextArrival.schedule(nextCustomer, new TimeSpan(interarrTime));
		
	}
}
