package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body {
	
	private double lossFactor; // entre 0 y 1 
	private double lossFrequency; //para perder masa
	private double cont;
	
	public MassLossingBody(String id, double mass, Vector vel, Vector acel, Vector pos, double freq, double factor) {
		super(id, mass, vel, acel, pos);	
		lossFactor = factor;
		lossFrequency = freq;
		cont = 0.0;
	}
	
	//mueve al cuerpo y le resta masa
	protected void move(double t) {
		super.move(t);
		cont += t;
		/*segun el guion solo pierde una vex por movimiento, pero si el tiempo es muy grande
		deberia de perder mas masa, opino, si no, solo seria quitar el while */
		while( cont >= lossFrequency) {
			masa -= lossFactor;
			cont -= lossFrequency;
		}	
	}	
}
