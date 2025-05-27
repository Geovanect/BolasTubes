// src/components/Tube.jsx
import React from 'react';
import './Tube.css';

const cores = {
  red: '#ff4d4d',
  green: '#4dff88',
  blue: '#4d94ff',
  yellow: '#ffff66',
  purple: '#cc66ff',
  black: '#000000',
  pink: '#ff66b2',
  orange: '#ff9933',
};

export default function Tube({ bolas, onClick }) {
  return (
    <div className="tube" onClick={onClick}>
      {Array.from({ length: 4 }).map((_, i) => {
        const cor = bolas[3 - i];
        return (
          <div
            key={i}
            className="ball"
            style={{
              backgroundColor: cores[cor] || '#e0e0e0',
              border: cor ? '1px solid black' : 'none',
            }}
          ></div>
        );
      })}
    </div>
  );
}
