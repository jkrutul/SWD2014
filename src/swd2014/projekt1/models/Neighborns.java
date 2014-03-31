package swd2014.projekt1.models;

import java.util.LinkedList;

public class Neighborns {
	private Point point;
	private LinkedList<ClassModel> distances;
	
	
		
	public Point getPoint() {
		return point;
	}
	public Neighborns(Point point, LinkedList<ClassModel> distances) {
		super();
		this.point = point;
		this.distances = distances;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public LinkedList<ClassModel> getDistances() {
		return distances;
	}
	public void setDistances(LinkedList<ClassModel> distances) {
		this.distances = distances;
	}
}