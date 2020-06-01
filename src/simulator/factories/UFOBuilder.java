package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.UFO;

public class UFOBuilder extends Builder<Body>{
	public UFOBuilder() {
		tipoObj = "ufo";
		desc = "Nave que se teletransporta cada cierto tiempo";	
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
		 info.put("id", "identificador");
		 info.put("pos", "posicion");
		 info.put("vel", "velocidad");
		 info.put("freq", "frecuencia de teletransporte");
		 return info;
	}
}
