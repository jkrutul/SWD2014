package swd2014.projekt1.utils;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.hadoop.hbase.util.Hash;

import swd2014.projekt1.models.DataAndClass;

public class DataSplit {
	public static String keys[];
	private static LinkedList<DataAndClass> dataAndClass = new LinkedList<>();
	
	
	/* Funkcja rozdziela do tablic według podanych klas
	 * 
	 */
	public static double[][] splitDataByClasses(double data[], String class_array[]){
		if(data.length != class_array.length){
			System.out.println("DATA SIZE:" +data.length + " CLASS: "+class_array.length);
			return null;
		}
			
		// zliczam ile jest klas i ile każda ma elementów
		LinkedHashMap<String, Integer> class_group = new LinkedHashMap<>();
		//LinkedList<double[]> split_list = new LinkedList<>();
		
		for(String c : class_array)
			if(class_group.containsKey(c)){
				int v = class_group.get(c);
				class_group.put(c, ++v);
			}
			else
				class_group.put(c, 1);
		
		
		int class_count = class_group.size();
		//String [] keys = class_group.keySet().toArray(new String[class_count]);
	    keys = class_group.keySet().toArray(new String[class_count]);
		LinkedList<LinkedList<Double>> ll = new LinkedList<>();
		
		for(String k : keys){
			LinkedList<Double> ld = new LinkedList<>();
			int index = 0;
			for( String kca : class_array){
				if(k.equals(kca) ){
					ld.add(data[index]);
				}
				index++;
			}
			
			ll.add(ld);
		}
		
		// convert to double[][]
		Double [][] matrix_Double = new Double[ll.size()][];
				
		int index =0 ;
		for(LinkedList<Double> ld : ll){
			matrix_Double[index++] = ld.toArray(new Double[ld.size()]);
		}
		ll= null;
		
		double[][] d_ = Converts.convertDoubleArray(matrix_Double);	
		return d_;
	}
	
	/**
	 * Funkcja przypisuje klasy dla atrybut�w
	 * @param values - atrybuty
	 * @param howManyClassAssign - liczba klas kt�re przypisa� atrybutom, pozosta�e maj� klas� n+1, 1 jest dla klasy z najwi�ksz� liczb� atrybut�w,
	 * @return tablica klas przypisanym atrybutom
	 */
	public static int[] classNumberAttribution(String[] values, int howManyClassAssign){
		howManyClassAssign--;
		if(values == null || values.length<=0)
			return null;

		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();			// string - klasa, integer - liczba element�w klasy
		
		for(String v : values){												//podliczenie ile jest klas i okre�lenie wielko�ci ka�dej z nich 
			if(map.containsKey(v))
				map.put(v,  map.get(v)+1);
			else
				map.put(v, 1);				
		}
		LinkedHashMap<String, Integer> sorted_map = Utils.sortMap(map);

		int numberOfClasses = sorted_map.keySet().size();	// maksymalna liczba klas na kt�re mo�na podzieli� atrybuty
		if(howManyClassAssign>numberOfClasses)
			howManyClassAssign = numberOfClasses;
		Collection<String> keyset  = sorted_map.keySet();
		String[] keySet = keyset.toArray(new String[numberOfClasses]);
		Integer[] class_vals = new Integer[numberOfClasses];
		int n_class = 1;
		int index = 0;
		
		while(n_class <= howManyClassAssign)
			class_vals[index++] = n_class++;

		while(index < class_vals.length)
			class_vals[index++] = n_class;
		
		int i=0;
		for(String key : keySet)
			sorted_map.put(key, class_vals[i++]);
		
		int [] split_toClasses = new int[values.length];
		//Main.Log(sorted_map.toString());
		int id=0;	
		for(String s : values){
			Integer class_n = sorted_map.get(s);
			split_toClasses[id++] = class_n.intValue();
		}
		return split_toClasses;
	}
	
	
	public static DataAndClass[] classNameAttribution(String[] class_val, double[] data_val){
		DataAndClass[] data_class = new DataAndClass[data_val.length];
		
		int i =0;
		for(double d : data_val){
			DataAndClass dac = new DataAndClass(d, class_val[i]);
			data_class[i++] = dac;
		}
		
		
		return data_class;
	}
	
	/***
	 * Zwraca tablicę elementów o wartosciach od 0 do (n/divideBy)
	 * @param divideBy -  na ile częci podzielić zbiór
	 * @param n - liczba elementów zbioru
	 * @return tablica liczb całkowitych o długości n
	 */
	private static int[] genTableOfClassElements(int divideBy, int n){
		if(divideBy<=0 || n<=0)
			return null;
		
		int[] tab = new int[n];
		int elementsInGroup = (int) Math.floor(n/divideBy);
		
		int disc= 0;
		for(int i = 0; i<n; ){
			tab[i++] = disc;
			if(i!= 0 && (i%elementsInGroup) == 0){
				disc++;		
			}
		}
		return tab;
	}
	
	public static DataAndClass[] discretization(double data[], int divideBy, boolean saveInputOrder){
		double[] unsorted_data = data.clone();
		double[] sorted_data;		
		Arrays.sort(data);
		sorted_data = data;
		//ArrayList<Double> sorted_data = new ArrayList<>();
		LinkedList<Integer> checkedIndexes = new LinkedList<>();
		
		DataAndClass[] dac = new DataAndClass[data.length];
		
		
		//for(double d : data){
		//	sorted_data.add(d);
		//}
		
		int class_tab[] = genTableOfClassElements(divideBy, unsorted_data.length);
		
		if(!saveInputOrder){
			for(int i = 0 ; i <sorted_data.length; i++){
				dac[i] = new DataAndClass(sorted_data[i], Integer.toString(class_tab[i]));
			}
			return dac;
		}else{
			int i = 0;
			for(double d : unsorted_data){
				int index=-1;
				for( int idx=0; idx<sorted_data.length ; idx++){
					if(sorted_data[idx] == d && !checkedIndexes.contains(idx)){
						index = idx;
						break;
					}
				}
				
				dac[i++] = new DataAndClass(d, Integer.toString(class_tab[index]));
				checkedIndexes.add(index);
			}
			
		}
		
		return dac;
		
	}

	public static DataAndClass[] splitData(double data[], String class_es[]){
		DataAndClass[] dnc = new DataAndClass[data.length];
		
		
		return dnc;
	}
}
