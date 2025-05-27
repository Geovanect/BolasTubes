// src/App.jsx
import React, { useEffect, useState } from 'react';
import Tube from './components/Tube';
import GameModal from './components/GameModal';
import { getJogo, verificarVitoria, moverBola } from './api';
import './App.css';

export default function App() {
  const [tubos, setTubos] = useState([]);
  const [modal, setModal] = useState({ show: true, title: 'NEW GAME' });
  const [origem, setOrigem] = useState(null);

  const carregarJogo = async () => {
    const res = await getJogo();
    setTubos(res.data);
    setModal({ show: false });
    setOrigem(null);
  };

  const handleTubeClick = async (index) => {
    if (origem === null) {
      setOrigem(index);
    } else {
      if (origem !== index) {
        await moverBola(origem, index);
        const res = await getJogo();
        setTubos(res.data);

        const venceu = await verificarVitoria();
        if (venceu.data === true) {
          setModal({ show: true, title: 'TRY AGAIN' });
        }
      }
      setOrigem(null);
    }
  };

  useEffect(() => {
    // opcional: auto start
    // carregarJogo();
  }, []);

  return (
    <div className="app">
      <GameModal show={modal.show} title={modal.title} onStart={carregarJogo} />
      <div className="game-container">
        {tubos.map((bolas, index) => (
          <Tube key={index} bolas={bolas} onClick={() => handleTubeClick(index)} />
        ))}
      </div>
    </div>
  );
}
