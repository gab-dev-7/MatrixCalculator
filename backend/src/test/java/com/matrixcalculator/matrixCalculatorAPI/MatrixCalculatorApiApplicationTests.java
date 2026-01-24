package com.matrixcalculator.matrixCalculatorAPI;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MatrixCalculatorApiApplicationTests {

	@Test
	void contextLoads() {
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

}
