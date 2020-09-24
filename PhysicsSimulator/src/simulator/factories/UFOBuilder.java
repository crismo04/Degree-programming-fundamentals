package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.UFO;

public class UFOBuilder extends Builder<Body>{
	public UFOBuilder() {
		objType = "ufo";
		desc = "Ship that teleports itself from time to time";	
	}
	protected Body createTheInstance(JSONObject jsonObject)  throws IllegalArgumentException {
		String id = jsonObject.getString("id");
		Vector p = new Vector(jsonArrayTodoubleArray(jsonObject.getJSONArray("pos")));
		Vector v = new Vector(jsonArrayTodoubleArray(jsonObject.getJSONArray("vel")));
		double freq = jsonObject.getDouble("freq");
		Body b = new UFO(id, v, new Vector(vectorCero), p, freq);
		return b;
	}
	protected JSONObject createData() {
		JSONObject info = new JSONObject();
		 info.put("id", "identifier (unique)");
		 info.put("pos", "position");
		 info.put("vel", "velocity");
		 info.put("freq", "teleports frequency");
		 return info;
	}
}
