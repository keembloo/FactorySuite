// App.jsx
import React, { useRef, createContext } from 'react';
import { BrowserRouter } from 'react-router-dom';
import AppRoutes from './routes';
import { useSnackbar } from 'notistack';

export const SocketContext = createContext();

export default function App() {
  const { enqueueSnackbar } = useSnackbar();

  // WebSocket 초기화
  const clientSocket = useRef(null);
  if (!clientSocket.current) {
    clientSocket.current = new WebSocket('ws://localhost:80/alram');

    clientSocket.current.onopen = (e) => console.log('WebSocket open', e);
    clientSocket.current.onerror = (e) => console.log('WebSocket error', e);
    clientSocket.current.onclose = (e) => console.log('WebSocket closed', e);

    clientSocket.current.onmessage = (e) => {
      console.log('WebSocket message', e.data);
      enqueueSnackbar(e.data, { variant: 'success' });
    };
  }

  return (
    <SocketContext.Provider value={clientSocket.current}>
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>
    </SocketContext.Provider>
  );
}