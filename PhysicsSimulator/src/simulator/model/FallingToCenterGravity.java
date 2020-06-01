package simulator.model;

import java.util.List;

public class FallingToCenterGravity implements GravityLaws {
	final static double G = -9.81;
	public FallingToCenterGravity() {} 
	
	public String toString() {
	    return "al centro del simulador";
	}
	
	public void apply( List<Body> bodies) {
		for(int i = 0; i < bodies.size(); i++) {	//para la lista de cuerpos
			// tiene una acceleracion de 9.81 hacia la direccion 00
			bodies.get(i).setAceleracion((bodies.get(i).getPosicion().direction()).scale(G));
		}
	}
	
}
