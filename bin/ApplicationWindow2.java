package swd2014.projekt1.gui;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.math3.stat.StatUtils;
import org.jfree.data.xy.XYSeriesCollection;

import swd2014.projekt1.csv.CsvFileReader;
import swd2014.projekt1.csv.CsvReadWriteSettings;
import swd2014.projekt1.main.Matrix;
import swd2014.projekt1.utils.Converts;
import swd2014.projekt1.utils.DataPrinting;
import swd2014.projekt1.utils.Statistic;
import swd2014.projekt1.utils.Utils;
import swd2014.projekt1.xls.UniParser;
import swd2014.projekt1.xls.XlsParseContainer;

public class ApplicationWindow2 extends javax.swing.JFrame{


	private static final long serialVersionUID = 1L;
	
	private static Matrix m;
	private static CsvFileReader cfr = null;
	private static String file = "plik1.txt";
	private static boolean hasColumnsNames = false;
	
	//private static Label avgLbl, lblrednia, lblWariancja, varLbl, OdchStd, sdLbl, mediana, medLbl, kwartyl1, q1Lbl, kwartyl3, q3Lbl, percentyl, prcLbl;
	
	
    private javax.swing.JButton calculate_file;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea consolTextArea;
    private javax.swing.JButton load_file;
    private javax.swing.JButton open_file;
   
    private JFileChooser fc;
    

    public ApplicationWindow2() {
    	setResizable(false);
        initComponents();
    }
    
    
    private void initComponents() {
        fc = new JFileChooser();
        FileFilter filter = new FileFilter() {
			@Override
			public String getDescription() {
				return new String("Pliki txt/csv/xls");
			}
			
			@Override
			public boolean accept(File f) {
				if(f.isDirectory())
					return true;
				else if(f.getName().endsWith(".txt"))
					return true;
				else if(f.getName().endsWith(".csv"))
					return true;
				else if(f.getName().endsWith(".xls"))
					return true;
				return false;
			}
		};
        fc.setFileFilter(filter);
        

        jScrollPane1 = new javax.swing.JScrollPane();
        consolTextArea = new javax.swing.JTextArea();
        consolTextArea.setLineWrap(true);
        open_file = new javax.swing.JButton();
        load_file = new javax.swing.JButton();
        calculate_file = new javax.swing.JButton();
        

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        consolTextArea.setColumns(20);
        consolTextArea.setRows(5);
        consolTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder("konsola"));
        jScrollPane1.setViewportView(consolTextArea);

        open_file.setText("otwórz plik");
        open_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open_fileActionPerformed(evt);
            }
        });

        load_file.setText("załaduj");
        load_file.setToolTipText("");
        load_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	load_fileActionPerformed(evt);
            }
        });

        calculate_file.setText("oblicz");
        calculate_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculate_fileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(open_file)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(load_file)
        					.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
        					.addComponent(calculate_file))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
        					.addGap(0, 0, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(open_file)
        				.addComponent(calculate_file)
        				.addComponent(load_file))
        			.addGap(0, 11, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);

        pack();
		//loadData(); //powoduje NPE po spaczkowaniu mavenem
    }
    
	public static void main(String[] args) {
		/*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ApplicationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplicationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplicationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
*/
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApplicationWindow2().setVisible(true);
            }
        });

	}
	
	
    private void open_fileActionPerformed(java.awt.event.ActionEvent evt) {                                          
    	 int returnVal = fc.showOpenDialog(ApplicationWindow2.this);
    	 
         if (returnVal == JFileChooser.APPROVE_OPTION) {
             File file = fc.getSelectedFile();
             this.file = file.getAbsolutePath();
             
         } else {
             Log("Open command cancelled by user.\n");
         }
         

		Log("Ładuję plik: "+this.file);
    } 
    
    private void load_fileActionPerformed(java.awt.event.ActionEvent evt) {                                          
		loadData();
    }  

    private void calculate_fileActionPerformed(java.awt.event.ActionEvent evt) {                                               
		calculateStatisticsForColumn(3);
    }  





	public void loadData() {
		if ((file != null) && (!file.equals(""))) {

			if (file.endsWith(new String(".txt"))
					|| file.endsWith(new String(".csv"))) {

				try {
					cfr = new CsvFileReader(file, new CsvReadWriteSettings(",",
							true, hasColumnsNames));
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
				DataPrinting.printMatrix(m, consolTextArea);
				
				Utils.splitDataByClasses(Converts.convertToDouble(m.getColumn(0)), Converts.convertToInt(m.getColumn(5)));
				
				drawChartForColumns(new int[] { 0, 1, 2, 3 });
				calculateStatisticsForColumn(3);

			}
			else if (file.endsWith(new String(".xls")))
			{
				UniParser parser = new UniParser();
				
				XlsParseContainer result = parser.parse(file);
				
				m = result.getValues();
				
				Log("\nWiersze: (" + m.getnRows() + ")\n");
				
				Log("\nPreferowanie najliczniejszych klas [max 5]\n");
				int class_attr[] = Utils.classAttribution(m.getColumn(0), 5);
				
				m.appendColumn(Converts.convertToString(class_attr));
				
				DataPrinting.printMatrix(m, consolTextArea);
				
				drawChartForColumns(new int[] { 0, 1, 2, 3 });
				
				calculateStatisticsForColumn(3);
			}
		}
	}
	
	public void calculateStatisticsForColumn(int column_index){
		
		double[] data = Converts.convertToDouble(m.getColumn(column_index));

		Log("\nStatystyka dla kolumny " + column_index + "\n");

		double mean;		
		mean = StatUtils.mean(data);
		Log("Srednia: " + mean + "\n");
		//avgLbl.setText(Double.toString(mean));

		double variance;
		variance = StatUtils.variance(data);
		Log("Wariancja: " + variance + "\n");
		//varLbl.setText(Double.toString(variance));

		double sd;
		sd = Statistic.standardDeviantion(variance);
		Log("Odchylenie standardowe: " + sd + "\n");
		//sdLbl.setText(Double.toString(sd));

		double median;
		median = StatUtils.percentile(data, 50);
		Log("Mediana: " + median + "\n");
		//medLbl.setText(Double.toString(median));

		double q1;
		q1 = StatUtils.percentile(data, 25);
		Log("Kwartyl Q1: " + q1 + "\n");
		//q1Lbl.setText(Double.toString(q1));

		double q3;
		q3 = StatUtils.percentile(data, 75);
		Log("Kwartyl Q3: " + q3 + "\n");
		//q3Lbl.setText(Double.toString(q3));

		double percentile;
		percentile = StatUtils.percentile(data, 5);
		Log("Percentyl 5%: " + percentile + "\n");
		//prcLbl.setText(Double.toString(percentile));
		
	}
	
	public static void drawChartForColumns(int[] column_indexes) {

		LinkedList<double[]> series = new LinkedList<>();
		for(int c_index : column_indexes){
			double[] s = Converts.convertToDouble(m.getColumn(c_index));
			series.add(s);
		}		
		Charts.chartScatterPlot((XYSeriesCollection) Charts.createDataset(series));
	}
	
	public static void drawChartForColumns(int[] column_indexes, double[][] data ) {
		
		
		LinkedList<double[]> series = new LinkedList<>();
		for(int c_index : column_indexes){
			double[] s = data[c_index];
			series.add(s);
		}		
		Charts.chartScatterPlot((XYSeriesCollection) Charts.createDataset(series));
	}
	
	
	
	public void Log(String s) {
		consolTextArea.append(s);
		
	}
}
