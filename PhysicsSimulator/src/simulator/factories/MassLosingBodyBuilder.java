package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body>{
	public MassLosingBodyBuilder() {
		tipoObj = "mlb";
		desc = "cuerpo que va perdiendo masa";
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
		 info.put("id", "identificador(unico)");
		 info.put("pos", "posicion");
		 info.put("vel", "velocidad");
		 info.put("mass", "masa");
		 info.put("freq", "frecuencia de perdida de masa");
		 info.put("factor", "factor de perdida de masa");
		 return info;
	}
}