package swd2014.projekt1.models;

public class PointClassModel  implements Comparable<PointClassModel> {
	private Point point;
	private String class_name;
	private double distance;
	
	public PointClassModel(){
		
	}
	
	public PointClassModel(String n_class, double distance){
		this.class_name = n_class;
		this.distance = distance;
	}
	
	public PointClassModel(double x, double y, String n_class ){
		point = new Point(x, y);
		this.class_name = n_class;
	}
	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public String getN_class() {
		return class_name;
	}
	public void setN_class(String n_class) {
		this.class_name = n_class;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(PointClassModel cm) {
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
		return distance+" class: " + class_name;
	}

}
