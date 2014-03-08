package utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Utils {
	
	/**
	 *  Liczy ile linii jest w pliku tekstowym
	 * @param filename nazwa pliku
	 * @return liczba linii bêd¹cych w pliku
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
	 * Zwraca tablicê elementów o wartoœciach od 0 do (n/divideBy)
	 * @param divideBy -  na ilê czêœci podzieliæ zbiór
	 * @param n - liczba elementów zbioru
	 * @return tablica liczb ca³kowitych o d³ugoœci n
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
	
	public static Integer getMaxVal(LinkedHashMap<String, Integer> map, int lessThan){
		Collection<Integer> valset  = map.values();
		Integer[] values = valset.toArray(new Integer[valset.size()]);
		return getMaxVal(values,lessThan);
	}
	
	public static int getMaxVal(LinkedHashMap<String, Integer> map){
		Collection<Integer> valset  = map.values();
		Integer[] values = valset.toArray(new Integer[valset.size()]);
		return getMaxVal(values);
	}
	

	
	public static int[] classAttribution(String[] values){
		if(values == null || values.length<=0)
			return null;

		//String[] valuesToClass = new String[values.length];
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		
		for(String v : values){			
			if(map.containsKey(v))
				map.put(v,  map.get(v)+1);
			else
				map.put(v, 1);				
		}

		/*
		Collection<String> keyset  = map.keySet();
		Collection<Integer> valset  = map.values();
		
		String[] distingKeyset = keyset.toArray(new String[keyset.size()]);
		Integer[] distingValues = valset.toArray(new Integer[valset.size()]);
		
		
		LinkedList<Integer> vals = (LinkedList<Integer>) map.values();
		LinkedList<Integer> vals_cpy = (LinkedList<Integer>) map.values();
		*/
		int numberOfClasses = map.keySet().size();
		Collection<Integer> valset  = map.values();
		Integer[] vals = valset.toArray(new Integer[numberOfClasses]);
		
		Collection<String> keyset  = map.keySet();
		String[] keySet = keyset.toArray(new String[keyset.size()]);
		
		Integer[] class_vals = new Integer[numberOfClasses];
				

		int n_class = 1;
		Integer max_val =getMaxVal(vals);
		
		while(n_class <= numberOfClasses){
			int index= 0;
			for(Integer i : vals){
				if(i.equals(max_val)){					
					class_vals[index]= n_class;
				}
				index++;
			}
			
			max_val = getMaxVal(map, max_val);
			if(max_val == null)
				break;
			n_class++;
		}
		
		
		
		int i=0;
		for(String key : keySet)
			map.put(key, class_vals[i++]);
		
		int [] split_toClasses = new int[values.length];
		
		int id=0;
		for(String s : values){
			split_toClasses[id++] = map.get(s);
		}
		
		
		return split_toClasses;
	}
	
	

	

}
