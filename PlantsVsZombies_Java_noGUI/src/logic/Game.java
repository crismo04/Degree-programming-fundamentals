package logic;

import java.util.Random;
import factory.ZombieFactory;
import logic.managers.*;
import print.*;
import logic.objets.*;

public class Game {
	private int ciclos;
	private ObjectList PlantList;
	private ObjectList ZombList;
	private Random rand;
	private SuncoinManager Suncoin;
	private ZombieManager ZManager;
	private Level lvl;
	private int tamX = 4;
	private int tamY = 8;
	private boolean jugadorPierde = false;
	private boolean ZombiesGanan = false;
	private boolean avanzaCiclo = true;
	private long semilla;
	
	public Game(Level level, Long seed ){
		ciclos = 0;
		semilla = seed;
		lvl = level;
		rand = new Random(seed);
		PlantList = new ObjectList(tamX*tamY);
		ZombList = new ObjectList(tamX*tamY);
		Suncoin = new SuncoinManager();
		ZManager = new ZombieManager(lvl);
	}
	
	public int getCiclos() {
		return ciclos;
	}
	public int getX() {
		return tamX;
	}
	public int getY() {
		return tamY;
	}
	public int getDimDebug() {
		return  PlantList.getCuantosObj()+ZombList.getCuantosObj();
	}
	//devuelve la cantidad de dinero disponible
	public int getSC() {
		return Suncoin.getSunCoins();
	}
	
	//reinicializa el juego
	public void resetGame() {
		ciclos = 0;
		Suncoin = new SuncoinManager();
		ZManager = new ZombieManager(lvl);
		PlantList = new ObjectList(tamX*tamY);
		ZombList = new ObjectList(tamX*tamY);	
		System.out.println("\n\n JUEGO RESETEADO \n");
	}
	
	//pinta el tablero
	public void PrintCicloRelease(GamePrinter GPrint) {
		System.out.println("Numero de ciclos: " + ciclos);
		System.out.println("SunCoins:" + getSC());
		System.out.println("Zombies restantes: " + ZManager.CuantosFaltan());							
		System.out.print(GPrint.PrinterGame());
	}	
	public void PrintCicloDebug(GamePrinter GPrint) {
		System.out.println("Numero de ciclos: " + ciclos);
		System.out.println("SunCoins:" + getSC());
		System.out.println("Zombies restantes: " + ZManager.CuantosFaltan());
		System.out.println("Nivel: " + lvl.toString());
		System.out.println("Semilla " + semilla);								
		System.out.print(GPrint.PrinterGame());
	}
	
	public String toStringRelease(int x, int y) {
		String s;
		if(!ZombList.vacia(x, y))
			s = ZombList.toStringRelease(x, y); 
		else if(!PlantList.vacia(x, y))
			s = PlantList.toStringRelease(x, y);
		else s = " ";
		return s;
	}
	public String toStringDebug(int i) {
		String s = "";
		if(i < ZombList.getCuantosObj())
			s = ZombList.toStringDebug(i);
		else if(i < ZombList.getCuantosObj()+PlantList.getCuantosObj())
			s = PlantList.toStringDebug(i - ZombList.getCuantosObj());
		return s;
	}
	
	public void noPasesCiclo() {
		avanzaCiclo = false;
	}
	// actualiza el juego invocando al update de la lista y eliminando objetos muertos
	public void update() {
		if(avanzaCiclo) {
			int aux;
			ciclos++;
			//actualizo plantas
			PlantList.updateObj();
			//zombies avanzan y atacan a las plantas
			ZombList.updateObj();
			//se añaden los zombies nuevos si se puede
			computerAction();
			//comprobar muertes
			aux = ZombList.mata();
			for(int i = 0; i < aux; i++)
				ZManager.mataZombie();
			PlantList.mata();
		}
		avanzaCiclo = true;
	}
	
	// suma sc
	public void sumaSuns(int soles) {
		Suncoin.sumaSC(soles);
	}
	
	//ataca a las plantas de delante
	public void atacaZ(int damage,int columna, int fila) {
		PlantList.ataqueA_Obj(damage, columna, fila-1); 
	}
	
	public void dispara(int damage,int x, int y) {
		boolean golpeado = false;
		for(int i = y+1; (i < tamY) && !golpeado ;i++) {
			if(!ZombList.vacia(x, i)) {
				golpeado = true;
				ZombList.ataqueA_Obj(damage, x, i);
			}
		}
	}
	
	//hace danio a toda las casillas (explosion)
	public void explota(int damage, int x, int y) {
		if(!ZombList.vacia(x+1, y+1))//arriba
			ZombList.ataqueA_Obj(damage, x+1, y+1);
		if(!ZombList.vacia(x+1, y))
			ZombList.ataqueA_Obj(damage, x+1, y);
		if(!ZombList.vacia(x+1, y-1))
			ZombList.ataqueA_Obj(damage, x+1, y-1);
		if(!ZombList.vacia(x, y+1)) //misma fila
			ZombList.ataqueA_Obj(damage, x, y+1);
		if(!ZombList.vacia(x, y-1))
			ZombList.ataqueA_Obj(damage, x, y-1);
		if(!ZombList.vacia(x-1, y+1)) //debajo
			ZombList.ataqueA_Obj(damage, x-1, y+1);
		if(!ZombList.vacia(x-1, y))
			ZombList.ataqueA_Obj(damage, x-1, y);
		if(!ZombList.vacia(x-1, y-1))
			ZombList.ataqueA_Obj(damage, x-1, y-1);
		
	}
	
	public void jugadorAbandona() {
		jugadorPierde = true;
	}
	
	public void ZombiesGanan() {
		ZombiesGanan = true;
	}
	
	//si los zombies ganan -1, si los zombies mueren 1, sino 0
	public int finJuego() {
		int res;
		if(ZManager.TodosMuertos()) res = 1;
		else if(ZombiesGanan || jugadorPierde) res = -1; 
		else res = 0;
		return res;
	}

	//comprueba las distintas casillas
	public boolean vacio(int x, int y) {
		boolean b = false;
		if(PlantList.vacia(x, y) && ZombList.vacia(x, y)) b = true;
		return b;
	}
	
	//para saber si añadir un zombie con isZombieAdded del manager
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
	//dado un numero aleatorio da un zombie
	public static String GetTipoZombie(int ran) {
		String c;
		if(ran%3 == 1) c = "w";
		else if(ran%3 == 2) c = "x";
		else c = "z";
		return c;
	}
	//comprueba si se puede aniadir el objeto y lo aniade
	public boolean addZombie(Zombies z, int x, int y) {
		boolean b = false;
		if (vacio(x, y) && x > 0 && x < tamY-1 && y >= 0 && y <= tamY-1) {
			ZombList.addObj(z,x,y,this);
			b = true;
		}
		else
			System.out.println("No es una posicion valida para un zombie");
		return b;
	}	
	
	
	//añade una planta a la lista
	public boolean addPlant(Plantas p, int x, int y) {
		boolean b = false;
		if (vacio(x, y) && x >= 0 && x < tamY-1 && y >= 0 && y < tamY-1) {
			if(Suncoin.SuficientesSC(p.getCoste())) {
				Suncoin.RestaSC(p.getCoste());
				PlantList.addObj(p,x,y,this); 
				b = true;
			}
			else
				System.out.println("No hay suficientes Suncoins");
		}
		else
			System.out.println("No es una posicion valida");
		return b;
	}
	
	
}