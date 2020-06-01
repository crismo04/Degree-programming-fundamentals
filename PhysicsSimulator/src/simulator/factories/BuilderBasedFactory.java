package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	List<Builder<T>> constructores;		//lista de constructores.
	List<JSONObject> factoryElem; 		// lista de objetos JSON construídos por defecto
	
	public BuilderBasedFactory(List<Builder<T>> builders){
		constructores = new ArrayList<Builder<T>>(builders);
		factoryElem = new ArrayList<JSONObject>();
		for(int i = 0; i < constructores.size(); i++)
			factoryElem.add(constructores.get(i).getBuilderInfo());
	}
	
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T obj = null;
		for(int i = 0; i < constructores.size(); i++) {
			obj = constructores.get(i).createInstance(info);
			if(obj != null) break;
		}
		return obj;	
	}
	
	public List<JSONObject> getInfo(){		
		return factoryElem;
	}
}
