package swd2014.projekt1.utils;

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
	
	
	public static double quantile(float[] data, float percent){
		double t = (percent/100) * (data.length-1);
		int i = (int) t;
		return (i+1-t)*data[i] + (t-i)* data[i+1];
	}
	
	

}
