import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/api',
});

export const getJogo = () => API.get('/bolas');
export const verificarVitoria = () => API.get('/vitoria');
export const moverBola = (origem, destino) =>
  API.post('/movimento', { origem, destino });

export default API;