package simulator.model;

import simulator.misc.Vector;

public class Body {
	
	protected String id;
	protected Vector velocity;
	protected Vector acceleration;
	protected Vector position;
	protected double mass;
	protected Double totVel;
	
	public Body(String ident,double m,Vector vel,Vector acel,Vector pos) {
		id = ident;
		velocity = vel;
		acceleration = acel;
		position = pos;
		mass = m;
		totVel = vel.magnitude();
	}
	
	
	public boolean equals(Body b) {
		return (this.id == b.id);
	}
	//functions to return data
	public String getId() {
		return id;
	}
	public double getMass() {
		return mass;
	}
	public Vector getVelocity() {
		return new Vector(velocity);
	}
	public Vector getAcceleration() {
		return new Vector(acceleration);
	}
	public Vector getPosition() {
		return new Vector(position);
	}
	public Double getMediumVelocity(Double tiempoTotal) {
		return totVel/tiempoTotal;
	}
 
	void setVelocity(Vector v) {
		velocity = v;
	}
	void setAcceleration(Vector a) {
		acceleration = a;
	}
	void setPosition(Vector pos) {
		position = pos;
	}
	
	// moves the body for 't' seconds
	void move(double t) {	
		position = position.plus((velocity.scale(t).plus(acceleration.scale((t*t)/2))));
		velocity = velocity.plus(acceleration.scale(t));
		totVel += velocity.magnitude();
	}
	
	//converts the body into a string in json format
	public String toString() {
		 return ("{  \"id\": \"" +id+"\", \"mass\":"+mass+", \"pos\": " +position.toString()+ ", \"vel\": "+velocity.toString()+", \"acc\":"+acceleration.toString()+ " }");   
	}

}
