package simulator.model;

import java.util.List;

public class FallingToCenterGravity implements GravityLaws {
	final static double G = -9.81;
	public FallingToCenterGravity() {} 
	
	public String toString() {
	    return "fall to the center";
	}
	
	public void apply( List<Body> bodies) {
		for(int i = 0; i < bodies.size(); i++) {	//for the body list
			// has an acceleration of 9.81 towards direction 00
			bodies.get(i).setAcceleration((bodies.get(i).getPosition().direction()).scale(G));
		}
	}
	
}
