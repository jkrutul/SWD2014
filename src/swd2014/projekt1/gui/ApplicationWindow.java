package swd2014.projekt1.gui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ApplicationWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Matrix m;
	private static CsvFileReader cfr = null;
	private static String file = "plik1.txt";
	private static boolean hasColumnsNames = true;
	//private JFrame frame;
    // Variables declaration - do not modify  
	private javax.swing.JButton calculate_file;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea consolTextArea;
	private javax.swing.JButton reload_fileButton, open_fileButton, drawChartButton, filesaveButton;
    private javax.swing.JComboBox colSelectComboBox, groupComboBox;
    private javax.swing.JCheckBox getColNamesCheckBox;
    private javax.swing.JLabel jLabel1, jLabel2, jLabel3,jLabel4,jLabel5;
    private javax.swing.JPanel jPanel1,jPanel2,statisticPanel,chartPanel;
    private javax.swing.JComboBox xComboBox, yComboBox, zComboBox;
    // End of variables declaration      
	private JFileChooser fc;
	private JTextField delimeterTF;
	private JLabel filenameLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (InstantiationException e) {
	        e.printStackTrace();
	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    } catch (UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
	        
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationWindow window = new ApplicationWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ApplicationWindow() {
		setTitle("Główne okno aplikacji");
		initializeGUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeGUI() {
		this.setLocationRelativeTo(null);
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
	    
	    File path_to_project_dir = new File(System.getProperty("user.dir"));
	    fc.setCurrentDirectory(path_to_project_dir);
        jPanel1 = new javax.swing.JPanel();
        open_fileButton = new javax.swing.JButton();
        getColNamesCheckBox = new javax.swing.JCheckBox();
        getColNamesCheckBox.setSelected(true);
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        consolTextArea = new javax.swing.JTextArea();
        consolTextArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        statisticPanel = new javax.swing.JPanel();

        jLabel1 = new javax.swing.JLabel();
        calculate_file = new javax.swing.JButton();
        colSelectComboBox = new javax.swing.JComboBox();
        chartPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        xComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        yComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        groupComboBox = new javax.swing.JComboBox();
        drawChartButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        zComboBox = new javax.swing.JComboBox();
		reload_fileButton = new javax.swing.JButton();
        filesaveButton = new JButton("zapisz");

        
		enable_disableButtons(false);
		
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("plik"));

        open_fileButton.setText("otwórz");
        open_fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open_fileButtonActionPerformed(evt);
            }
        });

        getColNamesCheckBox.setText("pobierz kolumny");
        getColNamesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               // getColNamesCheckBoxActionPerformed(evt);
            }
        });
        
        JLabel lblSeparator = new JLabel("separator:");
        
        delimeterTF = new JTextField();
        delimeterTF.setText(",");
        delimeterTF.setColumns(10);
        


        
        filenameLabel = new JLabel("nie załadowano pliku");
        filenameLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
        filenameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        

        		
       reload_fileButton.setText("odśwież");
       reload_fileButton.setToolTipText("");
       reload_fileButton.addActionListener(new java.awt.event.ActionListener() {
        		           	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		                load_fileButtonActionPerformed(evt);
        		            }
       });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(getColNamesCheckBox)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(lblSeparator, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(delimeterTF, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(filenameLabel, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(open_fileButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(filesaveButton, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(reload_fileButton, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addComponent(open_fileButton)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(filesaveButton)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(reload_fileButton)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(filenameLabel)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblSeparator)
        				.addComponent(delimeterTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(getColNamesCheckBox)
        			.addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1.setLayout(jPanel1Layout);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("konsola"));

        consolTextArea.setColumns(20);
        consolTextArea.setRows(5);
        consolTextArea.setBorder(null);
        jScrollPane1.setViewportView(consolTextArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        statisticPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("statystyka"));

        jLabel1.setText("kolumna:");

        calculate_file.setText("oblicz");
        calculate_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculate_fileActionPerformed(evt);
            }
        });

        colSelectComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5" }));

        javax.swing.GroupLayout gl_statisticPanel = new javax.swing.GroupLayout(statisticPanel);
        statisticPanel.setLayout(gl_statisticPanel);
        gl_statisticPanel.setHorizontalGroup(
            gl_statisticPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl_statisticPanel.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colSelectComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(calculate_file, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        gl_statisticPanel.setVerticalGroup(
            gl_statisticPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl_statisticPanel.createSequentialGroup()
                .addGroup(gl_statisticPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(colSelectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calculate_file)
                .addContainerGap())
        );

        chartPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("wykres"));

        jLabel2.setText("oś X:");

        xComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3" }));

        jLabel3.setText("oś Y:");

        yComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));

        jLabel4.setText("wg:");

        groupComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3" }));


        drawChartButton.setText("rysuj");
        drawChartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	drawChartActionPerformed(evt);
            }
        });

        jLabel5.setText("oś Z:");

        zComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4" }));

        javax.swing.GroupLayout gl_chartPanel = new javax.swing.GroupLayout(chartPanel);
        chartPanel.setLayout(gl_chartPanel);
        gl_chartPanel.setHorizontalGroup(
            gl_chartPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(drawChartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(gl_chartPanel.createSequentialGroup()
                .addGroup(gl_chartPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gl_chartPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(yComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        gl_chartPanel.setVerticalGroup(
            gl_chartPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gl_chartPanel.createSequentialGroup()
                .addGroup(gl_chartPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gl_chartPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gl_chartPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(zComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gl_chartPanel.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(drawChartButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        					.addComponent(chartPanel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
        					.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
        				.addComponent(statisticPanel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(statisticPanel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(chartPanel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(114, Short.MAX_VALUE))
        		.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
        );
        getContentPane().setLayout(layout);

        pack();

	}

	
	//FUNKCJE WYWOŁYWANE PRZEZ PRZYCISKI
	private void open_fileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		int returnVal = fc.showOpenDialog(ApplicationWindow.this);
		File file = null;
		if(returnVal !=JFileChooser.APPROVE_OPTION){
			Log("Plik nie został wybrany");

			return;
		}

		file = fc.getSelectedFile();
		this.file = file.getAbsolutePath();
		Log("Ładuję plik: " + this.file);
		filenameLabel.setText(file.getName());
		load_fileButtonActionPerformed(null);		
	}

	private void load_fileButtonActionPerformed(java.awt.event.ActionEvent evt) {
 
		if ((file != null) && (!file.equals(""))) {
			if (file.endsWith(new String(".txt")) || file.endsWith(new String(".csv"))) {
				hasColumnsNames = (getColNamesCheckBox.isSelected()) ? true : false;
				String delimeter = delimeterTF.getText();
				cfr = new CsvFileReader(file, new CsvReadWriteSettings(delimeter, true, hasColumnsNames));
				
				m = cfr.getData_matrix();
				String[] columNamesArray = null;	
				
				if (hasColumnsNames) {
					Log("\nNazwy kolumn (" + m.getnCols() + "):");

					LinkedList<String> column_names = cfr.getColumNames();
					for (String cName : column_names)
						Log(cName);
				}
				
				columNamesArray = cfr.getColumNames().toArray(new String[cfr.getColumNames().size()]);
				setComboBoxModel(columNamesArray);
				
				TableGUI tg = new TableGUI(columNamesArray, m.data);
				Log("\nWiersze: (" + m.getnRows() + ")\n");
				DataPrinting.printMatrix(m, consolTextArea);
				enable_disableButtons(true);
				/*
				Log("\nPreferowanie najliczniejszych klas\n");
				int class_attr[] = Utils.classAttribution(m.getColumn(0), 5);
				m.appendColumn(Converts.convertToString(class_attr));


				Utils.splitDataByClasses(
						Converts.convertToDouble(m.getColumn(0)),
						Converts.convertToInt(m.getColumn(5)));

				drawChartForColumns(new int[] { 0, 1, 2, 3 });
				*/

			} else if (file.endsWith(new String(".xls"))) {
				UniParser parser = new UniParser();

				XlsParseContainer result = parser.parse(file);

				m = result.getValues();

				Log("\nWiersze: (" + m.getnRows() + ")\n");
				/*

				Log("\nPreferowanie najliczniejszych klas [max 5]\n");
				int class_attr[] = Utils.classAttribution(m.getColumn(0), 5);

				m.appendColumn(Converts.convertToString(class_attr));

				DataPrinting.printMatrix(m, consolTextArea);

				drawChartForColumns(new int[] { 0, 1, 2, 3 });
				*/


			}
		}
		
		
		

	}

	private void calculate_fileActionPerformed(java.awt.event.ActionEvent evt) {
		
		int column_index = colSelectComboBox.getSelectedIndex();
		double[] data = Converts.convertToDouble(m.getColumn(column_index));

		Log("\nStatystyka dla kolumny " + column_index + "\n");

		double mean;
		mean = StatUtils.mean(data);
		Log("Srednia: " + mean + "\n");
		// avgLbl.setText(Double.toString(mean));

		double variance;
		variance = StatUtils.variance(data);
		Log("Wariancja: " + variance + "\n");
		// varLbl.setText(Double.toString(variance));

		double sd;
		sd = Statistic.standardDeviantion(variance);
		Log("Odchylenie standardowe: " + sd + "\n");
		// sdLbl.setText(Double.toString(sd));

		double median;
		median = StatUtils.percentile(data, 50);
		Log("Mediana: " + median + "\n");
		// medLbl.setText(Double.toString(median));

		double q1;
		q1 = StatUtils.percentile(data, 25);
		Log("Kwartyl Q1: " + q1 + "\n");
		// q1Lbl.setText(Double.toString(q1));

		double q3;
		q3 = StatUtils.percentile(data, 75);
		Log("Kwartyl Q3: " + q3 + "\n");
		// q3Lbl.setText(Double.toString(q3));

		double percentile;
		percentile = StatUtils.percentile(data, 5);
		Log("Percentyl 5%: " + percentile + "\n");
		// prcLbl.setText(Double.toString(percentile));
	}
	
	private void drawChartActionPerformed(java.awt.event.ActionEvent evt){
		int x_col = xComboBox.getSelectedIndex();
		int y_col = yComboBox.getSelectedIndex();
		int g_col = groupComboBox.getSelectedIndex();
		drawChart(x_col, y_col, g_col);
	}
	
	//FUNKCJE WYWOŁYWANE PRZEZ BUTTONY -- KONIEC



	public static void drawChartForColumns(int[] column_indexes) {

		LinkedList<double[]> series = new LinkedList<>();
		for (int c_index : column_indexes) {
			double[] s = Converts.convertToDouble(m.getColumn(c_index));
			series.add(s);
		}
		Charts.chartScatterPlot((XYSeriesCollection) Charts.createDataset(series));
	}

	public static void drawChart(int x_column, int y_column, int groupBy ){
		double[] x_data =  Converts.convertToDouble(m.getColumn(x_column));
		double[] y_data =  Converts.convertToDouble(m.getColumn(y_column));
		String[] group_data =  m.getColumn(groupBy);
		
		int [] class_array = Utils.classAttribution(group_data, 5);
		
		double[][] x_grouped = Utils.splitDataByClasses(x_data, class_array);
		double[][] y_grouped = Utils.splitDataByClasses(y_data, class_array);
		
		
		
		Charts.chartScatterPlot((XYSeriesCollection) Charts.createDataset(x_grouped, y_grouped));
		
		
	}
	
	public static void drawChartForColumns(int[] column_indexes, double[][] data) {

		LinkedList<double[]> series = new LinkedList<>();
		for (int c_index : column_indexes) {
			double[] s = data[c_index];
			series.add(s);
		}
		Charts.chartScatterPlot((XYSeriesCollection) Charts
				.createDataset(series));
	}

	public void Log(String s) {
		consolTextArea.append(s);

	}
	
	private void enable_disableButtons(boolean enable){
        zComboBox.setEnabled(enable);
        statisticPanel.setEnabled(enable);
        jLabel1.setEnabled(enable);
        calculate_file.setEnabled(enable);
        colSelectComboBox.setEnabled(enable);
        chartPanel.setEnabled(enable);
        jLabel2.setEnabled(enable);
        xComboBox.setEnabled(enable);
        jLabel3.setEnabled(enable);
        yComboBox.setEnabled(enable);
        jLabel4.setEnabled(enable);
        groupComboBox.setEnabled(enable);
        drawChartButton.setEnabled(enable);
        jLabel5.setEnabled(enable);
		reload_fileButton.setEnabled(enable);
        filesaveButton.setEnabled(enable);
		
	}
	
	private void setComboBoxModel(String[] items){
		if(items==null)
			items = new String[]{"1,2,3,4,5,6"};
	//	DefaultComboBoxModel<?> dcmb = new DefaultComboBoxModel(items);
		
		colSelectComboBox.setModel(new DefaultComboBoxModel(items));
		xComboBox.setModel(new DefaultComboBoxModel(items));
		yComboBox.setModel(new DefaultComboBoxModel(items));
		zComboBox.setModel(new DefaultComboBoxModel(items));
		groupComboBox.setModel(new DefaultComboBoxModel(items));


	}
}
