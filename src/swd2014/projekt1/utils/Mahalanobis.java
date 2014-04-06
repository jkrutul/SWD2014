package swd2014.projekt1.utils;

import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.MahalanobisDistanceMeasure;
import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.SparseMatrix;
import org.apache.mahout.math.Vector;


public class Mahalanobis {
    
    // the covariance matrix
    private double[][] S;
    
    /*
    public static void test() {
        
        double[] x = {2000};
        double[] y = {1999};
        
        Mahalanobis mah = new Mahalanobis(x.length);
        System.out.println(mah.getSimilarity(x, y));
        
    }
    */
    
    public static void test2(){
		double[][] d = { { 1.0, 2.0 }, { 2.0, 4.0 },
				{ 3.0, 6.0 }, { 3.0, 2.0 }, { 4.0, 8.0 } };

		Vector v1 = new RandomAccessSparseVector(2);
		v1.assign(d[0]);
		Vector v2 = new RandomAccessSparseVector(2);
		v2.assign(d[1]);
		Vector v3 = new RandomAccessSparseVector(2);
		v3.assign(d[2]);
		Vector v4 = new RandomAccessSparseVector(2);
		v4.assign(d[3]);
		Vector v5 = new RandomAccessSparseVector(2);
		v5.assign(d[4]);

		Matrix matrix = new SparseMatrix(new int[]{2,2});
		matrix.assignRow(0, v1);
		matrix.assignRow(1, v2);

		EuclideanDistanceMeasure dmE = new EuclideanDistanceMeasure();
		double distance1 = dmE.distance(v2, v3);
		double distance2 = dmE.distance(v2, v4);
		System.out.println("d1=" + distance1 + ", d2=" + distance2);

		MahalanobisDistanceMeasure dmM = new MahalanobisDistanceMeasure();
		dmM.setInverseCovarianceMatrix(matrix);
		distance1 = dmM.distance(v2, v3);
		distance2 = dmM.distance(v2, v4);
		System.out.println("d1=" + distance1 + ", d2=" + distance2);
    }
  /*  
    public Mahalanobis(int dim) {
        S = new double[dim][dim];
        for(int i=0; i<dim; i++)
            for(int j=0; j<dim; j++)
                if(i == j)
                    S[i][j] = 1.0;
                else
                    S[i][j] = 0.0;
    }
    
    
    public double getDistance(double[] x, double[] y) {
        double[][] diff = new double[1][x.length];
        for(int i=0; i<x.length; i++)
            diff[0][i] = x[i] - y[i];
        double result[][] = LinearAlgebra.times( diff, LinearAlgebra.inverse(S) );
        result = LinearAlgebra.times( result, LinearAlgebra.transpose(diff) );
        return Math.sqrt( result[0][0] );
    }
    
    public double getSimilarity(double[] x, double[] y) {
        return 1.0 / (1.0 + getDistance(x, y));
    }
    
    */
     
}