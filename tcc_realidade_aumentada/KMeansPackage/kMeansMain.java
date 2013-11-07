package KMeansPackage;
import java.util.ArrayList;

import java.util.Random;



public class kMeansMain {

	/**

	 * @param args

	 */

	public static void main(String[] args) {

		ArrayList<Ponto> p = new ArrayList<Ponto>();
		p.add(new Ponto(10,10));
		p.add(new Ponto(10,20));
		p.add(new Ponto(20,10));
		p.add(new Ponto(20,20));
		p.add(new Ponto(60,70));
		p.add(new Ponto(60,60));
		p.add(new Ponto(70,60));
		p.add(new Ponto(70,70));
		kMeans km = new kMeans(p);
		km.ExecutaKMeans(8);
		System.out.println("\nok!");
		for(int i=0; i<p.size(); i++)
			System.out.println(p.get(i));
	}
}

