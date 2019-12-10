package com.chase.WordCount;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
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


public class Word_count extends ApplicationFrame {
	
	private static final long serialVersionUID = 1L;

	public Word_count(String title, CategoryDataset DataSet) {
		super(title);
		JFreeChart barChart = ChartFactory.createBarChart(title, "Word(s)", "Appearance Count", DataSet, PlotOrientation.VERTICAL, false, true, false);
		
		CategoryAxis axis = barChart.getCategoryPlot().getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
		
		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1280 , 720)); 
		setContentPane(chartPanel);
	}
	
	private static CategoryDataset createDST(Map<String, Integer> datDict, String rowKey) {
		DefaultCategoryDataset DST = new DefaultCategoryDataset();
		for (String word : datDict.keySet()) {
			if (!word.equals("")) {
				DST.addValue(datDict.get(word), rowKey, word);
			}
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
				strbfr.append((char)ch);
			}
			fileReader.close();
		} catch (IOException e) {
			System.out.println("Caught exception: " + e);
			return null;
		}
		return strbfr.toString();
	}
	
	private static Map<String, Integer> sortMap(Map<String, Integer> inputMap) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(inputMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>> () {
			public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
				return (m2.getValue()).compareTo(m1.getValue());
			}
		});
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> kvPair : list) {
			sortedMap.put(kvPair.getKey(), kvPair.getValue());
		}
		return sortedMap;
	}
	
	public static void main(String[] args) {
		String[] text = readText().split("[\\s+,.]");
		Map<String, Integer> dict = new HashMap<String, Integer>();
		for (String word : text) {
			if(dict.get(word) == null) {
				dict.put(word, 1);
			}
			else {
				dict.put(word, dict.get(word) + 1);
			}
		}
		dict = sortMap(dict);
		System.out.println(dict);
		Word_count chart = new Word_count("Word Appearances", createDST(dict, "Number of Appearances"));
		chart.pack();
		chart.setVisible(true);
	}
}
