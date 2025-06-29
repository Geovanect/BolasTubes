// src/components/GameModal.jsx
import React from 'react';
import './GameModal.css';

export default function GameModal({ show, title, onStart, restartText }) {
  if (!show) return null;

  return (
    <div className="modal">
      <div className="modal-box">
        <h2>{title}</h2>
        <button onClick={onStart}>{restartText || 'START'}</button>
      </div>
    </div>
  );
}
