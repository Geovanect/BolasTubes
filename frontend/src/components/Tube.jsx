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
  white: '#ffffff',
  rosa: '#ff69b4',
};

export default function Tube({ bolas, onClick, isSelected, index }) {
  const tubeClassName = `tube ${isSelected ? 'selected' : ''}`;

  return (
    <div className={tubeClassName} onClick={onClick}>
      {Array.from({ length: 7 }).map((_, i) => {
        const bolaIndex = 6 - i;
        const cor = bolas[i];

        return (
          <div
            key={i}
            className="ball"
            style={{
              backgroundColor: cores[cor] || cor || '#e0e0e0',
              border: cor ? '1px solid black' : 'none',
            }}
          ></div>
        );
      })}
    </div>
  );
}
