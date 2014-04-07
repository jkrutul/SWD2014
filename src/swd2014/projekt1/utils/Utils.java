package swd2014.projekt1.utils;


import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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

import swd2014.projekt1.models.Matrix;
import swd2014.projekt1.models.Point;

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
	

	public static LinkedHashMap<String, Integer> sortMap(HashMap< String, Integer> map ){
	        ValueComparator bvc = new ValueComparator(map);
	        TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);



	        sorted_map.putAll(map);
	        Set<String> key_set =  sorted_map.keySet();
	        Collection<Integer> val_set =  sorted_map.values();
	        
	        LinkedHashMap<String, Integer> sorted_m = new LinkedHashMap<>();
	        Iterator v_iter = val_set.iterator();

	        for( String s : key_set)
	        	sorted_m.put(s, (Integer) v_iter.next());
	        
	        
	        return sorted_m;
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
	
	public static Point[] createPoints(double xs[], double ys[]){
		if(xs.length != ys.length)
			return null;
		
		Point[] points = new Point[xs.length];
		for(int i=0; i < xs.length; i++){
			points[i] = new Point(xs[i], ys[i]);
		}
		
		return points;
	}

	public static Object[] appendValue(String[] obj, Object newObj) {
		 
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();

	}
	
	public static void saveMatrixToFile(Matrix m, File file, String comment, String delimeter){
		
		String[] columnnames = m.getColumnNames();
		String[][] matrix_data = m.getMatrix();
		
		String content = new String();
		if(comment.length()>0){
			comment = comment.replaceAll("\n", "\n#");
			comment+="\n";
			comment="#"+comment;			
			content+=comment;
					
		}

		for(String colname : columnnames){
			content+=colname+delimeter;
		}
		
		content+="\n";
		
		for(String[] row : matrix_data){
			for(String attr : row)
				content+=attr+delimeter;
			content+="\n";
		}
		
		try {
			 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static double[] changeInterval(double[] data, double min, double max){
		double d_max=data[0];	
		double d_min=data[0];
		double [] out = new double[data.length];	
		
		for(double d : data){
			if(d_max<d)
				d_max = d;
			if(d_min>d)
				d_min=d;
		}
		if(d_min == d_max){
			return data;
		}
		
		if(d_min<0){
		
			d_max += Math.abs(d_min);
			int i =0;
			for(double d : data)
				out[i++] = d+Math.abs(d_min);
			
			d_min = 0;
		}else{
			d_max-=d_min;
			int i=0;
			for(double d : data)
				out[i++] = d-d_min;
			d_min=0;
		}
		
		int i=0;
		for(double o : out)
			out[i++] = (o/d_max) * (max-min) + min;
		
		return out;
		
		
	}
	
	public static String mostFrequent(String[] data){
		LinkedHashMap<String, Integer> mapOfOccurrences = new LinkedHashMap<String, Integer>();
		for(String s : data){
			if(mapOfOccurrences.containsKey(s)){
				int oldVal = mapOfOccurrences.get(s);
				mapOfOccurrences.put(s, ++oldVal);
			}else{
				mapOfOccurrences.put(s, 1);
			}
		}
		String mostOccurance =null;
		int max= 0;
		for(String s : mapOfOccurrences.keySet()){
			int count = mapOfOccurrences.get(s);
			if(max<count){
				max = count;
				mostOccurance=s;
			}
		}
		return mostOccurance;
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

