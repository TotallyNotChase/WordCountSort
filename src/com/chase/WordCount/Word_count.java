package com.chase.WordCount;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

//TODO : Change it's, I'm and stuff to full form

public class Word_count extends ApplicationFrame {
	
	private static final long serialVersionUID = 1L;

	public Word_count(String title, CategoryDataset DataSet) {
		super(title);
		JFreeChart barChart = ChartFactory.createBarChart(title, "Word(s)", "Appearance Count", DataSet, PlotOrientation.VERTICAL, false, true, false);
		
		CategoryAxis axis = barChart.getCategoryPlot().getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		
		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1920 , 1080)); 
		setContentPane(chartPanel);
	}
	
	private static CategoryDataset createDST(Map<String, Integer> datDict, String rowKey) {
		DefaultCategoryDataset DST = new DefaultCategoryDataset();
		for (String word : datDict.keySet()) {
			DST.addValue(datDict.get(word), rowKey, word);
		}
		return DST;
	}
	
	private static String readText() {
		int ch;
		StringBuilder strbfr = new StringBuilder();
		FileReader fileReader = null;
		try {
			fileReader = new FileReader("input.txt");
			while ((ch = fileReader.read()) != -1) {
				if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) 
				{
					strbfr.append((char)ch);
				} else {
					strbfr.append(' ');
				}
				
			}
			fileReader.close();
		} catch (IOException e) {
			System.out.println("Caught exception: " + e);
			return null;
		}
		return strbfr.toString();
	}
	
	public static void main(String[] args) {
		String[] text = readText().split("\\s+");
		Map<String, Integer> dict = new HashMap<String, Integer>();
		for (String word : text) {
			if(dict.get(word) == null) {
				dict.put(word, 1);
			}
			else {
				dict.put(word, dict.get(word) + 1);
			}
		}
		System.out.println(dict);
		Word_count chart = new Word_count("Word Appearances", createDST(dict, "Number of Appearances"));
		chart.pack();
		chart.setVisible(true);
	}
}
