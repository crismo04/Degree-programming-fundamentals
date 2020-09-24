package simulator.model;

import java.util.List;
import simulator.misc.Vector;

//the force of a body is the sum of those applied to it

public class NewtonUniversalGravitatiom implements GravityLaws{
	Vector force;
	int dim;
	static private final double G = 6.67E-11;
	
	public NewtonUniversalGravitatiom () {
		dim = 2;
		force = new Vector(2);
	}
    public NewtonUniversalGravitatiom (int n) {
    	dim = n;
    	force = new Vector(dim);
	}
    
    public String toString() {
    	return "newton's law of universal gravitation";
    }

	public void apply( List<Body> bodies) {
		for(int i = 0; i < bodies.size(); i++) { 		// for the entire body list
			for(int j = 0; j < bodies.size(); j++){ 	//calculate the force of all bodies towards i
				if(bodies.get(j).mass != 0 && j != i)	// if the mass is 0 or it is the same body, do not change the vector
					// force = the sum of the forces of position j minus position i as a direction, multiplied by the formula
					force = force.plus((bodies.get(j).getPosition().minus(bodies.get(i).getPosition()).direction().scale(/*force*/(G*bodies.get(i).getMass()*bodies.get(j).getMass())/Math.pow(bodies.get(i).position.distanceTo(bodies.get(j).position),2))));
			}
			bodies.get(i).setAcceleration((force.scale(1/bodies.get(i).getMass())));
	    	force = new Vector(dim); //I set the force vector to 0 for the following bodies
		}
	}
}
