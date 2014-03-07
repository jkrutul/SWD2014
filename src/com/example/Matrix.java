package com.example;

import java.util.Arrays;

public class Matrix {
	public String[][] data;
	private int nRows = 0, nCols = 0;

	public Matrix(int rows, int cols) {
		data = new String[rows][cols];
		this.nRows = rows;
		this.nCols = cols;
	}

	public String[] getColumn(int col_number) {
		if (col_number > nCols || nCols <= 0)
			return null;

		String[] column = new String[nRows];

		for (int r = 0; r < nRows; r++)
			column[r] = data[r][col_number];

		return column;
	}
	
	public String[] getRow(int row_number){
		return data[row_number];
	}

	
	public String[][] appendRow(String[] row){
		String[][] newsr = Arrays.copyOf(data, data.length+1);
		this.data = newsr;
		return newsr;
	}
	
	public String[][] appendColumn(String[] column){
		String [][] newsc = new String[nRows][nCols+1];
	
		int row_counter = 0;	
		for(String[] row : data){
			String[] new_row = Arrays.copyOf(row, nCols+1);
			new_row[nCols] = column[row_counter];
			newsc[row_counter] = new_row;
			row_counter++;
		}

		

		this.data= newsc;
		this.nCols++;
		return newsc;
	}

	public String[][] replaceColumn(String[] column, int columnToReplace){
		//String [][] newm = Arrays.copyOf(data, nCols);
		int i= 0;
		for(String[] row : data){
			row[columnToReplace] = column[i++];
		}
		//this.data = newm;
		return data;		
	}
	
	
	
	public String[][] replaceRow(String[] row, int rowToReplace){
		data[rowToReplace] = row;
		return data;
	}
	
		
	public String[][] getMatrix() {
		return data;
	}

	public void setMatrix(String[][] data) {
		this.data = data;
	}

	public int getnRows() {
		return nRows;
	}

	public void setnRows(int nRows) {
		this.nRows = nRows;
	}

	public int getnCols() {
		return nCols;
	}

	public void setnCols(int nCols) {
		this.nCols = nCols;
	}

}
