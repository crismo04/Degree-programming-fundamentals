package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {	
	static final double[] vectorCero = {0.0,0.0}; //para aceleracion
	String tipoObj;
	String desc;
	
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T ret = null;
		if (tipoObj != null && tipoObj.equals(info.getString("type")))
			ret = createTheInstance(info.getJSONObject("data"));	
		return ret;
	}

	protected abstract T createTheInstance(JSONObject jsonObject) throws IllegalArgumentException;
	
	public JSONObject getBuilderInfo() {
		 JSONObject info = new JSONObject();
		 info.put("type", tipoObj);
		 info.put("data", createData());
		 info.put("desc", desc);
		 return info;
	}
	protected JSONObject createData() {
		return new JSONObject();
	}
	
	protected double[] jsonArrayTodoubleArray(JSONArray ja) {
		 double[] da = new double[ja.length()];
		 for (int i = 0; i < da.length; i++)
		 da[i] = ja.getDouble(i);
		 return da;
		}
}	