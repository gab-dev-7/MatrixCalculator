package com.matrixcalculator.matrixCalculatorAPI;

public class MatrixOperationRequest {
  private double[][] matrixA;
  private double[][] matrixB;

  public double[][] getMatrixA() {
    return matrixA;
  }

  public void setMatrixA(double[][] matrixA) {
    this.matrixA = matrixA;
  }

  public double[][] getMatrixB() {
    return matrixB;
  }

  public void setMatrixB(double[][] matrixB) {
    this.matrixB = matrixB;
  }

  public MatrixOperationRequest(double[][] matrixA, double[][] matrixB) {
    this.matrixA = matrixA;
    this.matrixB = matrixB;
  }

}
