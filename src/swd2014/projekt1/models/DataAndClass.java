package swd2014.projekt1.models;

public class DataAndClass {
	private double data;
	private String className;

	
	public DataAndClass(double data, String className) {
		super();
		this.data = data;
		this.className = className;
	}
	
	
	public double getData() {
		return data;
	}
	public void setData(double data) {
		this.data = data;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}
