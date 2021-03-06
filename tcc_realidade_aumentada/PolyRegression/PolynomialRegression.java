package PolyRegression;

import Jama.Matrix;
import Jama.QRDecomposition;

public class PolynomialRegression {
    private final int N;
    private final int degree;
    private final Matrix beta;
    private double SSE;
    private double SST;

    public PolynomialRegression(double[] x, double[] y, int degree) {
        this.degree = degree;
        N = x.length;

        // build Vandermonde matrix
        double[][] vandermonde = new double[N][degree+1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= degree; j++) {
                vandermonde[i][j] = Math.pow(x[i], j);
            }
        }
        Matrix X = new Matrix(vandermonde);

        // create matrix from vector
        Matrix Y = new Matrix(y, N);

        // find least squares solution
        QRDecomposition qr = new QRDecomposition(X);
        beta = qr.solve(Y);


        // mean of y[] values
        double sum = 0.0;
        for (int i = 0; i < N; i++)
            sum += y[i];
        double mean = sum / N;

        // total variation to be accounted for
        for (int i = 0; i < N; i++) {
            double dev = y[i] - mean;
            SST += dev*dev;
        }

        // variation not accounted for
        Matrix residuals = X.times(beta).minus(Y);
        SSE = residuals.norm2() * residuals.norm2();

    }

    public double beta(int j) {
        return beta.get(j, 0);
    }

    public int degree() {
        return degree;
    }

    public double R2() {
        return 1.0 - SSE/SST;
    }

    // predicted y value corresponding to x
    public double predict(double x) {
        // horner's method
        double y = 0.0;
        for (int j = degree; j >= 0; j--)
            y = beta(j) + (x * y);
        return y;
    }

    public String toString() {
        String s = "";
        int j = degree;

        // ignoring leading zero coefficients
        while (Math.abs(beta(j)) < 1E-5)
            j--;

        // create remaining terms
        for (j = j; j >= 0; j--) {
            if      (j == 0) s += String.format("%.2f ", beta(j));
            else if (j == 1) s += String.format("%.2f X + ", beta(j));
            else             s += String.format("%.2f X^%d + ", beta(j), j);
        }
        return "Y = " + s ;
    }
    
    public double calculatePoly(double x){
    	double result = 0;
    	int j = degree;
		
		while (Math.abs(beta(j)) < 1E-5)
            j--;

        for (j = j; j >= 0; j--) {
            if      (j == 0) result += beta(j);
            else if (j == 1) result += beta(j)*x;
            else             result += Math.pow(x, j) * beta(j);
        }
    	return result;
    }

    /*public static void main(String[] args) {
        double[] x = { 1, 2, 3, 4};
        double[] y = { 1, 8, 27, 64};
        PolynomialRegression regression = new PolynomialRegression(x, y, 3);
        System.out.println(regression);
        System.out.println("y= "+ Double.toString(regression.calculatePoly(2)));
    }*/
}
