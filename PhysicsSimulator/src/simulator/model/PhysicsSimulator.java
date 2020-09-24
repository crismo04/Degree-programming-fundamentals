package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {
	
	private GravityLaws laws;
	private double TimePerStep;	// time per each step
	private List<Body> bodies;		// body list
	private double time;			// time of the complete simulation
	private List<SimulatorObserver> observers;
	
	public PhysicsSimulator(GravityLaws GL, double txStep ) {
		if(txStep < 0 || GL == null) throw new IllegalArgumentException();
		TimePerStep = txStep;
		laws = GL;
		time = 0.0;
		bodies = new ArrayList<Body>();
		observers = new ArrayList<SimulatorObserver>();
	}
	
	//add a body if it is possible
	public void addBody(Body cuerpo) {	
		
		if(!bodies.contains(cuerpo))
			bodies.add(cuerpo);
			//onAddBody notification----------------------------------------------------------------------
			for(SimulatorObserver ob: observers)
				ob.onBodyAdded(bodies, cuerpo);
	}
	
	// apply one step of the simulation
	public void advance() {
		laws.apply(bodies);
		for(int i = 0; i < bodies.size(); i++)
			bodies.get(i).move(TimePerStep);
		time += TimePerStep;
		//onAdvance notification ----------------------------------------------------------------------
		for(SimulatorObserver ob: observers)
			ob.onAdvance(bodies, time);
	}
	
	public String toString() {
		String s ="{ \"time\": " + time + ", \"bodies\": [ ";
		for(int i = 0; i < bodies.size();i++) {
			s += bodies.get(i).toString();	
			if(i < bodies.size()-1)
				s += ", ";
		}
		s+= " ] }";
		return s;
	}
	
	//resets the time and bodies, notifying the MVC
	public void reset() {
		time = 0.0;
		bodies = new ArrayList<Body>();	
		//onReset notification ------------------------------------------------------------
		for(SimulatorObserver ob: observers)
			ob.onReset(bodies, time, TimePerStep, laws.toString());
	}
	
	//changes the time per step, notifying the MVC
	public void setDeltaTime(double txStep) throws IllegalArgumentException{
		if(txStep < 0) throw new IllegalArgumentException();
		TimePerStep = txStep;
		//onDeltaTimeChanged notification ----------------------------------------------------------------------
		for(SimulatorObserver ob: observers)
			ob.onDeltaTimeChanged(txStep);
	}
	
	//changes the laws of gravity, notifying the MVC
	public void setGravityLaws(GravityLaws GL) throws IllegalArgumentException{
		if(GL == null) throw new IllegalArgumentException();
		laws = GL;
		//onGravityLawsChanged notification ----------------------------------------------------------------------
		for(SimulatorObserver ob: observers)
			ob.onGravityLawChanged(GL.toString());
	}
	
	public void addObserver(SimulatorObserver o) {	
		if(!observers.contains(o))
			observers.add(o);
			//onAddObserver notification ----------------------------------------------------------------------
			o.onRegistraObserver(bodies, time, TimePerStep, laws.toString());
	}
	
}
