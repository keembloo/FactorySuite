import api from './axios';

// 전체 조회 + 검색
export const getCustomer = (page, key, keyword, view) =>
    api.get('/customer/get', {
        params: { page, key, keyword, view }
    });

// 거래처 등록
export const createCustomer = (data) =>
    api.post('/customer/post', data);

// 수정
export const putCustomer = (data) =>
    api.put('/customer/put', data);

// 삭제
export const deleteCustomer = (customerId) =>
    api.delete(`/customer/delete`, {
        params: { customerId }
    });