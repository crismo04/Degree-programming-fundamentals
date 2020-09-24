package simulator.factories;

import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws>{	
	public FallingToCenterGravityBuilder() {
		objType = "ftcg";
		desc = "fall towards a single center of gravity";	
	}

	@Override
	protected GravityLaws createTheInstance(JSONObject jsonObject) throws IllegalArgumentException {
		GravityLaws g = new FallingToCenterGravity();
		return g;
	}
	
}
