package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{
	
	public BasicBodyBuilder() {
		objType = "basic";
		desc = "common celestial body";
	}
	protected Body createTheInstance(JSONObject jsonObject) throws IllegalArgumentException{
		String id = jsonObject.getString("id");
		double m = jsonObject.getDouble("mass");
		Vector p = new Vector(jsonArrayTodoubleArray(jsonObject.getJSONArray("pos")));
		Vector v = new Vector(jsonArrayTodoubleArray(jsonObject.getJSONArray("vel")));
		Body b = new Body(id, m, v, new Vector(vectorCero), p);
		return b;
	}
	protected JSONObject createData() {
		JSONObject info = new JSONObject();
		 info.put("id", "identifier(unique)");
		 info.put("pos", "position");
		 info.put("vel", "velocity");
		 info.put("mass", "mass");
		 return info;
	}
}
