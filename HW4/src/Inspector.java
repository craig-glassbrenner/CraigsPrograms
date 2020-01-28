// Craig Glassbrenner

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class Inspector extends SimProcess {

	public Inspector(Model model, String name, boolean showInTrace) {
		super(model, name, showInTrace);
	
	}

	public void lifeCycle() throws SuspendExecution {
		MachineShop model = (MachineShop) getModel();
		
		while(true) {
			if(model.machineQueue.isEmpty()) {
				model.inspectionQueue.insert(this);
				this.passivate();
			} else {
				Machine machine = model.machineQueue.removeFirst();
				
				double inspectionTime = model.inspectionTimes.sample();
				this.hold(new TimeSpan(inspectionTime));
				model.utilInspector = model.utilInspector + inspectionTime;
				
				machine.activate();
				
				boolean refine = model.needRefine.sample();
				
				if(refine) {
					double refineTime = model.refiningTimes.sample();
					this.hold(new TimeSpan(refineTime));
				}
			}
		}
		
	}

}
