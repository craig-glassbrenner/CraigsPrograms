//Craig Glassbrenner
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class InspectionEvent extends Event<Customer> {

	public InspectionEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eventRoutine(Customer customer) {
		
		MachineShop model = (MachineShop) getModel();
		model.sendTraceNote(customer + " enters inspection");
		
		if(model.inspectorIsIdle) {
			model.inspectorIsIdle = false;
			double inspectionTime = model.inspectionTimes.sample();
			
			boolean refine = model.needRefine.sample();
			if(refine) {
				RefineEvent refining = new RefineEvent(model, "Refining", true);
				refining.schedule(customer, new TimeSpan(inspectionTime));
				
			} else {
				DepartureEvent departure = new DepartureEvent(model, "Departure", true);
				departure.schedule(customer, new TimeSpan(inspectionTime));
			}
			
		} else {
			model.customerInspectorQueue.add(customer);
			model.avgLengthInspector.update(model.customerInspectorQueue.size());
		}
		
//		DepartureEvent departure = new DepartureEvent(model, "Departure", true);
//		departure.schedule(customer, new TimeSpan(inspectionTime));
		
		if(!model.customerMachineQueue.isEmpty()) {
			Customer nextCustomer = model.customerMachineQueue.remove();
			
			model.avgLengthMachine.update(model.customerMachineQueue.size());
			
			double processTime = model.processingTimes.sample();
			InspectionEvent nextInspection = new InspectionEvent(model, "Inspection", true);
			nextInspection.schedule(nextCustomer, new TimeSpan(processTime));
			
		} else {
			model.machineIsIdle = true;
		}
	}
}
