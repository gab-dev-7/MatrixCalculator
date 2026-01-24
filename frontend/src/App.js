import "./App.css";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Matrix Calculator</h1>
      </header>
      <div className="container">
        {/* Matrix A Input */}
        <div className="matrix-section">
          <h2>Matrix A</h2>
          <div className="matrix-grid">
            {/* We'll add input fields here later */}
          </div>
        </div>

        {/* Matrix B Input */}
        <div className="matrix-section">
          <h2>Matrix B</h2>
          <div className="matrix-grid">
            {/* We'll add input fields here later */}
          </div>
        </div>
      </div>

      {/* Operations and Result */}
      <div className="controls-section">
        <div className="buttons-container">
          <button>Add</button>
          <button>Subtract</button>
          <button>Multiply</button>
          <button>Transpose</button>
          <button>Determinant</button>
          <button>Inverse</button>
        </div>
        <div className="result-container">
          <h2>Result</h2>
          <textarea readOnly rows="10" cols="50"></textarea>
        </div>
      </div>
    </div>
  );
}

export default App;
