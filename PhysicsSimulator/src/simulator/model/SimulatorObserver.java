package simulator.model;

import java.util.List;

//each method of the interface communicates an action
public interface SimulatorObserver {
	
	//send a notification to the newly registered observer
	public void onRegistraObserver(List<Body> bodies, double time, double dt, String gLawsDesc);
	
	//inform all observers
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc);
	public void onBodyAdded(List<Body> bodies, Body b);
	public void onAdvance(List<Body> bodies, double time);
	public void onDeltaTimeChanged(double dt);
	public void onGravityLawChanged(String gLawsDesc);
}
