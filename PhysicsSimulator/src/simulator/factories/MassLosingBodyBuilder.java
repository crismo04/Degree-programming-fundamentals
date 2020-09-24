package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body>{
	public MassLosingBodyBuilder() {
		objType = "mlb";
		desc = "body that is losing mass";
	}		
	protected Body createTheInstance(JSONObject jsonObject) throws IllegalArgumentException{
		String id = jsonObject.getString("id");
		double m = jsonObject.getDouble("mass");
		Vector p = new Vector(jsonArrayTodoubleArray(jsonObject.getJSONArray("pos")));
		Vector v = new Vector(jsonArrayTodoubleArray(jsonObject.getJSONArray("vel")));
		double freq = jsonObject.getDouble("freq");
		double fact = jsonObject.getDouble("factor");
		Body b = new MassLossingBody(id, m, v, new Vector(vectorCero), p, freq, fact);
		return b;
	}
	protected JSONObject createData()  {
		JSONObject info = new JSONObject();
		 info.put("id", "identifier (unique)");
		 info.put("pos", "position");
		 info.put("vel", "velocity");
		 info.put("mass", "mass");
		 info.put("freq", "mass loss frequency");
		 info.put("factor", "mass loss factor");
		 return info;
	}
}