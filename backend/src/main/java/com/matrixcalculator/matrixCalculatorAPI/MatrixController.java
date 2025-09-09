
package com.matrixcalculator.matrixCalculatorAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/matrices")
public class MatrixController {

  @PostMapping("/add")
  public MatrixResponse addMatrices(@RequestBody MatrixOperationRequest request) {
    Matrix matrixA = new Matrix(request.getMatrixA());
    Matrix matrixB = new Matrix(request.getMatrixB());
    Matrix resultMatrix = matrixA.add(matrixB);
    return new MatrixResponse(resultMatrix.getData(), resultMatrix.getRows(), resultMatrix.getCols());
  }

  @PostMapping("/subtract")
  public MatrixResponse subtractMatrices(@RequestBody MatrixOperationRequest request) {
    Matrix matrixA = new Matrix(request.getMatrixA());
    Matrix matrixB = new Matrix(request.getMatrixB());
    Matrix resultMatrix = matrixA.subtract(matrixB);
    return new MatrixResponse(resultMatrix.getData(), resultMatrix.getRows(), resultMatrix.getCols());
  }

  @PostMapping("/multiply")
  public MatrixResponse multiplyMatrices(@RequestBody MatrixOperationRequest request) {
    Matrix matrixA = new Matrix(request.getMatrixA());
    Matrix matrixB = new Matrix(request.getMatrixB());
    Matrix resultMatrix = matrixA.multiply(matrixB);
    return new MatrixResponse(resultMatrix.getData(), resultMatrix.getRows(), resultMatrix.getCols());
  }

  @PostMapping("/transpose")
  public MatrixResponse transposeMatrix(@RequestBody MatrixOperationRequest request) {
    Matrix matrixA = new Matrix(request.getMatrixA());
    Matrix resultMatrix = matrixA.transpose();
    return new MatrixResponse(resultMatrix.getData(), resultMatrix.getRows(), resultMatrix.getCols());
  }

  @PostMapping("/determinant")
  public double getDeterminant(@RequestBody MatrixOperationRequest request) {
    Matrix matrixA = new Matrix(request.getMatrixA());
    return matrixA.determinant();
  }

  @PostMapping("/inverse")
  public MatrixResponse getInverse(@RequestBody MatrixOperationRequest request) {
    Matrix matrixA = new Matrix(request.getMatrixA());
    Matrix resultMatrix = matrixA.inverse();
    return new MatrixResponse(resultMatrix.getData(), resultMatrix.getRows(), resultMatrix.getCols());
  }

  @GetMapping("/random")
  public MatrixOperationRequest getRandomMatrices(@RequestParam int rows, @RequestParam int cols) {
    Matrix matrixA = Matrix.random(rows, cols);
    Matrix matrixB = Matrix.random(rows, cols);
    return new MatrixOperationRequest(matrixA.getData(), matrixB.getData());
  }
}
