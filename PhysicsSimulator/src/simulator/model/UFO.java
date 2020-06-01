package simulator.model;

import simulator.misc.Vector;
import java.util.Random;
/* Naves espaciales construidas por los aliens, vuelan por ahi, siempre tienen la misma masa
 * y se teletransportan cada portFrequency a una pos aleatoria
 */
public class UFO extends Body {
	
	private Random teleport;
	private double lossFrequency; //para perder masa
	private double cont;
	
	public UFO(String id, Vector vel, Vector acel, Vector pos, double freq) {
		super(id, 1.0E05, vel, acel, pos);	
		lossFrequency = freq;
		teleport = new Random();
		cont = 0.0;
	}
	
	//mueve al cuerpo y le transporta aleatoriamente a como mucho el doble de su distancia
	protected void move(double t) {
		super.move(t);
		cont += t;
		if( cont >= lossFrequency) {
			posicion.scale((teleport.nextDouble()%1.5) +0.5);
			cont = 0.0;
		}
	}
}
