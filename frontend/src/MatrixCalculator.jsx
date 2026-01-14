import React, { useState, useEffect } from 'react';
import './MatrixCalculator.css';

function MatrixCalculator() {
  const [matrixAInput, setMatrixAInput] = useState('1 2\n3 4');
  const [matrixBInput, setMatrixBInput] = useState('5 6\n7 8');
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);
  const [isSingleMatrixOp, setIsSingleMatrixOp] = useState(false);
  const [operation, setOperation] = useState(null);
  const [equation, setEquation] = useState('');
  const [randomDim, setRandomDim] = useState('2 2');
  const [minVal, setMinVal] = useState('-10');
  const [maxVal, setMaxVal] = useState('10');

  const singleMatrixOps = ['determinant', 'transpose', 'inverse'];

  useEffect(() => {
    if (isSingleMatrixOp) {
      setMatrixBInput('');
    } else {
      setMatrixBInput('5 6\n7 8');
    }
  }, [isSingleMatrixOp]);

  const parseMatrix = (matrixString) => {
    const rows = matrixString.trim().split('\n');
    if (rows.length === 0 || (rows.length === 1 && rows[0] === '')) {
      return null;
    }
    const parsedRows = rows.map(row => row.split(' ').map(Number));

    const firstRowCols = parsedRows[0].length;
    if (!parsedRows.every(row => row.length === firstRowCols)) {
      throw new Error('All rows must have the same number of columns.');
    }

    if (parsedRows.some(row => row.some(isNaN))) {
      throw new Error('Matrix input contains non-numeric values.');
    }

    return parsedRows;
  };

  const formatMatrixForDisplay = (data) => {
    if (typeof data === 'number') {
      return data.toString();
    }

    if (!data || data.length === 0) {
      return '';
    }
    return data.map(row => row.join(' ')).join('\n');
  };

  const handleOperation = async (op) => {
    setResult(null);
    setError(null);
    setOperation(op);
    setEquation('');

    const isSingleOp = singleMatrixOps.includes(op);
    setIsSingleMatrixOp(isSingleOp);

    try {
      const matrixA = parseMatrix(matrixAInput);
      if (matrixA === null) {
        throw new Error('Matrix A cannot be empty.');
      }
      let matrixB = null;
      if (!isSingleOp) {
        matrixB = parseMatrix(matrixBInput);
        if (matrixB === null) {
          throw new Error('Matrix B cannot be empty for this operation.');
        }
      }

      const requestBody = {
        matrixA: matrixA,
        matrixB: matrixB
      };

      const response = await fetch(`${import.meta.env.VITE_APP_API_URL}/api/matrices/${op}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestBody),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.error || data.message || 'Something went wrong with the API.');
      }

      let opSymbol = '';
      switch (op) {
        case 'add': opSymbol = '+'; break;
        case 'subtract': opSymbol = '-'; break;
        case 'multiply': opSymbol = '×'; break;
        case 'transpose': opSymbol = 'ᵀ'; break;
        case 'inverse': opSymbol = '⁻¹'; break;
        case 'determinant': opSymbol = 'det'; break;
        default: opSymbol = '';
      }

      if (isSingleOp) {
        if (op === 'determinant') {
          setEquation(`det(A) = ${data}`);
        } else if (op === 'inverse') {
          setEquation(`A⁻¹ = Result`);
        } else {
          setEquation(`Aᵀ = Result`);
        }
      } else {
        setEquation(`A ${opSymbol} B = Result`);
      }

      setResult(data.data || data);

    } catch (err) {
      setError(err.message);
    }
  };

  const handleRandom = async () => {
    try {
      const [rows, cols] = randomDim.split(' ').map(Number);
      if (isNaN(rows) || isNaN(cols) || rows <= 0 || cols <= 0) {
        throw new Error('Invalid dimensions. Please enter positive numbers.');
      }

      const min = parseInt(minVal, 10);
      const max = parseInt(maxVal, 10);

      if (isNaN(min) || isNaN(max)) {
        throw new Error('Invalid range values.');
      }

      if (min >= max) {
        throw new Error('Min value must be less than Max value.');
      }

      const response = await fetch(`${import.meta.env.VITE_APP_API_URL}/api/matrices/random?rows=${rows}&cols=${cols}&min=${min}&max=${max}`);
      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.error || data.message || 'Failed to generate random matrices.');
      }

      setMatrixAInput(formatMatrixForDisplay(data.matrixA));
      setMatrixBInput(formatMatrixForDisplay(data.matrixB));
      setError(null);
      setResult(null);
      setOperation(null);
      setEquation('');
    } catch (err) {
      setError(err.message);
    }
  };

  const handleClear = () => {
    setMatrixAInput('');
    setMatrixBInput('');
    setResult(null);
    setError(null);
    setOperation(null);
    setIsSingleMatrixOp(false);
    setEquation('');
  };

  return (
    <div className="calculator-container">
      <h2>Matrix Calculator</h2>
      <div className="input-section">
        <textarea
          placeholder="Enter Matrix A (e.g., 1 2&#10;3 4)"
          value={matrixAInput}
          onChange={(e) => setMatrixAInput(e.target.value)}
        />
        <div className="operation-buttons-container">
          <button onClick={() => handleOperation('add')}>Add</button>
          <button onClick={() => handleOperation('subtract')}>Subtract</button>
          <button onClick={() => handleOperation('multiply')}>Multiply</button>
          <button onClick={() => handleOperation('transpose')}>Transpose</button>
          <button onClick={() => handleOperation('determinant')}>Determinant</button>
          <button onClick={() => handleOperation('inverse')}>Inverse</button>
          <div className="random-generator-container">
            <input
              type="text"
              placeholder="rows cols"
              value={randomDim}
              onChange={(e) => setRandomDim(e.target.value)}
              className="dim-input"
            />
            <input
              type="number"
              placeholder="Min"
              value={minVal}
              onChange={(e) => setMinVal(e.target.value)}
              className="range-input"
            />
            <input
              type="number"
              placeholder="Max"
              value={maxVal}
              onChange={(e) => setMaxVal(e.target.value)}
              className="range-input"
            />
            <button onClick={handleRandom} className="random-button">Random</button>
          </div>
          <button onClick={handleClear} className="clear-button">Clear</button>
        </div>
        <div className={`matrix-b-container ${isSingleMatrixOp ? 'hidden' : ''}`}>
          <textarea
            placeholder="Enter Matrix B (e.g., 5 6&#10;7 8)"
            value={matrixBInput}
            onChange={(e) => setMatrixBInput(e.target.value)}
          />
        </div>
      </div>
      <div className={`result-display ${operation ? 'show' : ''}`}>
        <h3 className="equation-heading">{equation}</h3>
        {error && <pre className="error">{error}</pre>}
        {result && <pre className="result-matrix">{formatMatrixForDisplay(result)}</pre>}
      </div>
    </div>
  );
}

export default MatrixCalculator;
