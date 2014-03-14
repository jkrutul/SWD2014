package swd2014.projekt1.gui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Charts {
	public static void chartXY(XYSeriesCollection dataset){
		JFreeChart chart = ChartFactory.createXYAreaChart("XY Chart",
				"x-axis", "y-axis", dataset, PlotOrientation.VERTICAL, true, true, false);
		displayChart(chart);

	}
	
	public static void chartScatterPlot(XYSeriesCollection dataset){
		JFreeChart chart = ChartFactory.createScatterPlot("title", "xAxisLabel", "yAxisLabel", dataset, PlotOrientation.VERTICAL, true, true, false);
		displayChart(chart);
	}
	
	public static void chartBar(CategoryDataset dataset){
		JFreeChart chart = ChartFactory.createBarChart("title", "xAxisLabel", "yAxisLabel", dataset, PlotOrientation.VERTICAL, true, true, false);
		displayChart(chart);
	}
	
	public static void displayChart(JFreeChart chart){
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
		JFrame jFrame = new JFrame();
		jFrame.setContentPane(chartPanel);
		jFrame.pack();
		jFrame.setVisible(true);
	}
	
	public static void saveChartToFile(JFreeChart chart ,File f){
		try{
			ChartUtilities.saveChartAsJPEG(f, chart, 500, 300);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	public static XYDataset createDataset(double y[]){
		XYSeries series = new XYSeries("series1");

		for(int i=0; i<y.length; i++){
			series.add(i, y[i]);
		}
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		
		return dataset;
		
	}
	
	public static XYDataset createDataset(double s1[], double s2[]){

		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("series1");
		XYSeries series2 = new XYSeries("series2");
		
		for(int i=0; i<s1.length; i++){
			series1.add(i, s1[i]);
		}
		
		for(int i=0; i<s2.length; i++){
			series2.add(i, s2[i]);
		}
		

		dataset.addSeries(series1);
		dataset.addSeries(series2);
		
		return dataset;
		
	}
	
	public static XYDataset createDataset(List<double[]> series){

		XYSeriesCollection dataset = new XYSeriesCollection();
		int n_series = series.size();
		
		for(int i = 0 ; i<n_series ; i++){
			
		}
		int index = 0;
		for(double[] s : series){
			XYSeries ser = new XYSeries("series"+index++);
			for(int i=0; i<s.length; i++){
				ser.add(i, s[i]);
			}
			dataset.addSeries(ser);			
		}
		return dataset;
		
	}
	

	
	
	public static CategoryDataset createCategoryDataset(double y[]){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int index = 0;
		for( double item : y){
			dataset.addValue(item, "Series 1",Integer.toString(index++));
		}
		
		return dataset;
	}
	
}
