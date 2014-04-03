package swd2014.projekt1.models;

public class Point {
	private double x, y;
	private String point_class;
	
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

	public String getPoint_class() {
		return point_class;
	}

	public void setPoint_class(String point_class) {
		this.point_class = point_class;
	}
}
