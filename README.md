# Matrix Calculator

A web-based matrix calculator with a React frontend and a Spring Boot backend.

## Features

- **Matrix Operations**:
  - Addition
  - Subtraction
  - Multiplication
  - Transpose
  - Determinant
  - Inverse
- **Random Matrix Generation**: Generate random matrices with specified dimensions.

## Tech Stack

- **Frontend**:
  - React
  - Vite
- **Backend**:
  - Java
  - Spring Boot

## Getting Started

### Prerequisites

- Node.js and npm
- Java Development Kit (JDK)
- Maven

### Installation

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/MatrixCalculator.git
    cd MatrixCalculator
    ```

2.  **Run the backend:**

    ```bash
    cd backend
    mvn spring-boot:run
    ```

    The backend will be running on `http://localhost:8080`.

3.  **Run the frontend:**

    In a new terminal, navigate to the `frontend` directory:

    ```bash
    cd frontend
    npm install
    npm run dev
    ```

    The frontend will be running on `http://localhost:5173`.

4.  **Open the application:**

    Open your browser and navigate to `http://localhost:5173`.