package logic.managers;

public class SuncoinManager {
	private int numSuns;
	
	public SuncoinManager() {
		numSuns = 50;
	}
	
	//add up the SunCoins depending on the number of sunflowers
	public void sumSC(int suns) {
		numSuns += suns;
	}
	
	//subtract the price from the number of coins
	public void SubSC(int price) {
		numSuns -= price;
	}
	
	//return if there are enough coins
	public boolean EnoughSC(int price) {
		boolean b;
		if(price > numSuns) b = false;
		else b = true;
		return b;
	}
	
	public int getSunCoins() {
		return numSuns;
	}

}
