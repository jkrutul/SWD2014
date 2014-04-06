package swd2014.projekt1.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Converts {
	
	

	public static int[] convertStringData(String[] data, boolean alphabeticalOrder){
		if(alphabeticalOrder){
			Arrays.sort(data);
		}
		int[] converted = new int[data.length];
		ArrayList<String> unique_visited = new ArrayList<>();
		int i = 0;
		for(String s : data){
			if(!unique_visited.contains(s))
				unique_visited.add(s);
			converted[i++]=unique_visited.indexOf(s);
		}
		
		return converted;
	}

	
	public static String[] convertToString(int [] numbers){
		String[] strings = new String[numbers.length];
		int i =0;
		for(int n : numbers)
			strings[i++] = String.valueOf(n);
		
		return strings;
			
	}
	
	public static String[] convertToString(double [] numbers){
		String[] strings = new String[numbers.length];
		int i =0;
		for(double n : numbers)
			strings[i++] = String.valueOf(n);
		
		return strings;	
	}


	
	public static float[] convertToFloat(String[] numbers){
		float[] floats = new float[numbers.length];
		int i =0;
		for(String s : numbers)
			floats[i++] = Float.valueOf(s);
		
		return floats;
			
	}
	
	public static double[] convertToDouble(String[] numbers){
		double[] doubles = new double[numbers.length];
		int i =0;
		for(String s : numbers)
			doubles[i++] = Double.valueOf(s);
		
		return doubles;
			
	}
	
	public static int[] convertToInt(String[] numbers){
		int[] ints = new int[numbers.length];
		int i =0;
		for(String s : numbers)
			ints[i++] = Integer.valueOf(s);
		
		return ints;
			
	}
	
	public static double[][] convertDoubleArray(Double [][] matrix_Double){
		int size = matrix_Double.length;
		double [][] d_ = new double[size][];
		
		int i = 0;

		for(Double ditems[] : matrix_Double){
			d_[i] = new double[ditems.length];
			int j = 0;
			for(Double ditem : ditems){
				d_[i][j++] = ditem.doubleValue();
			}
			i++;
		}
		return d_;
	}
	
}
