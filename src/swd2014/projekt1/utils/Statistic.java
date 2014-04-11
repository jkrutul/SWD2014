package swd2014.projekt1.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.mahout.common.distance.MahalanobisDistanceMeasure;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.SparseMatrix;
import org.apache.mahout.math.Vector;

import swd2014.projekt1.models.Neighborns;
import swd2014.projekt1.models.Point;
import swd2014.projekt1.models.PointClassModel;


public class Statistic {
	
	
	
	/* Warto�� �rednia
	 * 
	 */
	public static float mean(float[] data){
		int n = data.length;
		float sum = 0;
		
		for(float f : data){
			sum+=f;
		}
		return sum/n;
	}
	
	/* Wariancja
	 * 
	 */
	public static float variance(float[] data){
		float mean = mean(data);
		float sum = 0;
		
		for(float f : data){
			sum+= (f-mean)*(f-mean);
		}
		
		return sum/data.length;
	}
	
	
	/* Ochylenie standardowe
	 * 
	 */
	public static float standardDeviantion(float[] data){
		float variance = variance(data);
		return (float) Math.sqrt(variance);
	}
	
	public static double standardDeviantion(double variance){
		return (double) Math.sqrt(variance);
	}
	

	
	public static float median(float[] data){
		int n = data.length;
		boolean isEven = (n%2 == 0) ? true : false;
		
		if(isEven)
			return (data[n/2]+data[(n/2)+1])/2;
		else 
			return data[n/2];
	}
	
	

	public static float q1(float[] data){
		int n = data.length;
		//int floor_1_4 = (int) Math.floor(n/4);
		//return data[floor_1_4];
		
		if(data.length<4)
			return -1;
		
		boolean isEven = (n%2 == 0) ? true : false;
		
		if(isEven)
			return (data[n/4]+data[(n/4)+1])/2;
		else 
			return data[n/4];
	}
	
	public static float q3(float[] data){
		int n = data.length;
		//int ceil_3_4 = (int) Math.ceil((3*n)/4);
		//return data[ceil_3_4];
		if(data.length<4)
			return -1;
		
		boolean isEven = (n%2 == 0) ? true : false;
		
		if(isEven)
			return (data[(3*n)/4]+data[((3*n)/4)+1])/2;
		else 
			return data[3*n/4];
	}
	
	public static double[] standardScore(double[] data){
		double mean = StatUtils.mean(data);
		double[] output_data = new double[data.length];
		StandardDeviation sd = new StandardDeviation();
		double sd_value = sd.evaluate(data);
		
		int i=0;
		for(double d : data){
			output_data[i++] = ((d-mean)/sd_value);
		}
		
		return output_data;
	}
	
	public static double quantile(float[] data, float percent){
		double t = (percent/100) * (data.length-1);
		int i = (int) t;
		return (i+1-t)*data[i] + (t-i)* data[i+1];
	}
	
	public static double euclideanDistance(Point from, Point to){
		/*
		double distance=0;
		distance = Math.pow((from.getX() - to.getX()), 2)+ Math.pow((from.getY()-to.getY()), 2);
		distance = Math.sqrt(distance);
		return distance;
		*/
		Vector2D v_from = new Vector2D(from.getX(),from.getY());
		Vector2D v_to = new Vector2D(to.getX(),to.getY());
		return Vector2D.distance(v_from, v_to);
		
	}
	
	public static double manhattanDistance(Point from, Point to){
		double distance=0;
		distance = Math.abs(from.getX() - from.getY()) + Math.abs(to.getX() - to.getY());
		return distance;
	}
	
	public static double metrykaNieskonczonosc(Point from, Point to){
		/*
		double distance = 0;
		distance = Math.max(Math.abs(from.getX()-to.getX()), Math.abs(from.getY()- from.getY()));
		return distance;
		*/
		Vector2D v_from = new Vector2D(from.getX(),from.getY());
		Vector2D v_to = new Vector2D(to.getX(),to.getY());
		return Vector2D.distanceInf(v_from, v_to);
	}
	
	public static double mahanalobisDistance(Point from, Point to){
		double[][] d = {{from.getX(), from.getY()},{to.getX(), to.getY()}};
		
		Vector v1 = new RandomAccessSparseVector(2);
		v1.assign(d[0]);
		Vector v2 = new RandomAccessSparseVector(2);
		v2.assign(d[1]);
		
		Matrix matrix  = new SparseMatrix();
		matrix.assignRow(0, v1);
		matrix.assignRow(1, v2);
		
		MahalanobisDistanceMeasure dmM = new MahalanobisDistanceMeasure();
		dmM.setInverseCovarianceMatrix(matrix);
		return dmM.distance(v1, v2);
		
		
	}
	
	
	
	public static double[] euclideanDistance( Point from, Point[] to){
		double[] distance = new double[to.length];
		int i=0;
		for(Point p : to ){
			distance[i++] =euclideanDistance(from, p);
		}
		
		return distance;
	}
	
	public static double[] manhattanDistance( Point from, Point[] to){
		double[] distance = new double[to.length];
		int i=0;
		for(Point p : to ){
			distance[i++] = manhattanDistance(from, p);
		}
		
		return distance;
	}
	
