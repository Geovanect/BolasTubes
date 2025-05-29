import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/BolaTubes',
});

export const getJogo = () => API.get('/jogo/estado');
export const verificarVitoria = () => API.get('/jogo/finalizado');
export const moverBola = (origem, destino) =>
  API.post('/jogo/mover', { origem, destino });
export const iniciarJogoAPI = () => API.post('/jogo/iniciar');

export default API;