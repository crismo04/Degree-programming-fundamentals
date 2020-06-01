package simulator.model;

import java.util.List;
import simulator.misc.Vector;

//la fuerza de un cuerpo es la suma de las que se le aplican

public class NewtonUniversalGravitatiom implements GravityLaws{
	Vector fuerza;
	int dim;
	static private final double G = 6.67E-11;
	
	public NewtonUniversalGravitatiom () {
		dim = 2;
		fuerza = new Vector(2);
	}
    public NewtonUniversalGravitatiom (int n) {
    	dim = n;
    	fuerza = new Vector(dim);
	}
    
    public String toString() {
    	return "ley de gravitacion universal de newton";
    }

	public void apply( List<Body> bodies) {
		for(int i = 0; i < bodies.size(); i++) { 		// para toda la lista de cuerpos
			for(int j = 0; j < bodies.size(); j++){ 	//calculamos la fuerza de todos los cuerpos hacia i
				if(bodies.get(j).masa != 0 && j != i)	// si la masa es 0 o es el mismo cuerpo no hay que cambiar el vector
					// fuerza = la suma a la fuerzas de la posicion j -  la posicion 	i  como direccion multiplicado por formula
					fuerza = fuerza.plus((bodies.get(j).getPosicion().minus(bodies.get(i).getPosicion()).direction().scale(/*formula de fuerza*/(G*bodies.get(i).getMasa()*bodies.get(j).getMasa())/Math.pow(bodies.get(i).posicion.distanceTo(bodies.get(j).posicion),2))));
			}
			bodies.get(i).setAceleracion((fuerza.scale(1/bodies.get(i).getMasa())));
	    	fuerza = new Vector(dim); //pongo el vector fuerza a 0 para los siguientes cuerpos
		}
	}
}
