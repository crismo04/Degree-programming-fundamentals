package simulator.model;

import simulator.misc.Vector;

public class MassLossingBody extends Body {
	
	private double lossFactor; // between 0 & 1 
	private double lossFrequency; //for lose mass
	private double cont;
	
	public MassLossingBody(String id, double mass, Vector vel, Vector acel, Vector pos, double freq, double factor) {
		super(id, mass, vel, acel, pos);	
		lossFactor = factor;
		lossFrequency = freq;
		cont = 0.0;
	}
	
	//move the body and subtract the mass
	protected void move(double t) {
		super.move(t);
		cont += t;
		//if the time is too long it loses more mass (remove the loop to lose once each t)
		while( cont >= lossFrequency) {
			mass -= lossFactor;
			cont -= lossFrequency;
		}	
	}	
}
