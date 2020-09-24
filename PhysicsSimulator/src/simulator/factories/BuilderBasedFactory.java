package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	List<Builder<T>> builders;		//list of constructors.
	List<JSONObject> factoryElem; 		// list of JSON objects built by default
	
	public BuilderBasedFactory(List<Builder<T>> build){
		builders = new ArrayList<Builder<T>>(build);
		factoryElem = new ArrayList<JSONObject>();
		for(int i = 0; i < builders.size(); i++)
			factoryElem.add(builders.get(i).getBuilderInfo());
	}
	
	public T createInstance(JSONObject info) throws IllegalArgumentException {
		T obj = null;
		for(int i = 0; i < builders.size(); i++) {
			obj = builders.get(i).createInstance(info);
			if(obj != null) break;
		}
		return obj;	
	}
	
	public List<JSONObject> getInfo(){		
		return factoryElem;
	}
}
