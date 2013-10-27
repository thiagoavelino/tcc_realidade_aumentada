package LinearRegression;

import java.util.ArrayList;

public class LinearRegression {
	
	/**
	 * Retorna os parametros da regressao linear. Y=a*X+b
	 * @param xp Array com os valores de X
	 * @param yp Array com os valores de Y
	 * @return Array Z com os parametros sendo Z[0]=R, Z[1]=stderr(a), Z[2]=stderr(b), Z[3]=a, Z[4]=b
	 * @throws Exception
	 */
	public double[] getRegression(double[] xp, double[] yp) throws Exception {
		int n=xp.length;
		double xbar,ybar;
		double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
		double[] ret=new double[5];
		
		if(xp.length!=yp.length){
			throw new Exception("O tamanho dos arrays deve ser o mesmo");
		}
		// first pass: read in data, compute xbar and ybar
		double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;

		for(int i=0; i<n; i++){
			sumx=sumx+xp[i];
			sumx2+=xp[i]*xp[i];
			sumy=sumy+yp[i];
		}

		xbar = sumx / n;
		ybar = sumy / n;

		// second pass: compute summary statistics
		for (int i = 0; i < n; i++) {
			xxbar += (xp[i] - xbar) * (xp[i] - xbar);
			yybar += (yp[i] - ybar) * (yp[i] - ybar);
			xybar += (xp[i] - xbar) * (yp[i] - ybar);
		}
		double beta1 = xybar / xxbar;
		double beta0 = ybar - beta1 * xbar;

		// print results
		//System.out.println("y   = " + beta1 + "*x + " + beta0);

		// analyze results
		int df = n - 2;
		double rss = 0.0;      // residual sum of squares
		double ssr = 0.0;      // regression sum of squares
		for (int i = 0; i < n; i++) {
			double fit = beta1*xp[i] + beta0;
			rss += (fit - yp[i]) * (fit - yp[i]);
			ssr += (fit - ybar) * (fit - ybar);
		}
		double R2    = ssr / yybar;
		double svar  = rss / df;
		double svar1 = svar / xxbar;
		double svar0 = svar/n + xbar*xbar*svar1;
//		System.out.println("R^2                 = " + R2);
//		System.out.println("std error of beta_1 = " + Math.sqrt(svar1));
//		System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
//		svar0 = svar * sumx2 / (n * xxbar);
//		System.out.println("std error of beta_0 = " + Math.sqrt(svar0));
//
//		System.out.println("SSTO = " + yybar);
//		System.out.println("SSE  = " + rss);
//		System.out.println("SSR  = " + ssr);
		
		ret[0]=R2;
		ret[1]=Math.sqrt(svar1);
		ret[2]=Math.sqrt(svar0);
		ret[3]=beta1;
		ret[4]=beta0;
		
		return ret;
	}


}
