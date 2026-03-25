// store/useStore.js
import { create } from 'zustand';
import logger from './logger';

const useStore = create(
  logger((set) => ({
    count: 0,
    increase: () => set((s) => ({ count: s.count + 1 }), false, 'count/increase'),
  }))
);

export default useStore;