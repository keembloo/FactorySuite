import api from './axios';

// 전체 조회 + 검색
export const getProduct = (page, key, keyword, view) =>
    api.get('/product/get', {
        params: { page, key, keyword, view }
    });

// 제품 등록
export const createProduct = (data) =>
    api.post('/product/post', data);

// 수정
export const putProduct = (data) =>
    api.put('/product/put', data);

// 삭제
export const deleteProduct = (productId) =>
    api.delete(`/product/delete`, {
        params: { productId }
    });