package logic.managers;

import logic.Level;
import java.util.Random;

// lleva la cuenta de los zombies que quedan por salir
public class ZombieManager {
	private int cuantosMas;
	private int cuantosViven; //probabilidad de que salga un zombie
	private Level lvl;
	
	public ZombieManager(Level l){
		lvl = l;
		cuantosMas = l.getNumZombies();
		cuantosViven = 0;
	}
	
	public int CuantosFaltan() {
		return cuantosMas;
	}
	public int CuantosViven() {
		return cuantosViven;
	}
	
	//para saber si hay que añadir un zombie o no 
	public boolean isZombieAdded(Random ran) {
		boolean b = false;
		double prob = ran.nextDouble();
		if((prob%1 < lvl.getFrecuencia()) && (cuantosMas > 0)) {
			b = true;
			cuantosViven++;
			cuantosMas--;
		}
		return b;
	}
	//devuelve true si no quedan zombies
	public boolean TodosMuertos() {
		boolean b = false;
		if(cuantosViven == 0 && cuantosMas == 0)
			b = true;
		return b;
	}
	public void mataZombie() {
			cuantosViven--;
	}
}
