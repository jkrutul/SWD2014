package utils;

import java.util.Map;

import com.example.Main;
import com.example.Matrix;

public class DataPrinting {
/**
 * Drukuje macie� w konsoli
 * @param matrix macie� do wydrukowania
 * @param rows liczba wierszy
 * @param cols liczba kolumn
 */
	public static void printMatrix(String[][] matrix, int rows, int cols) {

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++)
				System.out.print(matrix[r][c] + ", ");
			System.out.println();
		}
	}
	
	
	public static void printMatrix(Matrix matrix) {
		String[][] matrix_tab = matrix.getMatrix();
		int rows, cols;
		rows = matrix.getnRows();
		cols = matrix.getnCols();
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++)
				System.out.print(matrix_tab[r][c] + ", ");
			System.out.println();
		}
	}
	
	public static void printVector(String[] rows) {
		for(String attr : rows){
			System.out.print(attr+", ");
		}
		System.out.println();
	}
	
	public static void printVector(int[] rows) {
		for(int attr : rows){
			System.out.print(attr+", ");
		}
		System.out.println();
	}
	
	
	public static void printMap(Map<String,Integer> map){
		for( String s : map.keySet()){
			System.out.println("key: "+ s + " value: "+ map.get(s));
		}
		
	}
}
