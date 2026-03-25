// store/logger.js
const logger = (config) => (set, get, api) =>
  config(
    (args, replace, name) => {
      console.group(`🚀 Action: ${name || '이름 없음'}`);
      console.log('이전 상태:', get());
      set(args, replace, name);
      console.log('다음 상태:', get());
      console.groupEnd();
    },
    get,
    api
  );

export default logger;