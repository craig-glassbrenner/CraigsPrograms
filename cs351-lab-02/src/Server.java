import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

public class Server extends SimProcess {

	public Server(Model model, String name, boolean showInTrace) {
		super(model, name, showInTrace);
	}

	@Override
	public void lifeCycle() throws SuspendExecution {
		SSQModel model = (SSQModel) getModel();
		
		while(true) {
			if(model.customerQueue.isEmpty()) {
				model.idleServerQueue.insert(this);
				this.passivate();
			} else {
				Customer customer = model.customerQueue.removeFirst();
				
				long serviceTime = model.serviceTimes.sample();
				this.hold(new TimeSpan(serviceTime));
				
				customer.activate();
			}
		}
		
	}

}
