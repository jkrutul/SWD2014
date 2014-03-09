package swd2014.projekt1.gui;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import swd2014.projekt1.csv.CsvFileReader;
import swd2014.projekt1.csv.CsvReadWriteSettings;
import swd2014.projekt1.main.Matrix;
import swd2014.projekt1.utils.Converts;
import swd2014.projekt1.utils.DataPrinting;
import swd2014.projekt1.utils.Statistic;
import swd2014.projekt1.utils.Utils;
import org.eclipse.swt.widgets.Label;

public class ApplicationWindow {

	Matrix m;
	CsvFileReader cfr = null;
	String file ="plik1.txt";
	boolean hasColumnsNames = false;
	
	protected Shell shell;

	/**
	 * Launch the application.
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
		
		final Label avgLbl = new Label(grpStatystyka, SWT.NONE);
		avgLbl.setBounds(101, 28, 73, 16);
		avgLbl.setText("0");
		
		Label lblrednia = new Label(grpStatystyka, SWT.NONE);
		lblrednia.setBounds(10, 28, 73, 16);
		lblrednia.setText("Średnia");
		
		Label lblWariancja = new Label(grpStatystyka, SWT.NONE);
		lblWariancja.setBounds(10, 49, 73, 16);
		lblWariancja.setText("Wariancja");
		
		final Label varLbl = new Label(grpStatystyka, SWT.NONE);
		varLbl.setBounds(101, 50, 73, 16);
		varLbl.setText("0");
		
		Label OdchStd = new Label(grpStatystyka, SWT.NONE);
		OdchStd.setText("Odch. std.");
		OdchStd.setBounds(10, 71, 73, 16);
		
		final Label sdLbl = new Label(grpStatystyka, SWT.NONE);
		sdLbl.setText("0");
		sdLbl.setBounds(101, 72, 73, 16);
		
		Label mediana = new Label(grpStatystyka, SWT.NONE);
		mediana.setText("Mediana");
		mediana.setBounds(10, 93, 73, 16);
		
		final Label medLbl = new Label(grpStatystyka, SWT.NONE);
		medLbl.setText("0");
		medLbl.setBounds(101, 94, 73, 16);
		
		Label kwartyl1 = new Label(grpStatystyka, SWT.NONE);
		kwartyl1.setText("1 Kwartyl");
		kwartyl1.setBounds(10, 115, 73, 16);
		
		final Label q1Lbl = new Label(grpStatystyka, SWT.NONE);
		q1Lbl.setText("0");
		q1Lbl.setBounds(101, 116, 73, 16);
		
		Label kwartyl3 = new Label(grpStatystyka, SWT.NONE);
		kwartyl3.setText("3 Kwartyl");
		kwartyl3.setBounds(10, 140, 73, 16);
		
		final Label q3Lbl = new Label(grpStatystyka, SWT.NONE);
		q3Lbl.setText("0");
		q3Lbl.setBounds(101, 140, 73, 16);
		
		Label percentyl = new Label(grpStatystyka, SWT.NONE);
		percentyl.setText("Percentyl");
		percentyl.setBounds(10, 162, 73, 16);
		
		final Label prcLbl = new Label(grpStatystyka, SWT.NONE);
		prcLbl.setText("0");
		prcLbl.setBounds(101, 162, 73, 16);
		
		Button btnZaaduj = new Button(shell, SWT.NONE);
		btnZaaduj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					cfr = new CsvFileReader(file, new CsvReadWriteSettings(",", true, hasColumnsNames));
		 		} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				if(cfr == null)
					return;
				
				m = cfr.getData_matrix();
				
				if(hasColumnsNames){
					Log("\nNazwy kolumn ("+m.getnCols()+"):");
			
					LinkedList<String> column_names = cfr.getColumNames();
					for(String cName : column_names)
						Log(cName);
				
				}

				Log("\nWiersze: ("+m.getnRows()+")\n");

				Log("\nPreferowanie najliczniejszych klas\n");
				int class_attr[] = Utils.classAttribution(m.getColumn(0),5);
				m.appendColumn(Converts.convertToString(class_attr));
				DataPrinting.printMatrix(m,lista);
			}
		});
		btnZaaduj.setBounds(124, 229, 91, 26);
		btnZaaduj.setText("Załaduj");
		
		Button btnOblicz = new Button(shell, SWT.NONE);
		btnOblicz.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				int c =3;
				Log("\nStatystyka dla kolumny "+c+"\n");
				
				float mean;
				mean = Statistic.mean(Converts.convertToFloat(m.getColumn(c)));
				Log("Srednia: "+ mean+"\n");
				avgLbl.setText(Float.toString(mean));
				
				
				float variance;
				variance = Statistic.variance(Converts.convertToFloat(m.getColumn(c)));
				Log("Wariancja: "+ variance+"\n");
				varLbl.setText(Float.toString(variance));
				
				
				float sd;
				sd = Statistic.standardDeviantion(Converts.convertToFloat(m.getColumn(c)));
				Log("Odchylenie standardowe: "+ sd+"\n");
				sdLbl.setText(Float.toString(sd));
				
				
				float median;
				median = Statistic.median(Converts.convertToFloat(m.getColumn(c)));
				Log("Mediana: "+ median+"\n");
				medLbl.setText(Float.toString(median));
				
				float q1;
				q1 = Statistic.q1(Converts.convertToFloat(m.getColumn(c)));
				Log("Kwartyl Q1: "+ q1+"\n");
				q1Lbl.setText(Float.toString(q1));
				
				float q3;
				q3 = Statistic.q3(Converts.convertToFloat(m.getColumn(c)));
				Log("Kwartyl Q3: "+ q3+"\n");
				q3Lbl.setText(Float.toString(q3));
				
				float percentile;
				percentile = (float) Statistic.quantile(Converts.convertToFloat(m.getColumn(c)),5);
				Log("Percentyl 5%: "+ percentile+"\n");
				prcLbl.setText(Float.toString(percentile));
				
			}
		});
		btnOblicz.setBounds(221, 229, 91, 26);
		btnOblicz.setText("Oblicz");
		
		Button btnWybierzPlik = new Button(shell, SWT.NONE);
		btnWybierzPlik.setBounds(10, 229, 108, 26);
		btnWybierzPlik.setText("Wybierz plik");
	}
	
	public static void Log(String s){
		System.out.print(s);
	}
}
