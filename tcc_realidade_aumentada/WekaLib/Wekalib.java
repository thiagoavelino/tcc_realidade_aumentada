package WekaLib;

import weka.classifiers.Evaluation;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Cobweb;
import weka.clusterers.DensityBasedClusterer;
import weka.clusterers.FarthestFirst;
import weka.clusterers.HierarchicalClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Tag;

import java.util.ArrayList;

import Model.Pixel;



public class Wekalib {
	
	private Instances data;
	private int numberOfClusters;
	private String linkType;
	private ArrayList<Pixel> arrayPixels;
	
	
	public Wekalib(ArrayList<Pixel> arrayPixels, int numberOfClusters) throws Exception{
		this.numberOfClusters = numberOfClusters;
		initializeAlgorithm(arrayPixels);		
	}
	
	public Wekalib(ArrayList<Pixel> arrayPixels) throws Exception{
		initializeAlgorithm(arrayPixels);
		
	}

	public void initializeAlgorithm(ArrayList<Pixel> arrayPixels) {
		this.arrayPixels = arrayPixels;
		ArrayList<Attribute> atts = new ArrayList<Attribute>(2);
		atts.add(new Attribute("X"));
		atts.add(new Attribute("Y"));
		FastVector fvWekaAttributes = new FastVector(2);
		 fvWekaAttributes.addElement(atts.get(0));    
		 fvWekaAttributes.addElement(atts.get(1));
		 data = new Instances("TestInstances",fvWekaAttributes,0);
		
		for(int i=0; i<arrayPixels.size(); i++){
			Instance instance = new Instance(2);
			instance.setValue((Attribute)fvWekaAttributes.elementAt(0), (double)arrayPixels.get(i).getX());
			instance.setValue((Attribute)fvWekaAttributes.elementAt(1), (double)arrayPixels.get(i).getY());
			data.add(instance);
		}
	}
	

	public ArrayList<Data> calculateKmeans() throws Exception {
		SimpleKMeans simpleKMeans = new SimpleKMeans();
		simpleKMeans.setNumClusters(numberOfClusters);
		simpleKMeans.buildClusterer(data);
		
		ClusterEvaluation eval = new ClusterEvaluation();
		eval.setClusterer(simpleKMeans);
		eval.evaluateClusterer(data);
		double[] clusters = eval.getClusterAssignments();
		ArrayList<Data> dataClusters = new ArrayList<Data>();
		for(int i=0;i<data.numInstances();i++){
			int x= arrayPixels.get(i).getX();
			int y= arrayPixels.get(i).getY();
			int cluster = (int) Math.round(clusters[i]);
			Data data = new Data(x, y);
			data.cluster(cluster);
			dataClusters.add(data);
		}
		return dataClusters;
	}
	
	public ArrayList<Data> calculateFarthestFirst() throws Exception {
		FarthestFirst farthestFirst = new FarthestFirst();
		farthestFirst.setNumClusters(numberOfClusters);
		farthestFirst.buildClusterer(data);
		
		ClusterEvaluation eval = new ClusterEvaluation();
		eval.setClusterer(farthestFirst);
		eval.evaluateClusterer(data);
		double[] clusters = eval.getClusterAssignments();
		ArrayList<Data> dataClusters = new ArrayList<Data>();
		for(int i=0;i<data.numInstances();i++){
			int x= arrayPixels.get(i).getX();
			int y= arrayPixels.get(i).getY();
			int cluster = (int) Math.round(clusters[i]);
			Data data = new Data(x, y);
			data.cluster(cluster);
			dataClusters.add(data);
		}
		return dataClusters;
	}
	
	public ArrayList<Data> calculateHierarchical() throws Exception {
		HierarchicalClusterer hierarchicalClusterer = new HierarchicalClusterer();
		String[] options = hierarchicalClusterer.getOptions();
		options[3] = linkType;
		hierarchicalClusterer.setOptions(options);
		hierarchicalClusterer.setNumClusters(numberOfClusters);
		hierarchicalClusterer.buildClusterer(data);
		
		ClusterEvaluation eval = new ClusterEvaluation();
		eval.setClusterer(hierarchicalClusterer);
		eval.evaluateClusterer(data);
		double[] clusters = eval.getClusterAssignments();
		ArrayList<Data> dataClusters = new ArrayList<Data>();
		for(int i=0;i<data.numInstances();i++){
			int x= arrayPixels.get(i).getX();
			int y= arrayPixels.get(i).getY();
			int cluster = (int) Math.round(clusters[i]);
			Data data = new Data(x, y);
			data.cluster(cluster);
			dataClusters.add(data);
		}
		return dataClusters;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		ArrayList<Pixel> arrayPixel = new ArrayList<Pixel>();
		arrayPixel.add(new Pixel(1,2));
		arrayPixel.add(new Pixel(2,4));
		arrayPixel.add(new Pixel(7,20));
		arrayPixel.add(new Pixel(7,19));
		arrayPixel.add(new Pixel(7,16));
		arrayPixel.add(new Pixel(7,10));
		arrayPixel.add(new Pixel(7,14));
		arrayPixel.add(new Pixel(7,11));
		Wekalib wekaLib= new Wekalib(arrayPixel,4);
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

}
