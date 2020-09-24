package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitatiom;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws>{
	public NewtonUniversalGravitationBuilder() {
		objType = "nlug";
		desc = "Newton's Laws of Gravitation";	
	}
	protected GravityLaws createTheInstance(JSONObject jsonObject) {
		GravityLaws g = new NewtonUniversalGravitatiom();
		return g;
	}
}

