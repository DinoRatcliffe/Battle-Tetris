package utilities;

import java.util.Arrays;

/**
 * static functions for performing actions on 2d int arrays
 * 
 * @author Dino Ratcliffe
 *
 */
public class MatrixUtilities {
	
	/**
	 * Rotates a 2d int array
	 * 
	 * @param matrix int[][] to rotate
	 * @return a new rotated matrix
	 */
	public static int[][] rotate2dMatrix(int[][] matrix){
		int[][] newMatrix = new int[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix.length; i++){
			for(int j = 0; j<matrix[0].length; j++){
				newMatrix[j][i] = matrix[matrix.length - 1 - i][j];
			}
		}
		return newMatrix;
	}
	
	/**
	 * performs a deep copy of an 2d int array
	 * 
	 * @param a 2d array to be copied
	 * @return a new array that has no refrence to the original
	 */
	public static int[][] deepCopy(int[][] a){
		int[][] b = new int[a.length][a[0].length];
		
		for (int i = 0; i<a.length; i++){
			System.arraycopy(a[i], 0, b[i], 0, a[i].length);
		}
		
		return b;
	}
	
	/**
	 * fills a 2d int array with a given value
	 * 
	 * @param a the int array to fill with the value
	 * @param b the value to fill the int array with
	 */
	public static void fillMatrix(int[][] a, int b){
		for (int i = 0; i<a.length; i++){
			Arrays.fill(a[i], 0);
		}
	}
}
