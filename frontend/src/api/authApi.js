import axios from 'axios';

export const login = (username, password) =>
  axios.post('/api/auth/login', { username, password });