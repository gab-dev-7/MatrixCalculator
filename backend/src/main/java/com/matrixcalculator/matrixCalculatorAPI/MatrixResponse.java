package com.matrixcalculator.matrixCalculatorAPI;

public class MatrixResponse {
  private double[][] data;
  private int rows;
  private int cols;

  public MatrixResponse(double[][] data, int rows, int cols) {
    this.data = data;
    this.rows = rows;
    this.cols = cols;
  }

  // Getters and Setters
  public double[][] getData() {
    return data;
  }

  public void setData(double[][] data) {
    this.data = data;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getCols() {
    return cols;
  }

  public void setCols(int cols) {
    this.cols = cols;
  }
}
