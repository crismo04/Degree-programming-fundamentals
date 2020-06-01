package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws>{	
	public NoGravityBuilder() {
		tipoObj = "ng";
		desc = "Sin gravedad";	
	}
	protected GravityLaws createTheInstance(JSONObject jsonObject) {
		GravityLaws g = new NoGravity();
		return g;
	}
}
