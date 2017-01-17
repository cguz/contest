package hash_code.contest_2017.practice.pizza;

public class Slide {

	private int r1;
	private int c1;
	private int r2;
	private int c2;

	public Slide(int r1, int c1, int r2, int c2) {
		this.r1 = r1;
		this.c1 = c1;
		this.r2 = r2;
		this.c2 = c2;
	}
	
	
	public int getR1() {
		return r1;
	}

	public int getC1() {
		return c1;
	}

	public int getR2() {
		return r2;
	}

	public int getC2() {
		return c2;
	}

	public int getScore() {
		return 0;
	}


	public int getCells() {
		return (r2-r1)*(c2-c1);
	}

}
