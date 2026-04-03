import api from './axios';

// 전체 조회 + 검색
export const getProduct = (page, keyword, view, category, forSale) =>
    api.get('/product/get', {
        params: { page, keyword, view, category, forSale }
    }
);

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
    }
);

// select 선택용 제품목록
export const getProductList = () =>
    api.get('/product/getlist');