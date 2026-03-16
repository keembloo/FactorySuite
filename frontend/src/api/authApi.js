import axios from 'axios';

export const login = (username, password) =>
  axios.post('http://localhost:8081/api/auth/login', { username, password });