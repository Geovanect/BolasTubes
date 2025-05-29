// src/App.jsx
import React, { useEffect, useState } from 'react';
import { getJogo, iniciarJogoAPI, moverBola, verificarVitoria } from './api.js';
import './App.css';
import GameModal from './components/GameModal';
import Tube from './components/Tube';

// Mapeamento de cores do backend para o frontend
const colorMap = {
  "Vermelho": "red",
  "Azul": "blue",
  "Verde": "green",
  "Amarelo": "yellow",
  "Laranja": "orange",
  "Roxo": "purple",
  "Branco": "#ffffff", // Usar código hexadecimal para branco se não houver nome no Tube.jsx
  // Adicionar outras cores se necessário
};

export default function App() {
  const [tubos, setTubos] = useState([]);
  const [modal, setModal] = useState({ show: true, title: 'NEW GAME' });
  const [origem, setOrigem] = useState(null);

  const handleStartGame = async () => {
    try {
      const res = await iniciarJogoAPI();
      console.log("Dados recebidos da API:", res.data);
      // res.data é esperado como: [{bolas: [{cor: "Vermelho"}, ...]}, ...]
      setTubos(res.data || []); // Garantir que tubos seja um array
      setModal({ show: false });
      setOrigem(null);
    } catch (error) {
      console.error("Erro ao iniciar o jogo:", error);
      setTubos([]); // Limpar tubos em caso de erro
    }
  };

  const handleTubeClick = async (index) => {
    if (origem === null) {
      setOrigem(index);
    } else {
      if (origem !== index) {
        try {
          await moverBola(origem, index);
          const res = await getJogo();
          console.log("Dados após mover bola:", res.data);
          setTubos(res.data || []); // Garantir que tubos seja um array

          const venceu = await verificarVitoria();
          if (venceu.data === true) {
            setModal({ show: true, title: 'TRY AGAIN' });
          }
        } catch (error) {
          console.error("Erro ao mover bola ou verificar vitória:", error);
        }
      }
      setOrigem(null);
    }
  };

  useEffect(() => {
    // Se desejar que o jogo comece automaticamente ao carregar a página (sem modal inicial):
    // handleStartGame(); 
  }, []);

  return (
    <div className="app">
      <GameModal show={modal.show} title={modal.title} onStart={handleStartGame} />
      <div className="game-container">
        {/* 
          Cada 'tubo' aqui é um objeto Pilha do backend: { bolas: Array<BolaObjeto> } 
          BolaObjeto é: { cor: "StringCorBackend" }
          Tube.jsx espera um array de strings de cores do frontend: ["red", "blue", ...]
        */}
        {Array.isArray(tubos) && tubos.map((tuboBackend, index) => {
          // Extrai o array de objetos Bola da pilha atual
          const bolasDaPilha = tuboBackend.bolas || []; 
          // Mapeia os objetos Bola para strings de cores do frontend
          const coresParaFrontend = bolasDaPilha.map(bola => colorMap[bola.cor] || bola.cor.toLowerCase());
          return (
            <Tube 
              key={index} 
              index={index}
              bolas={coresParaFrontend}
              onClick={() => handleTubeClick(index)} 
              isSelected={origem === index}
            />
          );
        })}
      </div>
    </div>
  );
}
