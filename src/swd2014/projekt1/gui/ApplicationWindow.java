package swd2014.projekt1.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import javax.swing.filechooser.FileFilter;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.jfree.data.xy.XYSeriesCollection;

import swd2014.projekt1.csv.CsvFileReader;
import swd2014.projekt1.csv.CsvReadWriteSettings;
import swd2014.projekt1.models.DataAndClass;
import swd2014.projekt1.models.Matrix;
import swd2014.projekt1.models.Neighborns;
import swd2014.projekt1.models.Point;
import swd2014.projekt1.models.PointClassModel;
import swd2014.projekt1.utils.Converts;
import swd2014.projekt1.utils.DataPrinting;
import swd2014.projekt1.utils.DataSplit;
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
	private String[] delimeter_array = new String[]{"NaN", "\t", " ", ";", ","};
	private javax.swing.JButton calculate_file;
	private javax.swing.JScrollPane jScrollPane1;
	public static javax.swing.JTextArea consolTextArea;
	private javax.swing.JButton reload_fileButton, open_fileButton, drawChartButton, filesaveButton, standardBtn, discretizationBtn;
    private static javax.swing.JComboBox colSelectComboBox, delimeterComboBox;

	private static javax.swing.JComboBox groupComboBox;
    private javax.swing.JCheckBox getColNamesCheckBox;
    private javax.swing.JLabel jLabel1, jLabel2, jLabel3,jLabel4,jLabel5;
    private javax.swing.JPanel jPanel1,jPanel2,statisticPanel,chartPanel;
    private static javax.swing.JComboBox xComboBox;

	private static javax.swing.JComboBox yComboBox;

	private static javax.swing.JComboBox zComboBox;
    
    private JMenu group_menu, file_menu, disp_menu, stat_menu;
    private JMenuItem pref_class_mi, save_toFile_mi, close_mi, open_file_mi, mDispl_mi, knn_mi, discretization_mi, interval_mi;

	static TableGUI tg;
    

    
    // End of variables declaration      
	private JFileChooser fc;
	private JTextField delimeterTF;
	private JLabel filenameLabel;
	private JTextField nClass;

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
		stat_menu = new JMenu("Statystyka");
		
		
	    save_toFile_mi = new JMenuItem("Zapisz do pliku");
	    save_toFile_mi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveMatrixActionPerformed(e);
				
			}
		});
		

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
		
		interval_mi = new JMenuItem("Zmień przedział");
		interval_mi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PopupWindows.intervalForm();
				
			}
		});
		
		
		group_menu.add(pref_class_mi);
		group_menu.add(knn_mi);
		group_menu.add(interval_mi);
		
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
        standardBtn = new JButton("standaryzacja");
        nClass = new JTextField();
        discretizationBtn = new JButton("dyskretyzacja");
        
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
        
        delimeterComboBox = new JComboBox();
        delimeterComboBox.setModel(new DefaultComboBoxModel(new String[] {"niestandardowy", "tabulacja", "spacja", "średnik", "przecinek"}));
        

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(delimeterComboBox, 0, 106, Short.MAX_VALUE))
        				.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
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
        							.addComponent(reload_fileButton, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addContainerGap()
        							.addComponent(lblSeparator, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(delimeterTF, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
        					.addComponent(getColNamesCheckBox)))
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
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addComponent(delimeterComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(getColNamesCheckBox))
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
        

        standardBtn.setEnabled(false);
        standardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	standardActionPerformed(evt);
            }
        });
        

        discretizationBtn.setEnabled(false);
        discretizationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	 discretizationActionPerformed(evt);
            }
        });
        
        nClass.setText("5");
        nClass.setEnabled(false);
        nClass.setColumns(10);

        javax.swing.GroupLayout gl_statisticPanel = new javax.swing.GroupLayout(statisticPanel);
        gl_statisticPanel.setHorizontalGroup(
        	gl_statisticPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_statisticPanel.createSequentialGroup()
        			.addGroup(gl_statisticPanel.createParallelGroup(Alignment.LEADING)
        				.addComponent(calculate_file, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addGroup(gl_statisticPanel.createSequentialGroup()
        					.addComponent(jLabel1)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(colSelectComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        				.addGroup(gl_statisticPanel.createSequentialGroup()
        					.addComponent(discretizationBtn, 0, 0, Short.MAX_VALUE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(nClass, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
        				.addComponent(standardBtn, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
        			.addGap(1))
        );
        gl_statisticPanel.setVerticalGroup(
        	gl_statisticPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_statisticPanel.createSequentialGroup()
        			.addGroup(gl_statisticPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel1)
        				.addComponent(colSelectComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGroup(gl_statisticPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(discretizationBtn)
        				.addComponent(nClass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(standardBtn)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(calculate_file))
        );
        statisticPanel.setLayout(gl_statisticPanel);

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
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(chartPanel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
        				.addComponent(statisticPanel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
        			.addComponent(statisticPanel, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(chartPanel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        		.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
        );
        getContentPane().setLayout(layout);

        pack();
        this.setJMenuBar(menuBar);
        
        
        kodTestowyPoZaladowaniuGUI();
        
        
        
	}
	
	private void kodTestowyPoZaladowaniuGUI(){
		
		/*
		DataAndClass dac[] = DataSplit.discretization(new double[]{1,3,2,2,6,3,7,9,4,1}, 3, true);
		
		for(DataAndClass dc : dac){
			Log(dc.toString());
		}
		
		
		double[] i_change = Utils.changeInterval(new double[]{0,10,20,30,40,50,60,70,80,90,100}, -50 , 50);
		
		for(double d : i_change){
			Log(d+"\n");
			
		}
		*/
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
				String delimeter = delimeter_array[delimeterComboBox.getSelectedIndex()];
				if(delimeter.equals("NaN"))
					delimeter = delimeterTF.getText();
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


			} else if (file.endsWith(new String(".xls"))) {
				UniParser parser = new UniParser();

				XlsParseContainer result = parser.parse(file);

				m = result.getValues();

				Log("\nWiersze: (" + m.getnRows() + ")\n");

			}
		}
		
		
		

	}

	private void calculate_fileActionPerformed(java.awt.event.ActionEvent evt) {
		
		int column_index = colSelectComboBox.getSelectedIndex();
		double[] data = Converts.convertToDouble(m.getColumn(column_index));

		Log("\nStatystyka dla kolumny " + m.getColumnNames()[column_index] + "\n");

		double mean;
		mean = StatUtils.mean(data);
		Log("Srednia: " + mean + "\n");
		// avgLbl.setText(Double.toString(mean));

		double variance;
		variance = StatUtils.variance(data);
		Log("Wariancja: " + variance + "\n");
		// varLbl.setText(Double.toString(variance));

		StandardDeviation sdev = new StandardDeviation();
		double sd = sdev.evaluate(data);
		Log("Odchylenie standardowe: " + sd+"\n");
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
		String delimeter = delimeter_array[delimeterComboBox.getSelectedIndex()];
		if(delimeter.equals("NaN"))
			delimeter = delimeterTF.getText();
		Utils.saveMatrixToFile(m, save_file, "plik z programu \n SWD2014", delimeter);
	}
	
	private void standardActionPerformed(ActionEvent evt){
		int column_index = colSelectComboBox.getSelectedIndex();
		double[] data = Converts.convertToDouble(m.getColumn(column_index));
		double[] standard = Statistic.standardScore(data);
		String[] standard_str = new String[standard.length];
		int i=0;
		for(double d : standard){
			standard_str[i] = Double.toString(d);
			i++;
		}
		
		String colName = m.getColumnNames()[column_index]+"_standaryzacja";
		m.appendColumn(standard_str, colName);
		dataSetChanged();
		
	}
	
	private void discretizationActionPerformed(ActionEvent evt){
		int column_index = colSelectComboBox.getSelectedIndex();
		double[] data = Converts.convertToDouble(m.getColumn(column_index));
		int divideBy = Integer.parseInt(nClass.getText());
		String colName = m.getColumnNames()[column_index]+"_disc";
		
		DataAndClass[] dnc = DataSplit.discretization(data, divideBy, true);
		String[] class_col = new String[dnc.length];
		
		int i=0;
		for(DataAndClass dc : dnc){
			class_col[i++] = dc.getClassName();
		}
		
		m.appendColumn(class_col, colName);
		dataSetChanged();
		
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
		
		int [] class_array = DataSplit.classNumberAttribution(group_data, 1000);
		
		double[][] x_grouped = DataSplit.splitDataByClasses(x_data, class_array);
		double[][] y_grouped = DataSplit.splitDataByClasses(y_data, class_array);
		
		
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
        standardBtn.setEnabled(enable);
        nClass.setEnabled(enable);
        discretizationBtn.setEnabled(enable);
        
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

	            	int[] kolumnaKlas = DataSplit.classNumberAttribution(m.getColumn(wybrana_kolumna), liczba_klas);
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
		    JButton assign_classes, read_fromFileBtn, leave_one_out;
		    final JTextArea classesTextArea;
			final JTextArea dataTxtArea;
		    final JComboBox cb_x_select,cb_y_select,knn_method_cb, cb_decision_class;
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
	        leave_one_out = new JButton();
	        jScrollPane2 = new javax.swing.JScrollPane();
	        classesTextArea = new javax.swing.JTextArea();
	        tv_elements = new JTextField();
	        jLabel5 = new JLabel();
	        jLabel4 = new JLabel();

	        knn_frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	        knn_method_cb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "odległość euklidesowa", "metryka manhattan", "metryka nieskończoności", "odległość Mahalanobisa" }));

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

	        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("dane wejścowe - \'x,y\'"));

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
	        leave_one_out.setText("leave one out");

	        classesTextArea.setColumns(20);
	        classesTextArea.setRows(5);
	        jScrollPane2.setViewportView(classesTextArea);

	        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
	        jPanel3.setLayout(jPanel3Layout);
	        jPanel3Layout.setHorizontalGroup(
	            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
	                .addGap(0, 0, Short.MAX_VALUE)
	                .addComponent(assign_classes)
	                .addComponent(leave_one_out))
	            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
	        );
	        jPanel3Layout.setVerticalGroup(
	            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel3Layout.createSequentialGroup()
	                .addComponent(assign_classes)
	                .addComponent(leave_one_out)
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
	        
	        leave_one_out.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
	            	int x_col = cb_x_select.getSelectedIndex();
	            	int y_col = cb_y_select.getSelectedIndex();
	            	int class_col = cb_decision_class.getSelectedIndex();
					int selected_method=knn_method_cb.getSelectedIndex();
					int n_neighbors = Integer.parseInt(tv_elements.getText());
					
					String[] classes_array = m.getColumn(class_col);
	            	double[] data_x = Converts.convertToDouble(m.getColumn(x_col));
	            	double[] data_y = Converts.convertToDouble(m.getColumn(y_col));

	            	String[] knn_classes = Statistic.leave_one_out(data_x, data_y, classes_array, selected_method, n_neighbors);
	            	
	            	int index = 0;
	            	double correct_match=0;
	            	for(String knn_c : knn_classes){
	            		String test_class = classes_array[index++];
	            		if(test_class.equals(knn_c))
	            			correct_match++;
	            		classesTextArea.append("KLASA: " +test_class+" knn: "+knn_c+"\n");
	            	}
	            	int n=classes_array.length;
	            	correct_match/=n;
	            	correct_match*=100;
	            	classesTextArea.append("n= "+n_neighbors+" l.elementów="+classes_array.length+" poprawnie odgadnięto="+correct_match+"%" );
	            	
					
				}
			});
	        
	        assign_classes.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	int x_col = cb_x_select.getSelectedIndex();
	            	int y_col = cb_y_select.getSelectedIndex();
	            	int class_col = cb_decision_class.getSelectedIndex();
	            	
					int n_neighbors = Integer.parseInt(tv_elements.getText());
					int selected_method=knn_method_cb.getSelectedIndex();
					

					String[] classes_array = m.getColumn(class_col);
	            	//Converts.convertToString(DataSplit.classNumberAttribution(m.getColumn(class_col), 1000));
	            	double[] data_x = Converts.convertToDouble(m.getColumn(x_col));
	            	double[] data_y = Converts.convertToDouble(m.getColumn(y_col));
	            	LinkedList<PointClassModel> class_data = new LinkedList<>();
	            	
	            	for(int i=0; i<data_x.length; i++){
	            		PointClassModel cm = new PointClassModel(data_x[i], data_y[i], classes_array[i]);
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
	            	
	            	LinkedList<Neighborns> neighborns= Statistic.knn_neighbors(input_data, class_data, selected_method, n_neighbors);
					
					for(Neighborns n : neighborns){
		            	String[] el_classes = new String[n.getDistances().size()];
		            	

						int i =0;
						for(PointClassModel cm : n.getDistances())
							el_classes[i++] = cm.getN_class();

						classesTextArea.append(n.getPoint().toString()+"- ");
						classesTextArea.append(Utils.mostFrequent(el_classes));
						
						classesTextArea.append("\tsąsiedzi:[  ");
						for(String s : el_classes)
							classesTextArea.append(s+", ");
						classesTextArea.append("]\n");
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
	
		public static void intervalForm(){
			final JComboBox intColSel;
		    JButton jButton1;
		    JLabel jLabel1,jLabel2,jLabel3;
		    JPanel jPanel1;
		    final JTextField jTextField1;
			final JTextField jTextField2;
		    final JFrame intervalFrame = new JFrame("Konwertuj przedział danych dla kolumny");
		    
	        jPanel1 = new javax.swing.JPanel();
	        jLabel1 = new JLabel();
	        jLabel2 = new JLabel();
	        jLabel3 = new JLabel();
	        intColSel = new javax.swing.JComboBox();
	        jTextField1 = new javax.swing.JTextField();
	        jTextField2 = new javax.swing.JTextField();
	        jButton1 = new javax.swing.JButton();

	        intervalFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

	        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Zmień przedział "));

	        jLabel1.setText("kolumna:");

	        jLabel2.setText("minimum:");

	        jLabel3.setText("maximum:");

	        intColSel.setModel(new javax.swing.DefaultComboBoxModel( m.getColumnNames()));

	        jTextField1.setText("0");

	        jTextField2.setText("100");

	        jButton1.setText("OK");
	        jButton1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedColumn = intColSel.getSelectedIndex();
					double min, max;
					min = Double.parseDouble(jTextField1.getText());
					max = Double.parseDouble(jTextField2.getText());
					double[] i_change = Utils.changeInterval(Converts.convertToDouble(m.getColumn(selectedColumn)), min , max);
					String colName = m.getColumnNames()[selectedColumn]+"_"+min+"_"+max;
					m.appendColumn(Converts.convertToString(i_change), colName);
					dataSetChanged();
					intervalFrame.dispose();
				}
			});

	        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGap(0, 0, Short.MAX_VALUE)
	                .addComponent(jButton1))
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jLabel3)
	                    .addComponent(jLabel2)
	                    .addGroup(jPanel1Layout.createSequentialGroup()
	                        .addGap(2, 2, 2)
	                        .addComponent(jLabel1)))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(intColSel, 0, 151, Short.MAX_VALUE)
	                    .addComponent(jTextField1)
	                    .addComponent(jTextField2)))
	        );
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel1)
	                    .addComponent(intColSel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel2)
	                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel3)
	                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jButton1))
	        );

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(intervalFrame.getContentPane());
	        intervalFrame.getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	        );

	        intervalFrame.pack();
	        
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                intervalFrame.setVisible(true);
	            }
	        });
		    
		}
	
	}
}
