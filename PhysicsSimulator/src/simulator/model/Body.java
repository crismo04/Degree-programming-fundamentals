package simulator.model;

import simulator.misc.Vector;

public class Body {
	
	protected String id;
	protected Vector velocidad;
	protected Vector aceleracion;
	protected Vector posicion;
	protected double masa;
	protected Double velTot;
	
	public Body(String identificador,double mass,Vector vel,Vector acel,Vector pos) {
		id = identificador;
		velocidad = vel;
		aceleracion = acel;
		posicion = pos;
		masa = mass;
		velTot = vel.magnitude();
	}
	
	
	public boolean equals(Body b) {
		return (this.id == b.id);
	}
	//funciones de para devolver datos
	public String getId() {
		return id;
	}
	public double getMasa() {
		return masa;
	}
	public Vector getVelocidad() {
		return new Vector(velocidad);
	}
	public Vector getAceleracion() {
		return new Vector(aceleracion);
	}
	public Vector getPosicion() {
		return new Vector(posicion);
	}
	public Double getVelocidadMedia(Double tiempoTotal) {
		return velTot/tiempoTotal;
	}

	// funciones set 
	void setVelocidad(Vector v) {
		velocidad = v;
	}
	void setAceleracion(Vector a) {
		aceleracion = a;
	}
	void setPosicion(Vector pos) {
		posicion = pos;
	}
	
	// mueve el cuerpo durante 't' segundos
	void move(double t) {	
		posicion = posicion.plus((velocidad.scale(t).plus(aceleracion.scale((t*t)/2))));
		velocidad = velocidad.plus(aceleracion.scale(t));
		velTot += velocidad.magnitude();
	}
	
	//convierte el cuerpo en un string en formato json
	public String toString() {
		 return ("{  \"id\": \"" +id+"\", \"mass\":"+masa+", \"pos\": " +posicion.toString()+ ", \"vel\": "+velocidad.toString()+", \"acc\":"+aceleracion.toString()+ " }");   
	}
}
