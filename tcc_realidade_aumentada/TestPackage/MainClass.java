package TestPackage;
import java.util.ArrayList;

import KMeansPackage.Data;
import KMeansPackage.KMeans;


public class MainClass {

	public static void main(String[] args)
	{
		//private static final double SAMPLES[][] = new double[][] {{1.0, 1.0}, {3.0, 4.0}, {5.0, 7.0}, {3.5, 5.0}, {4.5, 5.0},  {3.5, 4.5}};
		ArrayList<Data> data = new ArrayList<Data>();
		data.add(new Data(1, 1));
		data.add(new Data(1.5,2));
		data.add(new Data(3, 4));
		data.add(new Data(5, 7));
		data.add(new Data(3.5, 5));
		data.add(new Data(4.5, 5));
		data.add(new Data(3.5, 4.5));

		KMeans km = new KMeans(data,2);

		// Print out clustering results.
		for(int i = 0; i < km.getNumberOfClusters(); i++)
		{
			System.out.println("Cluster " + i + " includes:");
			for(int j = 0; j < km.getNumerOfData(); j++)
			{
				if(km.getProcessedData().get(j).cluster() == i){
					System.out.println(km.getProcessedData().get(j));
				}
			} // j
			System.out.println();
		} // i

		// Print out centroid results.
		System.out.println("Centroids finalized at:");
		for(int i = 0; i < km.getNumberOfClusters(); i++)
		{
			System.out.println(km.getCentroids().get(i));
		}
		System.out.print("\n");
		return;
	}
}
