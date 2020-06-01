package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	PhysicsSimulator simulador;
	Factory<Body> bodiesFactory;
	Factory<GravityLaws> lawsFactory;
	
	public Controller(PhysicsSimulator sim, Factory<Body> bodyFactory, Factory<GravityLaws> leyes) {
		simulador = sim;
		bodiesFactory = bodyFactory;
		lawsFactory = leyes;
	}
	
	public void loadBodies(InputStream in) {
		 JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
		 JSONArray bodies = jsonInupt.getJSONArray("bodies");
		 for (int i = 0; i < bodies.length(); i++)
			 simulador.addBody(bodiesFactory.createInstance(bodies.getJSONObject(i)));
	}
	
	
	public void run(int n, OutputStream out) {
		if(n < 0) n = 0;
		PrintStream p = (out == null) ? null : new PrintStream(out);
		p.print("{\n\"states\": [\n");
		for(int i = 0; i <= n; i++ ){
			p.print(simulador.toString());
			if(i < n) p.print(",\n");
				simulador.advance();		
		}
		p.print("\n]\n}");
		p.close();
	}
	public void run(int n) {
		if(n < 0) n = 0;
		for(int i = 0; i <= n; i++ )
			simulador.advance();
	}
	
	public void reset() {
		simulador.reset();
	}
	public void SetDeltaTime(double tPorPaso) {
		simulador.setDeltaTime(tPorPaso);
	}
	public void addObserver(SimulatorObserver o) {
		simulador.addObserver(o);
	}
	public void setGravityLaws(JSONObject info){
		simulador.setGravityLaws(lawsFactory.createInstance(info));
	}
	public Factory<GravityLaws> getGravityLawsFactory(){
		return lawsFactory;
	}
}
