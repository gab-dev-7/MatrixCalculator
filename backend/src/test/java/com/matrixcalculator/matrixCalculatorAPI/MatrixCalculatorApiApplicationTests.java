package com.matrixcalculator.matrixCalculatorAPI;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MatrixCalculatorApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMatrixAddition() {
		double[][] dataA = {{1, 2}, {3, 4}};
		double[][] dataB = {{5, 6}, {7, 8}};
		Matrix matrixA = new Matrix(dataA);
		Matrix matrixB = new Matrix(dataB);

		Matrix result = matrixA.add(matrixB);

		assertEquals(6.0, result.getElement(0, 0));
		assertEquals(8.0, result.getElement(0, 1));
		assertEquals(10.0, result.getElement(1, 0));
		assertEquals(12.0, result.getElement(1, 1));
	}

	@Test
	void testMatrixSubtraction() {
		double[][] dataA = {{5, 6}, {7, 8}};
		double[][] dataB = {{1, 2}, {3, 4}};
		Matrix matrixA = new Matrix(dataA);
		Matrix matrixB = new Matrix(dataB);

		Matrix result = matrixA.subtract(matrixB);

		assertEquals(4.0, result.getElement(0, 0));
		assertEquals(4.0, result.getElement(0, 1));
		assertEquals(4.0, result.getElement(1, 0));
		assertEquals(4.0, result.getElement(1, 1));
	}

	@Test
	void testMatrixMultiplication() {
		// A is 2x3
		double[][] dataA = {
				{1, 2, 3},
				{4, 5, 6}
		};
		Matrix matrixA = new Matrix(dataA);

		// B is 3x2
		double[][] dataB = {
				{7, 8},
				{9, 1},
				{2, 3}
		};
		Matrix matrixB = new Matrix(dataB);

		// Result should be 2x2
		// [ 1*7 + 2*9 + 3*2 , 1*8 + 2*1 + 3*3 ] = [ 7+18+6, 8+2+9 ] = [ 31, 19 ]
		// [ 4*7 + 5*9 + 6*2 , 4*8 + 5*1 + 6*3 ] = [ 28+45+12, 32+5+18 ] = [ 85, 55 ]
		Matrix result = matrixA.multiply(matrixB);

		assertEquals(2, result.getRows());
		assertEquals(2, result.getCols());
		assertEquals(31.0, result.getElement(0, 0));
		assertEquals(19.0, result.getElement(0, 1));
		assertEquals(85.0, result.getElement(1, 0));
		assertEquals(55.0, result.getElement(1, 1));
	}

	@Test
	void testMatrixTranspose() {
		// 2x3 Matrix
		double[][] data = {
				{1, 2, 3},
				{4, 5, 6}
		};
		Matrix matrix = new Matrix(data);
		Matrix result = matrix.transpose();

		// Should be 3x2
		assertEquals(3, result.getRows());
		assertEquals(2, result.getCols());
		
		assertEquals(1.0, result.getElement(0, 0));
		assertEquals(4.0, result.getElement(0, 1));
		assertEquals(2.0, result.getElement(1, 0));
		assertEquals(5.0, result.getElement(1, 1));
		assertEquals(3.0, result.getElement(2, 0));
		assertEquals(6.0, result.getElement(2, 1));
	}

	@Test
	void testDeterminant() {
		// 2x2 Matrix: det = ad - bc
		double[][] data = {
				{4, 6},
				{3, 8}
		};
		Matrix matrix = new Matrix(data);
		double det = matrix.determinant();
		
		// 4*8 - 6*3 = 32 - 18 = 14
		assertEquals(14.0, det);
		
		// Identity matrix determinant should be 1
		Matrix identity = Matrix.identity(3);
		assertEquals(1.0, identity.determinant());
	}

	@Test
	void testInverse() {
		// Simple 2x2 invertible matrix
		double[][] data = {
				{4, 7},
				{2, 6}
		};
		Matrix matrix = new Matrix(data);
		
		// Det = 4*6 - 7*2 = 24 - 14 = 10
		// Inverse = (1/10) * [ 6 -7 ]
		//                    [ -2 4 ]
		//         = [ 0.6  -0.7 ]
		//           [ -0.2  0.4 ]
		
		Matrix result = matrix.inverse();
		
		assertEquals(0.6, result.getElement(0, 0), 0.0001);
		assertEquals(-0.7, result.getElement(0, 1), 0.0001);
		assertEquals(-0.2, result.getElement(1, 0), 0.0001);
		assertEquals(0.4, result.getElement(1, 1), 0.0001);
	}

	@Test
	void testInvalidDimensions() {
		Matrix m2x2 = new Matrix(2, 2);
		Matrix m2x3 = new Matrix(2, 3);

		// Addition mismatch
		assertThrows(IllegalArgumentException.class, () -> {
			m2x2.add(m2x3);
		});

		// Subtraction mismatch
		assertThrows(IllegalArgumentException.class, () -> {
			m2x2.subtract(m2x3);
		});
		
		// Multiplication mismatch (cols A != rows B)
		// 2x2 * 3x2 -> 2 != 3
		Matrix m3x2 = new Matrix(3, 2);
		assertThrows(IllegalArgumentException.class, () -> {
			m2x2.multiply(m3x2); 
		});
	}
	
	@Test
	void testSingularMatrixInverse() {
		// Singular matrix (det = 0)
		double[][] data = {
				{1, 2},
				{2, 4}
		};
		Matrix matrix = new Matrix(data);
		
		assertThrows(ArithmeticException.class, () -> {
			matrix.inverse();
		});
	}

}
