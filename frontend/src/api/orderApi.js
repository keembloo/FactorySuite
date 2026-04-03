import api from './axios';

// 전체 조회 + 검색
export const getOrder = (page, key, keyword, status, startDate, endDate, view) =>
    api.get('/order/get', {
        params: { page, key, keyword, status, startDate, endDate, view }
    });

// 제품 등록
export const createOrder = (data) =>
    api.post('/order/post', data);

// 수정
export const putOrder = (data) =>
    api.put('/order/put', data);

// 삭제
export const deleteOrder = (orderId) =>
    api.delete(`/order/delete`, {
        params: { orderId }
    });