	public static double[] metrykaNieskonczonosc(Point from, Point[] to){
		double[] distance = new double[to.length];
		int i=0;
		for(Point p : to ){
			distance[i++] = metrykaNieskonczonosc(from, p);
		}
		return distance;
	}
	
	public static void mahanalobisDistance(Point from[], Point[] to){
		int size = (from.length>to.length) ? from.length : to.length;
		
		double x[], y[], x1[], y1[];
		
		x = new double[from.length];
		y = new double[from.length];
		
		x1 = new double[to.length];
		y1 = new double[to.length];
		
		int index=0;
		for(Point p : from){
			x[index] = p.getX();
			y[index] = p.getY();
		}
		
		index=0;
		for(Point p : to){
			x1[index] = p.getX();
			y1[index] = p.getY();
		}
		
		//Mahalanobis m = new Mahalanobis(size);
		//m.getDistance(x, x1);
		
		//1. średnia dla wektorów
		//2. odejmuję wartości średnie od każdego elementu wektoru
		//3. liczę kowariancję dla 2 grup (mnożę macierz i jej transpozycję)
		//4. dzielę wartości kowariancji przez liczbę elementów w wektorze
		//5. łączę macierze kowariancji licząc średnie ważone
		//6. Obliczam odwrotność macierzy kowariancjii
	}
	
	
	public static String[] knn(LinkedList<Point> input_data, LinkedList<PointClassModel> class_data, int selected_method, int n_neighbors){
		String[] output_classes = new String[input_data.size()];	
		LinkedList<Neighborns> neighborns = knn_neighbors(input_data, class_data, selected_method, n_neighbors);
		int index = 0;
		for(Neighborns n : neighborns){
           	String[] el_classes = new String[n.getDistances().size()];
			int i =0;
			for(PointClassModel cm : n.getDistances())
				el_classes[i++] = cm.getN_class();
			
			output_classes[index] = Utils.mostFrequent(el_classes);
		}
		return output_classes;
	}
	
	public static LinkedList<Neighborns> knn_neighbors(LinkedList<Point> input_data, LinkedList<PointClassModel> class_data, int selected_method, int n_neighbors){
		Class<?> cls = null;
		try {
			cls = Class.forName("swd2014.projekt1.utils.Statistic");
		} catch (ClassNotFoundException e1) {e1.printStackTrace();}
		
		Method metric_method = null;
		Class[] paramPoint = new Class[]{Point.class, Point.class};
		
		try {
			switch (selected_method) {
			case 0:// "odległość euklidesowa"
				metric_method = cls.getDeclaredMethod("euclideanDistance", paramPoint);
				break;
				
			case 1://"metryka manhattan"
				metric_method = cls.getDeclaredMethod("manhattanDistance", paramPoint);
				break;		
				
			case 2://"metryka nieskończoność"
				metric_method = cls.getDeclaredMethod("metrykaNieskonczonosc",paramPoint);
				break;	
			case 3:
				metric_method = cls.getDeclaredMethod("mahanalobisDistance", paramPoint);
				break;
			}
		} catch( NoSuchMethodError | NoSuchMethodException e){	e.printStackTrace();}
		
		LinkedList<Neighborns> neighborns = new LinkedList<>();
		for(Point from : input_data){
			LinkedList<PointClassModel> tempDist = new LinkedList<>();
			
			for(PointClassModel to : class_data){
				double dist = 0;
				try {
					dist = (double) metric_method.invoke(null, from, to.getPoint());
				} catch (IllegalAccessException	| IllegalArgumentException| InvocationTargetException e) {	e.printStackTrace();}
				PointClassModel cm = new PointClassModel(to.getN_class(), dist);
				cm.setPoint(from);
				tempDist.add(cm);
			}
			
			Collections.sort(tempDist);
			ListIterator iter = tempDist.listIterator();
			
			LinkedList<PointClassModel> closest_neighborns = new LinkedList<>();
			
			for(int i = 0; i<n_neighbors; i++){
				if(iter.hasNext())
					closest_neighborns.add((PointClassModel) iter.next());
			}
			
			Neighborns nghbrns = new Neighborns(from, closest_neighborns);
			neighborns.add(nghbrns);
		}
		return neighborns;
	}

	public static String[] leave_one_out(double [] data_x, double[] data_y, String[] class_data, int selected_method, int n_neighbors){
				String[] classes = new String[data_x.length];
				

				for(int ex_i =0; ex_i<data_x.length;ex_i++){
					LinkedList<PointClassModel> ex_class_data = new LinkedList<>();
					
					for(int i =0; i<data_x.length ; i++){
						if(i != ex_i)
							ex_class_data.add(new PointClassModel(data_x[i], data_y[i], class_data[i]));
					}
					
					LinkedList<Point> input_data = new LinkedList<>();
					input_data.add(new Point(data_x[ex_i], data_y[ex_i]));
					
					classes[ex_i] = knn(input_data, ex_class_data, selected_method, n_neighbors)[0];
				}
				return classes;
	}

	
}
