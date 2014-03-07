package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import com.example.Matrix;

import utils.DataPrinting;

public class CsvFileReader {
	private LinkedList<String[]> rows;
	
	//private String[][] data_matrix;
	private Matrix matrix;
	
	private LinkedList<String> columNames;
	private CsvReadWriteSettings settings;

	public CsvFileReader() {
		this.rows = new LinkedList<>();
		this.columNames = new LinkedList<>();
		this.settings = new CsvReadWriteSettings();
	}

	public CsvFileReader(String path_to_file) throws FileNotFoundException {
		this();
		
		read_csv_file(path_to_file);

	}

	public CsvFileReader(String path_to_file, CsvReadWriteSettings settings)
			throws FileNotFoundException {
		this();
		this.settings = settings;
		read_csv_file(path_to_file);
	}

	/**
	 * Funkcja pobiera z pliku dane w formacie csv,
	 * 
	 * @param path_to_file
	 *            œcie¿ka do pliku z danymi
	 * @return macierz stringów zawieraj¹ca dane
	 * @throws FileNotFoundException
	 */
	private String[][] read_csv_file(String path_to_file)
			throws FileNotFoundException {
		String delimeter;
		boolean remove_white_spaces, first_row_have_columnames;

		delimeter = settings.getDelimeter();
		remove_white_spaces = settings.isRemove_white_spaces();
		first_row_have_columnames = settings.isFirstRowHasColumnNames();

		File f = new File(path_to_file);
		if (!f.exists())
			return null;
		Scanner scanner = new Scanner(f);
		String[] columnnames = null;

		if (first_row_have_columnames) {
			String line;
			do {
				line = scanner.nextLine();
			} while (line.startsWith("#"));

			line = line.replaceAll("\\s", "");
			columnnames= line.split(delimeter);
			for (String columnname : columnnames) {
				this.columNames.add(columnname);
			}
		}

		if (remove_white_spaces)
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.startsWith("#")) {
					line = line.replaceAll("\\s", "");
					String[] row = line.split(delimeter);
					this.rows.add(row);
				}
			}
		else
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.startsWith("#")) {
					String[] row = line.split(delimeter);
					rows.add(row);
				}
			}

		scanner.close();

		int column_count, row_count;
		row_count = rows.size();
		
		if(columnnames!=null)
			column_count = columnnames.length;
		else{
			int max=rows.getFirst().length;
			for(String [] row : rows)
				if(row.length>max)
					max=row.length;
			column_count = max;
		}
			

		this.matrix = new Matrix(row_count, column_count);


		for (int r = 0; r < row_count; r++) {
			matrix.data[r] = rows.get(r);

		}

		


		return matrix.data;
	}





	public LinkedList<String[]> getRows() {
		return rows;
	}

	public LinkedList<String> getColumNames() {
		return columNames;
	}

	public Matrix getData_matrix() {
		return this.matrix;
	}



}
