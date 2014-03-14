package swd2014.projekt1.gui;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import javax.swing.text.View;

import org.apache.commons.math3.stat.StatUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.jfree.data.xy.XYSeriesCollection;

import swd2014.projekt1.csv.CsvFileReader;
import swd2014.projekt1.csv.CsvReadWriteSettings;
import swd2014.projekt1.main.Matrix;
import swd2014.projekt1.utils.Converts;
import swd2014.projekt1.utils.DataPrinting;
import swd2014.projekt1.utils.Statistic;
import swd2014.projekt1.utils.Utils;

public class ApplicationWindow {

	private static Matrix m;
	private static CsvFileReader cfr = null;
	private static String file = "plik1.txt";
	private static boolean hasColumnsNames = false;
	
	private static Label avgLbl, lblrednia, lblWariancja, varLbl, OdchStd, sdLbl, mediana, medLbl, kwartyl1, q1Lbl, kwartyl3, q3Lbl, percentyl, prcLbl;
	

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			
			ApplicationWindow window = new ApplicationWindow();
			window.open();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		

	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");

		final List lista = new List(shell, SWT.BORDER | SWT.V_SCROLL);
		lista.setBounds(10, 10, 231, 213);

		Group grpStatystyka = new Group(shell, SWT.NONE);
		grpStatystyka.setText("Statystyka");
		grpStatystyka.setBounds(252, 10, 184, 213);

		avgLbl = new Label(grpStatystyka, SWT.NONE);
		avgLbl.setBounds(101, 28, 73, 16);
		avgLbl.setText("0");

		lblrednia = new Label(grpStatystyka, SWT.NONE);
		lblrednia.setBounds(10, 28, 73, 16);
		lblrednia.setText("Średnia");

		lblWariancja = new Label(grpStatystyka, SWT.NONE);
		lblWariancja.setBounds(10, 49, 73, 16);
		lblWariancja.setText("Wariancja");

		varLbl = new Label(grpStatystyka, SWT.NONE);
		varLbl.setBounds(101, 50, 73, 16);
		varLbl.setText("0");

		OdchStd = new Label(grpStatystyka, SWT.NONE);
		OdchStd.setText("Odch. std.");
		OdchStd.setBounds(10, 71, 73, 16);

		sdLbl = new Label(grpStatystyka, SWT.NONE);
		sdLbl.setText("0");
		sdLbl.setBounds(101, 72, 73, 16);

		mediana = new Label(grpStatystyka, SWT.NONE);
		mediana.setText("Mediana");
		mediana.setBounds(10, 93, 73, 16);

		medLbl = new Label(grpStatystyka, SWT.NONE);
		medLbl.setText("0");
		medLbl.setBounds(101, 94, 73, 16);

		kwartyl1 = new Label(grpStatystyka, SWT.NONE);
		kwartyl1.setText("1 Kwartyl");
		kwartyl1.setBounds(10, 115, 73, 16);

		q1Lbl = new Label(grpStatystyka, SWT.NONE);
		q1Lbl.setText("0");
		q1Lbl.setBounds(101, 116, 73, 16);

		kwartyl3 = new Label(grpStatystyka, SWT.NONE);
		kwartyl3.setText("3 Kwartyl");
		kwartyl3.setBounds(10, 140, 73, 16);

		q3Lbl = new Label(grpStatystyka, SWT.NONE);
		q3Lbl.setText("0");
		q3Lbl.setBounds(101, 140, 73, 16);

		percentyl = new Label(grpStatystyka, SWT.NONE);
		percentyl.setText("Percentyl");
		percentyl.setBounds(10, 162, 73, 16);

		prcLbl = new Label(grpStatystyka, SWT.NONE);
		prcLbl.setText("0");
		prcLbl.setBounds(101, 162, 73, 16);

		Button btnZaaduj = new Button(shell, SWT.NONE);
		btnZaaduj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
					loadData(lista);
			}
		});
		btnZaaduj.setBounds(124, 229, 91, 26);
		btnZaaduj.setText("Załaduj");

		Button btnOblicz = new Button(shell, SWT.NONE);
		btnOblicz.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				calculateStatisticsForColumn(3);
			}
		});
		btnOblicz.setBounds(221, 229, 91, 26);
		btnOblicz.setText("Oblicz");

		Button btnWybierzPlik = new Button(shell, SWT.NONE);
		btnWybierzPlik.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fileChooser = new FileDialog(shell, SWT.OPEN);
				fileChooser.open();
				file = fileChooser.getFilterPath()+"/"+fileChooser.getFileName();
				Log("Ładuję plik:"+file);

			}
		});
		btnWybierzPlik.setBounds(10, 229, 108, 26);
		btnWybierzPlik.setText("Wybierz plik");
		
		loadData(lista);
	}


	public static void loadData(List lista){
		if ((file != null) && (!file.equals(""))) {
			try {
				cfr = new CsvFileReader(file, new CsvReadWriteSettings(",", true, hasColumnsNames));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			if (cfr == null)
				return;

			m = cfr.getData_matrix();

			if (hasColumnsNames) {
				Log("\nNazwy kolumn (" + m.getnCols() + "):");

				LinkedList<String> column_names = cfr.getColumNames();
				for (String cName : column_names)
					Log(cName);

			}

			Log("\nWiersze: (" + m.getnRows() + ")\n");

			Log("\nPreferowanie najliczniejszych klas\n");
			int class_attr[] = Utils.classAttribution(m.getColumn(0), 5);
			m.appendColumn(Converts.convertToString(class_attr));
			DataPrinting.printMatrix(m, lista);
			drawChartForColumns( new int[]{0,1,2,3} );
			calculateStatisticsForColumn(3);
		}
	}
	
	public static void calculateStatisticsForColumn(int column_index){
		
		double[] data = Converts.convertToDouble(m.getColumn(column_index));

		Log("\nStatystyka dla kolumny " + column_index + "\n");

		double mean;		
		mean = StatUtils.mean(data);
		Log("Srednia: " + mean + "\n");
		avgLbl.setText(Double.toString(mean));

		double variance;
		variance = StatUtils.variance(data);
		Log("Wariancja: " + variance + "\n");
		varLbl.setText(Double.toString(variance));

		double sd;
		sd = Statistic.standardDeviantion(variance);
		Log("Odchylenie standardowe: " + sd + "\n");
		sdLbl.setText(Double.toString(sd));

		double median;
		median = StatUtils.percentile(data, 50);
		Log("Mediana: " + median + "\n");
		medLbl.setText(Double.toString(median));

		double q1;
		q1 = StatUtils.percentile(data, 25);
		Log("Kwartyl Q1: " + q1 + "\n");
		q1Lbl.setText(Double.toString(q1));

		double q3;
		q3 = StatUtils.percentile(data, 75);
		Log("Kwartyl Q3: " + q3 + "\n");
		q3Lbl.setText(Double.toString(q3));

		double percentile;
		percentile = StatUtils.percentile(data, 5);
		Log("Percentyl 5%: " + percentile + "\n");
		prcLbl.setText(Double.toString(percentile));
		
	}
	
	public static void drawChartForColumns(int[] column_indexes) {

		LinkedList<double[]> series = new LinkedList<>();
		for(int c_index : column_indexes){
			double[] s = Converts.convertToDouble(m.getColumn(c_index));
			series.add(s);
		}		
		Charts.chartScatterPlot((XYSeriesCollection) Charts.createDataset(series));
	}
	
	
	
	public static void Log(String s) {
		System.out.print(s);
	}
}
