// Craig Glassbrenner
import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class Machine extends SimProcess {

	public Machine(Model model, String name, boolean showInTrace) {
		super(model, name, showInTrace);
		
	}

	public void lifeCycle() throws SuspendExecution {
		MachineShop model = (MachineShop) getModel();
		
		while(true) {
			if(model.customerQueue.isEmpty()) {
				model.machineQueue.insert(this);
				this.passivate();
			} else {
				Customer customer = model.customerQueue.removeFirst();
				
				double processTime = model.processingTimes.sample();
				this.hold(new TimeSpan(processTime));
				model.utilMachine = model.utilMachine + processTime;
				
				customer.activate();
				
				if(!model.inspectionQueue.isEmpty()) {
					model.inspectionQueue.removeFirst().activate();
				}
				
				model.sendTraceNote(this + " enters inspection.");
			}
		}
		
	}

}
