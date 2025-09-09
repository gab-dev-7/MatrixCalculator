import React from 'react';
import MatrixCalculator from './MatrixCalculator';
import './App.css'; // This will contain general app styles including the header

function App() {
  return (
    <div className="App-container">
      <header className="project-header">
        <h1>Linear Algebra Project</h1>
        <p>Computer Science Bachelor - ETH Zürich</p>
      </header>
      <MatrixCalculator />
    </div>
  );
}

export default App;
