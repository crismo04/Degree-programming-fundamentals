package logic;

import logic.objets.Character;

public class ObjectList {
	int howManyObj;
	private Character[] Characters;
	
	//creates an array of size = X*Y
	public ObjectList(int tam) {
		Characters = new Character[tam];
		howManyObj = 0;
	}
	
	// eliminates lifeless objects
	public int kill() {
		int aux = 0; //returns how many zombies have been killed
		for(int i = 0; i < howManyObj; i++) {
			if(Characters[i].getHealth() <= 0) {       
				Characters[i] = Characters[howManyObj-1]; 
				howManyObj--; 
				aux++;
			}
		}
		return aux;
	}
			
	//returns if the list is empty or not
	public boolean empty(int x, int y) {
		boolean b = false;
		if (findObj(x,y) == -1) b = true;
		return b;
	}	
	
	//returns how many Objects are on the board
	public int getHowManyObj() {
		return howManyObj;
	}
	
	public void updateObj() {
		for(int i = 0; i < howManyObj; i++) 
			Characters[i].update();		
	}

	//returns true if it has found an Object in x,y
	public int findObj(int x, int y) {
		int pos = -1;
		for(int i = 0; i < howManyObj; i++) {
			if(Characters[i].getX() == x && Characters[i].getY() == y)
				pos = i;
			}
		return pos;
	}
	
	// attacks objects that are in the given position
	public void attackToObj(int damage, int x, int y) {
		int i = findObj(x,y);
		if(i != -1 && Characters[i].getHealth()>0)
			Characters[findObj(x, y)].subHealth(damage);	
	}
		
	//return P[Health]
	public String toStringRelease(int x, int y) {
		String s = "";
		Character p = Characters[findObj(x,y)];
		s += p.getSim();
		s += "[" + p.getHealth() + "]";
		return s;
	}
	
	public String toStringDebug(int i) {
		String s = "";
		Character p = Characters[i];
		if(p != null) {
			s += p.getSim();
			s += "[V:" + p.getHealth() + ",X:" + p.getX() + ",Y:"+ p.getY() + ",T:"+ p.GetCurrentTime()+ "]";
		}
		return s;
	}
		
	//add a objet
	public void addObj(Character p, int x, int y, Game g) {
		p.positioning(x,y,g);
		Characters[howManyObj] = p;
		howManyObj++;
	}

}

