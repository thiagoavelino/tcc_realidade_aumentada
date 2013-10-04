package TestPackage;

import LinearRegression.LinearRegression;

public class LinearRegressionEx01 {

	public static void main(String[] args) throws Exception {
		double resultado[];// = new double[5];
		double[] x = {1, 2, 3, 4, 5};
		double[] y = {2, 5, 3, 8, 7};


		resultado = LinearRegression.LinearRegression(x,y);

		System.out.println("y   = " + resultado[3] + "*x + " + resultado[4]);
		System.out.println("R^2                 = " + resultado[0]);
		System.out.println("std error of beta_1 = " + resultado[1]);
		System.out.println("std error of beta_0 = " + resultado[2]); 
	}
}
