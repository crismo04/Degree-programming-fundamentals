package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {
	
	private GravityLaws leyes;
	private double tiempoPorPaso;	// tiempo que dura cada paso
	private List<Body> cuerpos;		// lista de cuerpos
	private double tiempo;			// tiempo que lleva la simulacion
	private List<SimulatorObserver> observadores;
	
	public PhysicsSimulator(GravityLaws leyGravedad, double tPorPaso ) {
		if(tPorPaso < 0 || leyGravedad == null) throw new IllegalArgumentException();
		tiempoPorPaso = tPorPaso;
		leyes = leyGravedad;
		tiempo = 0.0;
		cuerpos = new ArrayList<Body>();
		observadores = new ArrayList<SimulatorObserver>();
	}
	
	//añade un cuerpo si es posible
	public void addBody(Body cuerpo) {	
		
		if(!cuerpos.contains(cuerpo))
			cuerpos.add(cuerpo);
			//notificacion onAddBody----------------------------------------------------------------------
			for(SimulatorObserver ob: observadores)
				ob.onBodyAdded(cuerpos, cuerpo);
	}
	
	// aplica un paso de la simulacion
	public void advance() {
		leyes.apply(cuerpos);
		for(int i = 0; i < cuerpos.size(); i++)
			cuerpos.get(i).move(tiempoPorPaso);
		tiempo += tiempoPorPaso;
		//notificacion onAdvance----------------------------------------------------------------------
		for(SimulatorObserver ob: observadores)
			ob.onAdvance(cuerpos, tiempo);
	}
	
	public String toString() {
		String s ="{ \"time\": " + tiempo + ", \"bodies\": [ ";
		for(int i = 0; i < cuerpos.size();i++) {
			s += cuerpos.get(i).toString();	
			if(i < cuerpos.size()-1)
				s += ", ";
		}
		s+= " ] }";
		return s;
	}
	
	//resetea el tiempo y los cuerpos, notificando al MVC
	public void reset() {
		tiempo = 0.0;
		cuerpos = new ArrayList<Body>();	
		//notificacion onReset------------------------------------------------------------
		for(SimulatorObserver ob: observadores)
			ob.onReset(cuerpos, tiempo, tiempoPorPaso, leyes.toString());
	}
	
	//cambia el tiempo por paso, notificando al MVC
	public void setDeltaTime(double tPorPaso) throws IllegalArgumentException{
		if(tPorPaso < 0) throw new IllegalArgumentException();
		tiempoPorPaso = tPorPaso;
		//notificacion onDeltaTimeChanged----------------------------------------------------------------------
		for(SimulatorObserver ob: observadores)
			ob.onDeltaTimeChanged(tPorPaso);
	}
	
	//cambia las leyes de gravedad, notificando al MVC
	public void setGravityLaws(GravityLaws leyGravedad) throws IllegalArgumentException{
		if(leyGravedad == null) throw new IllegalArgumentException();
		leyes = leyGravedad;
		//notificacion onGravityLawsChanged----------------------------------------------------------------------
		for(SimulatorObserver ob: observadores)
			ob.onGravityLawChanged(leyGravedad.toString());
	}
	
	public void addObserver(SimulatorObserver o) {
		
		if(!observadores.contains(o))
			observadores.add(o);
			//notificacion onAddObserver----------------------------------------------------------------------
			o.onRegistraObserver(cuerpos, tiempo, tiempoPorPaso, leyes.toString());
	}
		
}
