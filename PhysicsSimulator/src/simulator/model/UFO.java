package simulator.model;

import simulator.misc.Vector;
import java.util.Random;
/* Spaceships built by aliens, fly around, always have the same mass and 
 * teleport each portFrequency to a nearby random position
 */
public class UFO extends Body {
	
	private Random teleport;
	private double lossFrequency; //teleport frequency
	private double cont;
	
	public UFO(String id, Vector vel, Vector acel, Vector pos, double freq) {
		super(id, 1.0E05, vel, acel, pos);	
		lossFrequency = freq;
		teleport = new Random();
		cont = 0.0;
	}
	
	//moves the body and transports it randomly at most four times its distance of the center
	protected void move(double t) {
		super.move(t);
		cont += t;
		if( cont >= lossFrequency) {
			position.scale((teleport.nextDouble()%3.5) +0.5);
			cont = 0.0;
		}
	}
}
