package swd2014.projekt1.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import swd2014.projekt1.main.Main;

public class Utils {
	
	/**
	 *  Liczy ile linii jest w pliku tekstowym
	 * @param filename nazwa pliku
	 * @return liczba linii b�d�cych w pliku
	 * @throws IOException
	 */
	public static int count(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
	
	/***
	 * Zwraca tablicę elementów o wartosciach od 0 do (n/divideBy)
	 * @param divideBy -  na ile częci podzielić zbiór
	 * @param n - liczba elementów zbioru
	 * @return tablica liczb całkowitych o długości n
	 */
	public static int[] discretization(int divideBy, int n){
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
		
	

	public static int getMaxVal(Integer [] tab){
		if(tab.length==0)
			return -1;
		
		int max_val = tab[0];
		for(int i : tab){
			if(i>max_val)
				max_val = i;
		}
		return max_val;
	}
	
	public static Integer getMaxVal(Integer[] tab, int less_than){
		LinkedList<Integer> list = new LinkedList<>();
		for(int i : tab){
			if(i<less_than)
				list.add(i);
		}
		
		if(list.isEmpty())
			return null;
		Collections.sort(list);
		return list.getLast();
	}
	
	
	
	public static int getMaxVal(LinkedList<Integer> list){
		if(list.size() ==0)
			return -1;
		int max_val = list.get(0);
		for(Integer v : list){
			if(v>max_val)
				max_val=v;
		}
		return max_val;
	}
	
	public static int getMaxVal(LinkedList<Integer> list, int max_v){
		if(list.size() ==0)
			return -1;
		int max_val = list.get(0);
		for(Integer v : list){
			if(v>max_val && v<max_v)
				max_val=v;
		}
		return max_val;
	}
	
	public static Integer getMaxVal(Map<String, Integer> map, int lessThan){
		Collection<Integer> valset  = map.values();
		Integer[] values = valset.toArray(new Integer[valset.size()]);
		return getMaxVal(values,lessThan);
	}
	
	public static int getMaxVal(LinkedHashMap<String, Integer> map){
		Collection<Integer> valset  = map.values();
		Integer[] values = valset.toArray(new Integer[valset.size()]);
		return getMaxVal(values);
	}
	

	/**
	 * Funkcja przypisuje klasy dla atrybut�w
	 * @param values - atrybuty
	 * @param howManyClassAssign - liczba klas kt�re przypisa� atrybutom, pozosta�e maj� klas� n+1, 1 jest dla klasy z najwi�ksz� liczb� atrybut�w,
	 * @return tablica klas przypisanym atrybutom
	 */
	public static int[] classAttribution(String[] values, int howManyClassAssign){
		
		if(values == null || values.length<=0)
			return null;

		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();			// string - klasa, integer - liczba element�w klasy
		
		for(String v : values){												//podliczenie ile jest klas i okre�lenie wielko�ci ka�dej z nich 
			if(map.containsKey(v))
				map.put(v,  map.get(v)+1);
			else
				map.put(v, 1);				
		}
		
		//DataPrinting.printMap(map);
		LinkedHashMap<String, Integer> sorted_map = sortMap(map);

		int numberOfClasses = sorted_map.keySet().size();	// maksymalna liczba klas na kt�re mo�na podzieli� atrybuty
		Main.Log("liczba klas na które można podzielić zbiór: " + numberOfClasses+"\n");
		
		Collection<String> keyset  = sorted_map.keySet();
		String[] keySet = keyset.toArray(new String[numberOfClasses]);
		
		Integer[] class_vals = new Integer[numberOfClasses];
		
		
		
		int n_class = 1;
		int index = 0;
		
		while(n_class <= howManyClassAssign){
			class_vals[index++] = n_class++;
		}
		
		// dla pozosta�ych n+1
		while(index < class_vals.length){
			class_vals[index++] = n_class;
		}

		int i=0;
		for(String key : keySet)
			sorted_map.put(key, class_vals[i++]);
		
		int [] split_toClasses = new int[values.length];
		Main.Log(sorted_map.toString());
		int id=0;	
		for(String s : values){
			Integer class_n = sorted_map.get(s);
			split_toClasses[id++] = class_n.intValue();
		}

		return split_toClasses;
	}
	
	
	public static LinkedHashMap<String, Integer> sortMap(HashMap< String, Integer> map ){
	        ValueComparator bvc = new ValueComparator(map);
	        TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);

	        System.out.println("unsorted map: "+map);

	        sorted_map.putAll(map);
	        Set<String> key_set =  sorted_map.keySet();
	        Collection<Integer> val_set =  sorted_map.values();
	        
	        LinkedHashMap<String, Integer> sorted_m = new LinkedHashMap<>();
	        

	        Iterator v_iter = val_set.iterator();

	        for( String s : key_set){
	        	sorted_m.put(s, (Integer) v_iter.next());
	        }
	        
	        
	       	 
	        System.out.println("results: "+sorted_m);	
	        return sorted_m;
	}
	
	/* Funkcja rozdziela do tablic według podanych klas
	 * 
	 */
	public static double[][] splitDataByClasses(double data[], int class_array[]){
		if(data.length != class_array.length){
			System.out.println("DATA SIZE:" +data.length + " CLASS: "+class_array.length);
			return null;
		}
			
		
		// zliczam ile jest klas i ile każda ma elementów
		LinkedHashMap<Integer, Integer> class_group = new LinkedHashMap<>();
		LinkedList<double[]> split_list = new LinkedList<>();
		
		for(int c : class_array)
			if(class_group.containsKey(c)){
				int v = class_group.get(c);
				class_group.put(c, ++v);
			}
			else
				class_group.put(c, 1);
		
		
		int class_count = class_group.size();
		Integer [] keys = class_group.keySet().toArray(new Integer[class_count]);
		LinkedList<LinkedList<Double>> ll = new LinkedList<>();
		
		for(Integer k : keys){
			LinkedList<Double> ld = new LinkedList<>();
			int index = 0;
			for( int kca : class_array){
				if(k.intValue() == kca ){
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
	
	private static LinkedList<String> distingtValues(String data[]){
		LinkedList<String> dsl = new LinkedList<>();
		
		for(String i : data)
			if(!dsl.contains(i)){
				dsl.add(i);
			}
		
		return dsl;
	}
	
	private static LinkedList<Integer> distingtValues(int data[]){
		LinkedList<Integer> dsl = new LinkedList<>();
		
		for(int i : data)
			if(!dsl.contains(i)){
				dsl.add(i);
			}
		
		return dsl;
	}
	
}

class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;
    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}