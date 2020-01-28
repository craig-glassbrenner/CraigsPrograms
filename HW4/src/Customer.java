//Craig Glassbrenner

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class Customer extends SimProcess {

	protected double intTime;
	
	public Customer(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
	}

	public void lifeCycle() throws SuspendExecution {
		
		MachineShop model = (MachineShop) getModel();
		intTime = model.presentTime().getTimeAsDouble();
		model.maxParts.update();
		
		model.customerQueue.insert(this);
		
		if(!model.machineQueue.isEmpty()) {
			model.machineQueue.removeFirst().activate();
		}
		
		this.passivate();
		
        model.sendTraceNote(this + " leaves the system.");
        model.maxParts.update(-1);
        
	}

}
