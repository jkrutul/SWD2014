package swd2014.projekt1.models;

public class ClassModel  implements Comparable<ClassModel> {
	private Point point;
	private int n_class;
	private double distance;
	
	public ClassModel(){
		
	}
	
	public ClassModel(int n_class, double distance){
		this.n_class = n_class;
		this.distance = distance;
	}
	
	public ClassModel(double x, double y, int n_class ){
		point = new Point(x, y);
		this.n_class = n_class;
	}
	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public int getN_class() {
		return n_class;
	}
	public void setN_class(int n_class) {
		this.n_class = n_class;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(ClassModel cm) {
		double compareDist = cm.getDistance();
		if(this.distance> compareDist)
			return 1;
		else if (this.distance == compareDist)
			return 0;
		else
			return -1;
	}
	
	@Override
	public String toString(){
		return distance+" class: " + n_class;
	}

}
