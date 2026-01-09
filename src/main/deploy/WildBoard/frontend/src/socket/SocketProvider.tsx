// src/context/SocketContext.js
import { createContext } from 'preact';
import { useContext } from 'preact/hooks';
import SocketStore from './SocketStore.ts';

const SocketContext = createContext<SocketStore | null>(null);

// Create a single shared instance
const socket = new SocketStore();

export const SocketStoreProvider = ({ children }: { children: preact.ComponentChildren }) => {
  return (
    <SocketContext.Provider value={socket}>
      {children}
    </SocketContext.Provider>
  );
};

export const useSocketStore = () => useContext(SocketContext);