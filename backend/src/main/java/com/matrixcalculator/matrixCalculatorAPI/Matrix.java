package com.matrixcalculator.matrixCalculatorAPI;

import java.util.Random;

public class Matrix {

  private double[][] data;
  private int rows;
  private int cols;

  public Matrix(int rows, int cols) {

    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Matrix dimensions must be positive.");
    }

    this.rows = rows;
    this.cols = cols;
    this.data = new double[rows][cols];
  }

  public int getRows() {
    return this.rows;
  }

  public int getCols() {
    return this.cols;
  }

  public double[][] getData() {
    return this.data;
  }

  public void setElements(int row, int col, double value) {
    if (row < 0 || row >= this.rows || col < 0 || col >= this.cols) {
      throw new IllegalArgumentException("Invalid matrix indices.");
    }
    this.data[row][col] = value;
  }

  public double getElement(int row, int col) {
    if (row < 0 || row >= this.rows || col < 0 || col >= this.cols) {
      throw new IllegalArgumentException("Illegal matrix indices");
    }
    return this.data[row][col];
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        sb.append(String.format("%8.2f", this.data[i][j]));
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  // static factory method:

  public static Matrix identity(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("Size of identity must be positive");
    }
    Matrix identityMatrix = new Matrix(size, size);
    for (int i = 0; i < size; i++) {
      identityMatrix.data[i][i] = 1.0;
    }
    return identityMatrix;
  }

  // Basic Operations:

  public Matrix add(Matrix other) {
    if (this.rows != other.rows || this.cols != other.cols) {
      throw new IllegalArgumentException("Matrices must have same dimensions for addition");
    }

    Matrix result = new Matrix(this.rows, this.cols);
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        result.setElements(i, j, this.data[i][j] + other.data[i][j]);
      }
    }
    return result;
  }

  public Matrix subtract(Matrix other) {
    if (this.rows != other.rows || this.cols != other.cols) {
      throw new IllegalArgumentException("Matrices must have same dimensions for addition");
    }

    Matrix result = new Matrix(this.rows, this.cols);
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        result.setElements(i, j, this.data[i][j] - other.data[i][j]);
      }
    }
    return result;
  }

  public Matrix multiply(Matrix other) {
    if (this.cols != other.rows) {
      throw new IllegalArgumentException("#columns of the first matrix must be equal #rows of the second matrix");
    }

    Matrix result = new Matrix(this.rows, this.cols);
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        double sum = 0;
        for (int k = 0; k < this.cols; k++) {
          sum += this.data[i][k] * other.data[k][j];
        }
        result.setElements(i, j, sum);
      }
    }
    return result;
  }

  public Matrix transpose() {
    Matrix result = new Matrix(this.cols, this.rows);
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        result.setElements(j, i, this.data[i][j]);
      }
    }
    return result;
  }

  // more advances Operations:

  public double determinant() {
    if (this.rows != this.cols) {
      throw new IllegalArgumentException("Determinant can only be calculated for square matrices");
    }
    return determinant(this.data);
  }

  private double determinant(double[][] matrix) {
    int n = matrix.length;
    if (n == 1) {
      return matrix[0][0];
    }
    if (n == 2) {
      return (matrix[0][0] * matrix[1][1] - (matrix[0][1] * matrix[1][0]));
    }

    double det = 0;
    for (int j = 0; j < n; j++) {
      det += Math.pow(-1, j) * matrix[0][j] * determinant(minor(matrix, 0, j));
    }
    return det;
  }

  private double[][] minor(double[][] matrix, int row, int col) {
    int n = matrix.length;
    double[][] minorMatrix = new double[n - 1][n - 1];
    int minorRow = 0;
    for (int i = 0; i < n; i++) {
      if (i == row)
        continue;
      int minorCol = 0;
      for (int j = 0; j < n; j++) {
        if (j == col)
          continue;
        minorMatrix[minorRow][minorCol] = matrix[i][j];
        minorCol++;
      }
      minorRow++;
    }
    return minorMatrix;
  }

  public Matrix inverse() {
    if (this.rows != this.cols) {
      throw new IllegalArgumentException("Inverse can only be calculated for square matrices");
    }

    double det = this.determinant();
    if (det == 0) {
      throw new ArithmeticException("Matrix is singular and cannot be inverted (determinant is zero)");
    }

    Matrix cofactorMatrix = this.cofactor();
    Matrix adjugateMatrix = cofactorMatrix.transpose();

    Matrix inverse = new Matrix(rows, cols);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        inverse.data[i][j] = adjugateMatrix.data[i][j] / det;
      }
    }
    return inverse;
  }

  public Matrix cofactor() {
    if (this.rows != this.cols) {
      throw new IllegalArgumentException("Cofactor matrix can only be calculated for square matrices.");
    }
    Matrix cofactorMatrix = new Matrix(rows, cols);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        double[][] minor = minor(this.data, i, j);
        double cofactor = Math.pow(-1, i + j) * determinant(minor);
        cofactorMatrix.setElements(i, j, cofactor);
      }
    }
    return cofactorMatrix;
  }

  public Matrix inverseByGaussianElimination() {
    if (this.rows != this.cols) {
      throw new IllegalArgumentException("Inverse can only be calculated for square matrices.");
    }

    int n = this.rows;
    Matrix augmented = new Matrix(n, 2 * n);
    Matrix identity = Matrix.identity(n);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        augmented.data[i][j] = this.data[i][j];
        augmented.data[i][j + n] = identity.data[i][j];
      }
    }

    for (int i = 0; i < n; i++) {
      int maxRow = i;
      for (int k = i + 1; k < n; k++) {
        if (Math.abs(augmented.data[k][i]) > Math.abs(augmented.data[maxRow][i])) {
          maxRow = k;
        }
      }

      double[] temp = augmented.data[i];
      augmented.data[i] = augmented.data[maxRow];
      augmented.data[maxRow] = temp;

      double pivot = augmented.data[i][i];
      if (pivot == 0) {
        throw new ArithmeticException("Matrix is singular and cannot be inverted.");
      }

      for (int j = i; j < 2 * n; j++) {
        augmented.data[i][j] /= pivot;
      }

      for (int k = 0; k < n; k++) {
        if (k != i) {
          double factor = augmented.data[k][i];
          for (int j = i; j < 2 * n; j++) {
            augmented.data[k][j] -= factor * augmented.data[i][j];
          }
        }
      }
    }

    Matrix inverse = new Matrix(n, n);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        inverse.data[i][j] = augmented.data[i][j + n];
      }
    }
    return inverse;
  }

  public Matrix(double[][] data) {
    this.rows = data.length;
    this.cols = data[0].length;
    this.data = new double[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        this.data[i][j] = data[i][j];
      }
    }
  }

  public static Matrix random(int rows, int cols) {
    Matrix result = new Matrix(rows, cols);
    Random random = new Random();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        result.data[i][j] = random.nextInt(201) - 100;
      }
    }
    return result;
  }

}
