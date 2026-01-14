package com.matrixcalculator.matrixCalculatorAPI;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matrices")
@CrossOrigin
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

  @ExceptionHandler({ ArithmeticException.class, IllegalArgumentException.class })
  public ResponseEntity<Map<String, String>> handleMatrixExceptions(RuntimeException ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", ex.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(errorResponse);
  }
}
