package logic;

import logic.objets.*;

public class ObjectList {
	int cuantosObj;
	private Personaje[] personajes;
	
	//crea un array de tam = X*Y
	public ObjectList(int tam) {
		personajes = new Personaje[tam];
		cuantosObj = 0;
	}
	
	// elimina a los objetos sin vida
	public int mata() {
		int aux = 0; //devuelve cuantos se han matado para los zombies
		for(int i = 0; i < cuantosObj; i++) {
			if(personajes[i].getVida() <= 0) {       
				personajes[i] = personajes[cuantosObj-1]; 
				cuantosObj--; 
				aux++;
			}
		}
		return aux;
	}
			
	//devuelve si la lista esta vacia
	public boolean vacia(int x, int y) {
		boolean b = false;
		if (buscarObj(x,y) == -1) b = true;
		return b;
	}	
	
	//devuelve cuantos Obj hay en el tablero
	public int getCuantosObj() {
		return cuantosObj;
	}
	
	public void updateObj() {
		for(int i = 0; i < cuantosObj; i++) 
			personajes[i].update();		
	}

	//devuelve true si ha encontrado un Obj en x,y
	public int buscarObj(int x, int y) {
		int pos = -1;
		for(int i = 0; i < cuantosObj; i++) {
			if(personajes[i].getX() == x && personajes[i].getY() == y)
				pos = i;
			}
		return pos;
	}
	
	// ataca a los objetos que esten en la posicion  dada
	public void ataqueA_Obj(int damage, int x, int y) {
		int i = buscarObj(x,y);
		if(i != -1 && personajes[i].getVida()>0) 
			personajes[buscarObj(x, y)].quitarVida(damage);	
	}
		
	//devuelve P[vida]
	public String toStringRelease(int x, int y) {
		String s = "";
		Personaje p = personajes[buscarObj(x,y)];
		s += p.getSim();
		s += "[" + p.getVida() + "]";
		return s;
	}
	
	public String toStringDebug(int i) {
		String s = "";
		Personaje p = personajes[i];
		if(p != null) {
			s += p.getSim();
			s += "[V:" + p.getVida() + ",X:" + p.getX() + ",Y:"+ p.getY() + ",T:"+ p.GetTiempoAct()+ "]";
		}
		return s;
	}
		
	//aniade un Obj
	public void addObj(Personaje p, int x, int y, Game g) {
		p.posicionar(x,y,g);
		personajes[cuantosObj] = p;
		cuantosObj++;
	}
}

