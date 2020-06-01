package simulator.model;

import java.util.List;

//cada metodo de la interfaz comunica una accion
public interface SimulatorObserver {
	
	//envia una notificacion al observer recien registrado
	public void onRegistraObserver(List<Body> bodies, double time, double dt, String gLawsDesc);
	
	//informan a todos los observadores
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc);
	public void onBodyAdded(List<Body> bodies, Body b);
	public void onAdvance(List<Body> bodies, double time);
	public void onDeltaTimeChanged(double dt);
	public void onGravityLawChanged(String gLawsDesc);
}
