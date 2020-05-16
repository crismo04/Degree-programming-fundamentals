package logic;

public enum Level {EASY, HARD, INSANE;

	public int getNumZombies() {
		int n;
		if(this == EASY) n = 3;
		else if(this == HARD) n = 5;
		else n = 10;
		return n;
	}
	
	public double getFrecuencia() {
		double n;
		if(this == EASY) n = 0.1;
		else if(this == HARD) n = 0.2;
		else n = 0.3;
		return n;
	}
	public String toString() {
		String n;
		if(this == EASY) n = "EASY";
		else if(this == HARD) n = "HARD";
		else n = "INSANE";
		return n;
	}
}
