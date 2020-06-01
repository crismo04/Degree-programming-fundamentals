package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitatiom;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws>{
	public NewtonUniversalGravitationBuilder() {
		tipoObj = "nlug";
		desc = "Leyes de la gravitacion de newton";	
	}
	protected GravityLaws createTheInstance(JSONObject jsonObject) {
		GravityLaws g = new NewtonUniversalGravitatiom();
		return g;
	}
}

