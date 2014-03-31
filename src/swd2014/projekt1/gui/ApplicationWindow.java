package swd2014.projekt1.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.stat.StatUtils;
import org.jfree.data.xy.XYSeriesCollection;

import swd2014.projekt1.csv.CsvFileReader;
import swd2014.projekt1.csv.CsvReadWriteSettings;
import swd2014.projekt1.models.ClassModel;
import swd2014.projekt1.models.Matrix;
import swd2014.projekt1.models.Neighborns;
import swd2014.projekt1.models.Point;
import swd2014.projekt1.utils.Converts;
import swd2014.projekt1.utils.DataPrinting;
import swd2014.projekt1.utils.Statistic;
import swd2014.projekt1.utils.Utils;
import swd2014.projekt1.xls.UniParser;
import swd2014.projekt1.xls.XlsParseContainer;

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
	public static javax.swing.JTextArea consolTextArea;
	private javax.swing.JButton reload_fileButton, open_fileButton, drawChartButton, filesaveButton;
    private static javax.swing.JComboBox colSelectComboBox;

	private static javax.swing.JComboBox groupComboBox;
    private javax.swing.JCheckBox getColNamesCheckBox;
    private javax.swing.JLabel jLabel1, jLabel2, jLabel3,jLabel4,jLabel5;
    private javax.swing.JPanel jPanel1,jPanel2,statisticPanel,chartPanel;
    private static javax.swing.JComboBox xComboBox;

	private static javax.swing.JComboBox yComboBox;

	private static javax.swing.JComboBox zComboBox;
    
    private JMenu group_menu, file_menu, disp_menu;
    private JMenuItem pref_class_mi, save_toFile_mi, close_mi, open_file_mi, mDispl_mi, knn_mi;
    
	static TableGUI tg;
    

    
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
		/* MENU */
		
	    JMenuBar menuBar = new JMenuBar();
	    
	    group_menu = new JMenu("Grupowanie");
		file_menu = new JMenu("Plik");	
		disp_menu = new JMenu("Wyświetl");
	    
	    save_toFile_mi = new JMenuItem("Zapisz do pliku");
	    save_toFile_mi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveMatrixActionPerformed(e);
				
			}
		});
	    
		//group_menu.setMnemonic(KeyEvent.VK_G);		
		pref_class_mi = new JMenuItem("Preferowanie najliczniejszych klas");
		pref_class_mi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupWindows.displayClassPref();
				
			}
		});
		
		
		knn_mi = new JMenuItem("Klasyfikacja metodą knn");
		knn_mi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				PopupWindows.displayKnn();
			}
		});
			
		close_mi = new JMenuItem("Zamknij");
		close_mi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		open_file_mi = new JMenuItem("Otwórz plik");
		open_file_mi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 open_fileButtonActionPerformed(e);
			}
		});
		

		mDispl_mi = new JMenuItem("Wypisz zawartość macierzy");
		mDispl_mi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DataPrinting.printMatrix(m, consolTextArea);
			}
		});
		
		
		
		group_menu.add(pref_class_mi);
		group_menu.add(knn_mi);
		
		file_menu.add(open_file_mi);
		file_menu.add(save_toFile_mi);
		file_menu.addSeparator();
		file_menu.add(close_mi);

		
		disp_menu.add(mDispl_mi);
		
		menuBar.add(file_menu);
		menuBar.add(group_menu);
		menuBar.add(disp_menu);

		

		
		
		
		
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
        
        filesaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveMatrixActionPerformed(e);
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
        
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Preferowanie najliczniejszych klass", TitledBorder.LEADING, TitledBorder.TOP, null, null));

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
        				.addComponent(statisticPanel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
        				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
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
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
        			.addContainerGap())
        		.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
        );
        getContentPane().setLayout(layout);

        pack();
        this.setJMenuBar(menuBar);
        
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
				m.setColumnNames(columNamesArray);
				setComboBoxModel(columNamesArray);
				
				tg = new TableGUI(columNamesArray, m.data);
				
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
		
		Point p = new Point(23,43);
		

		
		
	}
	
	private void drawChartActionPerformed(java.awt.event.ActionEvent evt){
		int x_col = xComboBox.getSelectedIndex();
		int y_col = yComboBox.getSelectedIndex();
		int g_col = groupComboBox.getSelectedIndex();
		
		
		drawChart(x_col, y_col, g_col);
		/*
		double[] xs = Converts.convertToDouble(m.getColumn(x_col));
		double[] ys = Converts.convertToDouble(m.getColumn(y_col));
		Point[] points = Utils.createPoints(xs, ys);
		
		Point from, to;
		from =  new Point(1, 1);
		to = new Point(10,20);
		
		double euclideandist = Statistic.euclideanDistance(from, to);
		double manhattandist = Statistic.manhattanDistance(from, to);
		double chebyshevdist = Statistic.chebyshevDistance(from, to);
		
		Log("Distance from point:"+from+ " to point:" +to+"\n");
		Log("euclidean: "+ euclideandist+"\n");
		Log("manhattan: "+ manhattandist+ "\n");
		Log("chebyshew: "+ chebyshevdist+ "\n" );
		*/
	}
	
	private void saveMatrixActionPerformed(ActionEvent evt){
	    File path_to_project_dir = new File(System.getProperty("user.dir"));
		String filename = JOptionPane.showInputDialog(null, "Podaj nazwę pliku", "Zapis macierz do pliku",1);
		File save_file = new File(path_to_project_dir.getAbsolutePath()+File.separator+filename+".csv");
		Utils.saveMatrixToFile(m, save_file, "plik z programu \n SWD2014");
	}
	
	//FUNKCJE WYWOŁYWANE PRZEZ BUTTONY -- KONIEC



	public static void drawChartForColumns(int[] column_indexes) {

		LinkedList<double[]> series = new LinkedList<>();
		for (int c_index : column_indexes) {
			double[] s = Converts.convertToDouble(m.getColumn(c_index));
			series.add(s);
		}
		Charts.chartScatterPlot((XYSeriesCollection) Charts.createDataset(series), 		"x", "y", "xy");
		
	}

	public static void drawChart(int x_column, int y_column, int groupBy ){
		double[] x_data =  Converts.convertToDouble(m.getColumn(x_column));
		double[] y_data =  Converts.convertToDouble(m.getColumn(y_column));
		String[] group_data =  m.getColumn(groupBy);
		
		int [] class_array = Utils.classAttribution(group_data, 5);
		
		double[][] x_grouped = Utils.splitDataByClasses(x_data, class_array);
		double[][] y_grouped = Utils.splitDataByClasses(y_data, class_array);
		
		
		String[] col_names = m.getColumnNames();
		String x_title ="x", y_title="y", title="xy";
		
		if(col_names.length>0){
			x_title = col_names[x_column];
			y_title = col_names[y_column];
			
		}
		
		Charts.chartScatterPlot((XYSeriesCollection) Charts.createDataset(x_grouped, y_grouped), x_title, y_title, title);
		
		
	}
	
	public static void drawChartForColumns(int[] column_indexes, double[][] data) {

		LinkedList<double[]> series = new LinkedList<>();
		for (int c_index : column_indexes) {
			double[] s = data[c_index];
			series.add(s);
		}
		Charts.chartScatterPlot((XYSeriesCollection) Charts.createDataset(series),"x", "y", "xy");
	}

	public static void Log(String s) {
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
        
        //    private JMenu group_menu, file_menu, disp_menu;
       // private JMenuItem pref_class_mi, close_mi, open_file_mi, mDispl_mi;
        
        disp_menu.setEnabled(enable);
        group_menu.setEnabled(enable);
        save_toFile_mi.setEnabled(enable);
        
        
	}
	
	private  static void setComboBoxModel(String[] items){
		if(items==null)
			items = new String[]{"1,2,3,4,5,6"};
	//	DefaultComboBoxModel<?> dcmb = new DefaultComboBoxModel(items);
		
		colSelectComboBox.setModel(new DefaultComboBoxModel(items));
		xComboBox.setModel(new DefaultComboBoxModel(items));
		yComboBox.setModel(new DefaultComboBoxModel(items));
		zComboBox.setModel(new DefaultComboBoxModel(items));
		groupComboBox.setModel(new DefaultComboBoxModel(items));
	}
	
	public static void dataSetChanged(){
		setComboBoxModel(m.getColumnNames());
		if(tg!=null)
			tg.dispose();
		tg = new TableGUI(m.getColumnNames(), m.data);
		
	}
	
	private static class PopupWindows{
		
		public static void displayClassPref(){
			final JFrame classPrefFrame = new JFrame("Preferowanie najliczniejszych klass");
		    javax.swing.JButton cancelBtn;
		    final javax.swing.JComboBox column_selectCbx;
		    javax.swing.JButton confirmBtn;
		    final javax.swing.JTextField how_many_classTfld;
		    javax.swing.JLabel jLabel1;
		    javax.swing.JLabel jLabel2;
		    javax.swing.JPanel jPanel1;
		    
	        jPanel1 = new javax.swing.JPanel();
	        jLabel1 = new javax.swing.JLabel();
	        column_selectCbx = new javax.swing.JComboBox();
	        jLabel2 = new javax.swing.JLabel();
	        how_many_classTfld = new javax.swing.JTextField();
	        confirmBtn = new javax.swing.JButton();
	        cancelBtn = new javax.swing.JButton();

	        classPrefFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	        jLabel1.setText("kolumna:");
	        column_selectCbx.setModel(new javax.swing.DefaultComboBoxModel( m.getColumnNames()));

	        jLabel2.setText("liczba klas:");

	        how_many_classTfld.setText("3");
	       

	        confirmBtn.setText("ok");
	        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	int liczba_klas = Integer.parseInt(how_many_classTfld.getText());
	            	int wybrana_kolumna = column_selectCbx.getSelectedIndex();
	            	String newColName = (String) column_selectCbx.getSelectedItem() + "_classes";
	            	
	            	//m.getColumn(wybrana_kolumna);

	            	int[] kolumnaKlas = Utils.classAttribution(m.getColumn(wybrana_kolumna), liczba_klas);
	            	m.appendColumn(Converts.convertToString(kolumnaKlas), newColName);
	            	dataSetChanged();
	            	
	            	
	            	DataPrinting.printMatrix(m, consolTextArea);
	                //confirmBtnActionPerformed(evt);
	            	classPrefFrame.dispose();
	            }
	        });

	        cancelBtn.setText("Anuluj");
	        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                classPrefFrame.dispose();
	            }
	        });

	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                    .addComponent(cancelBtn)
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addGroup(jPanel1Layout.createSequentialGroup()
	                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                                    .addGroup(jPanel1Layout.createSequentialGroup()
	                                        .addGap(4, 4, 4)
	                                        .addComponent(confirmBtn))
	                                    .addGroup(jPanel1Layout.createSequentialGroup()
	                                        .addContainerGap()
	                                        .addComponent(jLabel1)))
	                                .addGap(10, 10, 10))
	                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                                .addComponent(jLabel2)
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
	                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addComponent(how_many_classTfld, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(column_selectCbx, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
	                .addGap(0, 11, Short.MAX_VALUE))
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(column_selectCbx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel2)
	                    .addComponent(how_many_classTfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(cancelBtn)
	                    .addComponent(confirmBtn))
	                .addContainerGap())
	        );

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(classPrefFrame.getContentPane());
	        classPrefFrame.getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	        );

	        classPrefFrame.pack();
	        
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                classPrefFrame.setVisible(true);
	            }
	        });
		    
		    
		}
		
	
		
		public static void displayKnn(){
		    JButton assign_classes, read_fromFileBtn;
		    final JTextArea classesTextArea;
			final JTextArea dataTxtArea;
		    final JComboBox cb_x_select;
			final JComboBox cb_y_select;
			final JComboBox knn_method_cb;
			final JComboBox cb_decision_class;
		    JLabel jLabel1, jLabel2,jLabel3, jLabel5, jLabel4;
		    JPanel jPanel1,jPanel2,jPanel3;
		    JScrollPane jScrollPane1,jScrollPane2;
		    final JTextField tv_elements;

			final JFrame knn_frame = new JFrame("KNN");
			
			
			jPanel1 = new javax.swing.JPanel();
	        knn_method_cb = new javax.swing.JComboBox();
	        jLabel1 = new javax.swing.JLabel();
	        jLabel2 = new javax.swing.JLabel();
	        cb_x_select = new javax.swing.JComboBox();
	        jLabel3 = new javax.swing.JLabel();
	        cb_y_select = new javax.swing.JComboBox();
	        cb_decision_class = new JComboBox();
	        jPanel2 = new javax.swing.JPanel();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        dataTxtArea = new javax.swing.JTextArea();
	        read_fromFileBtn = new javax.swing.JButton();
	        jPanel3 = new javax.swing.JPanel();
	        assign_classes = new javax.swing.JButton();
	        jScrollPane2 = new javax.swing.JScrollPane();
	        classesTextArea = new javax.swing.JTextArea();
	        tv_elements = new JTextField();
	        jLabel5 = new JLabel();
	        jLabel4 = new JLabel();

	        knn_frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	        knn_method_cb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "odległość euklidesowa", "metryka manhattan", "metryka nieskończoności" }));

	        jLabel1.setText("metoda klasyfikacji:");

	        jLabel2.setText("oś X:");

	        cb_x_select.setModel(new javax.swing.DefaultComboBoxModel(m.getColumnNames()));

	        jLabel3.setText("oś Y:");

	        cb_y_select.setModel(new javax.swing.DefaultComboBoxModel(m.getColumnNames()));

	        jLabel5.setText("klasa decyzyjna:");

	        tv_elements.setText("3");

	        jLabel4.setText("liczba sąsiadów:");

	        cb_decision_class.setModel(new javax.swing.DefaultComboBoxModel(m.getColumnNames()));

	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                        .addComponent(jLabel1)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(knn_method_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addComponent(jLabel2)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(cb_x_select, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                        .addComponent(jLabel3)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(cb_y_select, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addComponent(jLabel5)
	                            .addComponent(jLabel4))
	                        .addGap(18, 18, 18)
	                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addGroup(jPanel1Layout.createSequentialGroup()
	                                .addComponent(tv_elements, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                .addGap(0, 0, Short.MAX_VALUE))
	                            .addComponent(cb_decision_class, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
	                .addContainerGap())
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(knn_method_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel1))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel2)
	                    .addComponent(cb_x_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(cb_y_select, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel3))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel5)
	                    .addComponent(cb_decision_class, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(tv_elements, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel4)))
	        );

	        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("dane wejścowe"));

	        dataTxtArea.setColumns(20);
	        dataTxtArea.setRows(5);
	        jScrollPane1.setViewportView(dataTxtArea);

	        read_fromFileBtn.setText("wczytaj z pliku");

	        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
	        jPanel2.setLayout(jPanel2Layout);
	        jPanel2Layout.setHorizontalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(jScrollPane1)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addGap(0, 0, Short.MAX_VALUE)
	                .addComponent(read_fromFileBtn))
	        );
	        jPanel2Layout.setVerticalGroup(
	            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel2Layout.createSequentialGroup()
	                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(read_fromFileBtn))
	        );

	        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("klasyfikacja"));

	        assign_classes.setText("klasyfikuj");

	        classesTextArea.setColumns(20);
	        classesTextArea.setRows(5);
	        jScrollPane2.setViewportView(classesTextArea);

	        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
	        jPanel3.setLayout(jPanel3Layout);
	        jPanel3Layout.setHorizontalGroup(
	            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
	                .addGap(0, 0, Short.MAX_VALUE)
	                .addComponent(assign_classes))
	            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
	        );
	        jPanel3Layout.setVerticalGroup(
	            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel3Layout.createSequentialGroup()
	                .addComponent(assign_classes)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jScrollPane2))
	        );

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(knn_frame.getContentPane());
	        knn_frame.getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, Short.MAX_VALUE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	        );
	        
	        assign_classes.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	int x_col = cb_x_select.getSelectedIndex();
	            	int y_col = cb_y_select.getSelectedIndex();
	            	int class_col = cb_decision_class.getSelectedIndex();
	            	
					int n_neighbors = Integer.parseInt(tv_elements.getText());
	            	
	            	int[] classes_array = Utils.classAttribution(m.getColumn(class_col), 1000);
	            	double[] data_x = Converts.convertToDouble(m.getColumn(x_col));
	            	double[] data_y = Converts.convertToDouble(m.getColumn(y_col));
	            	LinkedList<ClassModel> class_data = new LinkedList<>();
	            	
	            	for(int i=0; i<data_x.length; i++){
	            		ClassModel cm = new ClassModel(data_x[i], data_y[i], classes_array[i]);
	            		class_data.add(cm);
	            	}
	            	
	            	String raw_input = dataTxtArea.getText();
	            	raw_input.replaceAll("\\s", "");
	            	String[] raw_points = raw_input.split("\n");
	            	
	            	LinkedList<Point> input_data= new LinkedList<>();
	            	
	            	for(String raw_point : raw_points){
	            		String[] xy = raw_point.split(",");
	            		Point p = new Point(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
	            		input_data.add(p);
	            	}
	            	
	            	LinkedList<ClassModel> distance = new LinkedList<>();
	            	
	            	int knn_method = knn_method_cb.getSelectedIndex();
					LinkedList<Neighborns> neighborns = new LinkedList<>();
	            	switch (knn_method) {
					case 0:// "odległość euklidesowa"
						for(Point from : input_data){
							LinkedList<ClassModel> tempDist = new LinkedList<>();
							
							for(ClassModel to : class_data){
								double dist = Statistic.euclideanDistance(from, to.getPoint());
								ClassModel cm = new ClassModel(to.getN_class(), dist);
								cm.setPoint(from);
								tempDist.add(cm);
							}
							
							Collections.sort(tempDist);
							ListIterator iter = tempDist.listIterator();
							
							LinkedList<ClassModel> closest_neighborns = new LinkedList<>();
							
							for(int i = 0; i<n_neighbors; i++){
								if(iter.hasNext())
									closest_neighborns.add((ClassModel) iter.next());
							}
							
							Neighborns nghbrns = new Neighborns(from, closest_neighborns);
							neighborns.add(nghbrns);
						}
						
						
						
						for(Neighborns n : neighborns){
							classesTextArea.append(n.getPoint().toString()+" Klasy: ");
							for(ClassModel cm : n.getDistances())
								classesTextArea.append(cm.getN_class()+", ");
							classesTextArea.append("\n");
						}
						
						//TODO dodać wybór najliczniejszej klasy
						
						/*
						Collections.sort(distance);
						
						LinkedList<ClassModel> closest_neighborns = new LinkedList<>();
						
						ListIterator iter =  distance.listIterator();
						
						for(int i=0; i<n_neighbors; i++){
							closest_neighborns.add((ClassModel) iter.previous());
						}
						
						for(ClassModel cm : closest_neighborns){
							Log(cm.toString());
						}
						*/
						
						
						break;
						
					case 1://"metryka manhattan"
						for(Point from : input_data){
							LinkedList<ClassModel> tempDist = new LinkedList<>();
							
							for(ClassModel to : class_data){
								double dist = Statistic.manhattanDistance(from, to.getPoint());
								ClassModel cm = new ClassModel(to.getN_class(), dist);
								cm.setPoint(from);
								tempDist.add(cm);
							}
							
							Collections.sort(tempDist);
							ListIterator iter = tempDist.listIterator();
							
							LinkedList<ClassModel> closest_neighborns = new LinkedList<>();
							
							for(int i = 0; i<n_neighbors; i++){
								if(iter.hasNext())
									closest_neighborns.add((ClassModel) iter.next());
							}
							
							Neighborns nghbrns = new Neighborns(from, closest_neighborns);
							neighborns.add(nghbrns);
						}
						
						
						
						for(Neighborns n : neighborns){
							classesTextArea.append(n.getPoint().toString()+" Klasy: ");
							for(ClassModel cm : n.getDistances())
								classesTextArea.append(cm.getN_class()+", ");
							classesTextArea.append("\n");
						}
						
						break;
						
					case 2://"metryka nieskończoność"
						for(Point from : input_data){
							LinkedList<ClassModel> tempDist = new LinkedList<>();
							
							for(ClassModel to : class_data){
								double dist = Statistic.chebyshevDistance(from, to.getPoint());
								ClassModel cm = new ClassModel(to.getN_class(), dist);
								cm.setPoint(from);
								tempDist.add(cm);
							}
							
							Collections.sort(tempDist);
							ListIterator iter = tempDist.listIterator();
							
							LinkedList<ClassModel> closest_neighborns = new LinkedList<>();
							
							for(int i = 0; i<n_neighbors; i++){
								if(iter.hasNext())
									closest_neighborns.add((ClassModel) iter.next());
							}
							
							Neighborns nghbrns = new Neighborns(from, closest_neighborns);
							neighborns.add(nghbrns);
						}
						
						
						
						for(Neighborns n : neighborns){
							classesTextArea.append(n.getPoint().toString()+" Klasy: ");
							for(ClassModel cm : n.getDistances())
								classesTextArea.append(cm.getN_class()+", ");
							classesTextArea.append("\n");
						}
						
						break;

					default:
						break;
					}
	            	
	            	
	         
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            }
	        });
	        knn_frame.pack();
	        
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                knn_frame.setVisible(true);
	            }
	        });
			
			
		}
	}
	
}
