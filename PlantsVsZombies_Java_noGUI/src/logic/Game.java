package logic;

import java.util.Random;
import factory.ZombieFactory;
import logic.managers.*;
import print.*;
import logic.objets.*;

public class Game {
	private int cycle;
	private ObjectList PlantList;
	private ObjectList ZombList;
	private Random rand;
	private SuncoinManager Suncoin;
	private ZombieManager ZManager;
	private Level lvl;
	private int tamX = 4;
	private int tamY = 8;
	private boolean playerLose = false;
	private boolean ZombieWin = false;
	private boolean cycleAdvance = true;
	private long Seed;
	
	public Game(Level level, Long cycleAdvance ){
		cycle = 0;
		Seed = cycleAdvance;
		lvl = level;
		rand = new Random(cycleAdvance);
		PlantList = new ObjectList(tamX*tamY);
		ZombList = new ObjectList(tamX*tamY);
		Suncoin = new SuncoinManager();
		ZManager = new ZombieManager(lvl);
	}
	
	public int getCycles() {
		return cycle;
	}
	public int getX() {
		return tamX;
	}
	public int getY() {
		return tamY;
	}
	public int getDimDebug() {
		return  PlantList.getHowManyObj()+ZombList.getHowManyObj();
	}
	//returns the amount of suns available
	public int getSC() {
		return Suncoin.getSunCoins();
	}
	
	//restart the game
	public void resetGame() {
		cycle = 0;
		Suncoin = new SuncoinManager();
		ZManager = new ZombieManager(lvl);
		PlantList = new ObjectList(tamX*tamY);
		ZombList = new ObjectList(tamX*tamY);	
		System.out.println("\n\n GAME RESET CORRECTLY \n");
	}
	
	//draw the board
	public void PrintCycleRelease(GamePrinter GPrint) {
		System.out.println("Cycle number: " + cycle);
		System.out.println("SunCoins:" + getSC());
		System.out.println("Remaining Zombies: " + ZManager.HowMany());
		System.out.print(GPrint.PrinterGame());
	}	
	public void PrintCycleDebug(GamePrinter GPrint) {
		System.out.println("Cycle number: " + cycle);
		System.out.println("SunCoins:" + getSC());
		System.out.println("Remaining Zombies: " + ZManager.HowMany());
		System.out.println("lEVEL: " + lvl.toString());
		System.out.println("Seed " + Seed);								
		System.out.print(GPrint.PrinterGame());
	}
	
	public String toStringRelease(int x, int y) {
		String s;
		if(!ZombList.empty(x, y))
			s = ZombList.toStringRelease(x, y); 
		else if(!PlantList.empty(x, y))
			s = PlantList.toStringRelease(x, y);
		else s = " ";
		return s;
	}
	public String toStringDebug(int i) {
		String s = "";
		if(i < ZombList.getHowManyObj())
			s = ZombList.toStringDebug(i);
		else if(i < ZombList.getHowManyObj()+PlantList.getHowManyObj())
			s = PlantList.toStringDebug(i - ZombList.getHowManyObj());
		return s;
	}
	
	public void noCyclePass() {
		cycleAdvance = false;
	}
	// updates the game by invoking the list update and removing dead objects
	public void update() {
		if(cycleAdvance) {
			int aux;
			cycle++;
			//Plants update
			PlantList.updateObj();
			//zombies advance and attack plants
			ZombList.updateObj();
			//new zombies are added if it is possible
			computerAction();
			//check deaths
			aux = ZombList.kill();
			for(int i = 0; i < aux; i++)
				ZManager.killZombie();
			PlantList.kill();
		}
		cycleAdvance = true;
	}
	
	// add suncoins
	public void sumSuns(int soles) {
		Suncoin.sumSC(soles);
	}
	
	//attacks the plants in front
	public void attacksZ(int damage,int column, int row) {
		PlantList.attackToObj(damage, column, row-1); 
	}
	
	public void shoot(int damage,int x, int y) {
		boolean hit = false;
		for(int i = y+1; (i < tamY) && !hit ;i++) {
			if(!ZombList.empty(x, i)) {
				hit = true;
				ZombList.attackToObj(damage, x, i);
			}
		}
	}
	
	//damages all the boxes next to it (explosion)
	public void explota(int damage, int x, int y) {
		if(!ZombList.empty(x+1, y+1))//up
			ZombList.attackToObj(damage, x+1, y+1);
		if(!ZombList.empty(x+1, y))
			ZombList.attackToObj(damage, x+1, y);
		if(!ZombList.empty(x+1, y-1))
			ZombList.attackToObj(damage, x+1, y-1);
		if(!ZombList.empty(x, y+1)) //same row
			ZombList.attackToObj(damage, x, y+1);
		if(!ZombList.empty(x, y-1))
			ZombList.attackToObj(damage, x, y-1);
		if(!ZombList.empty(x-1, y+1)) //under
			ZombList.attackToObj(damage, x-1, y+1);
		if(!ZombList.empty(x-1, y))
			ZombList.attackToObj(damage, x-1, y);
		if(!ZombList.empty(x-1, y-1))
			ZombList.attackToObj(damage, x-1, y-1);
		
	}
	
	public void playerLeaves() {
		playerLose = true;
	}
	
	public void ZombieWin() {
		ZombieWin = true;
	}
	
	//if zombies win -1, if zombies die 1, if not 0
	public int gameOver() {
		int res;
		if(ZManager.AllDead()) res = 1;
		else if(ZombieWin || playerLose) res = -1; 
		else res = 0;
		return res;
	}

	//check the different boxes
	public boolean empty(int x, int y) {
		boolean b = false;
		if(PlantList.empty(x, y) && ZombList.empty(x, y)) b = true;
		return b;
	}
	
	//to know if add a zombie with 'isZombieAdded' 
	public void computerAction(){
		int prob = rand.nextInt(4);
		if(ZManager.isZombieAdded(rand)) {
			String c;
			int ran = rand.nextInt(12);
			if(ran%3 == 1) c = "w";
			else if(ran%3 == 2) c = "x";
			else c = "z";
			while(!addZombie(ZombieFactory.getZombie(c), (int)prob%4, tamY-1))
				 prob = rand.nextInt();;
		}		
	}
	//for a random number, assign a zombie
	public static String GetTipoZombie(int ran) {
		String c;
		if(ran%3 == 1) c = "w";
		else if(ran%3 == 2) c = "x";
		else c = "z";
		return c;
	}
	//checks if the object can be added and adds it
	public boolean addZombie(Zombies z, int x, int y) {
		boolean b = false;
		if (empty(x, y) && x > 0 && x < tamY-1 && y >= 0 && y <= tamY-1) {
			ZombList.addObj(z,x,y,this);
			b = true;
		}
		else
			System.out.println("Not a valid position for a zombie");
		return b;
	}	
	
	
	//checks if the object can be added and adds it
	public boolean addPlant(Plants p, int x, int y) {
		boolean b = false;
		if (empty(x, y) && x >= 0 && x < tamY-1 && y >= 0 && y < tamY-1) {
			if(Suncoin.EnoughSC(p.getPrice())) {
				Suncoin.SubSC(p.getPrice());
				PlantList.addObj(p,x,y,this); 
				b = true;
			}
			else
				System.out.println("Not enough Suncoins");
		}
		else
			System.out.println("Not a valid position");
		return b;
	}	
}