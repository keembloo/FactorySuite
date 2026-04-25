import api from './axios';

// 전체 조회 + 검색
export const getProcess = (page, keyword, view) =>
    api.get('/process/get', {
        params: { page, keyword, view }
    }
);

// 제품 등록
export const createProcess = (data) =>
    api.post('/process/post', data);

// 수정
export const putProcess = (data) =>
    api.put('/process/put', data);

// 삭제
export const deleteProcess = (processId) =>
    api.delete(`/process/delete`, {
        params: { processId }
    }
);

// select 선택용 제품목록
export const getProcessList = () =>
    api.get('/process/getlist');