package swd2014.projekt1.models;

public class Point {
	private double x, y;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
	@Override
	public String toString(){
		return "["+ Double.toString(x) + ", "+ Double.toString(y)+"]";
	}
}
