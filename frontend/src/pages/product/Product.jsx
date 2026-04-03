import { useState, useEffect } from 'react';
import { createProduct, getProduct, putProduct, deleteProduct } from '../../api/productApi';
import './Product.css';
import '../Page.css';
import Pagination from '@mui/material/Pagination';
import useStore from '../../store/useStore';
import ProductModal from './ProductModal';


function Product() {

    const [keyword, setKeyword] = useState('');
    const [list, setList] = useState([]);
    const [open, setOpen] = useState(false);
    const count = useStore(s => s.count);
    const increase = useStore(s => s.increase);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [forSale, setForSale] = useState('');   // 판매중여부 Y, N
    const [category, setCategory] = useState(''); // 카테고리 상의, 하의
    // 페이징처리 위한 상태변수 전달받은객체
    let [ pageDto , setPageDto ] = useState({
        productDtos : [] ,
        totalPage : 0 ,
        totalCount : 0
    });

    // 페이징처리 위한 페이지 번호 상태변수 전달할 객체
    const [ pageInfo , setPageInfo ] = useState({
        page : 1 , forSale : '', category : '', keyword : '' , view : 10 // view 초기값 10
    });


    // 조회 함수
    const fetchProduct = () => {
        console.log("pageInfo:", pageInfo);
        getProduct(pageInfo.page, pageInfo.keyword, pageInfo.view ,pageInfo.category , pageInfo.forSale)
        .then(res =>
            {console.log(res.data); setPageDto(res.data);})
        .catch(err => console.error(err));
    };

    // 처음 전체조회
    useEffect(() => {
        fetchProduct();
    }, [pageInfo.page, pageInfo.keyword, pageInfo.view, pageInfo.category, pageInfo.forSale]);


    // 검색
    const productSearch = (e) => {
        e.preventDefault();
        setPageInfo(prev => ({
            ...prev,
            page: 1,
            keyword: keyword ,
            forSale: forSale,
            category: category
        }));
    };

    //등록 버튼 클릭시 핸들러
    const handleOpenRegister = () => {
        setSelectedProduct(null); // 수정 데이터를 비우고
        setOpen(true);             // 모달 열기
    };

    //수정 버튼 클릭 핸들러 (item 데이터를 인자로 받음)
    const handleOpenUpdate = (item) => {
        setSelectedProduct(item); // 클릭한 행의 데이터를 저장하고
        setOpen(true);             // 모달 열기
    };

    //  저장/수정 처리 함수
    const handleSaveOrUpdate = async (formData) => {
        if (selectedProduct) {
            // 수정 모드
            console.log("수정 실행", formData);
            await putProduct(formData);
        } else {
            // 등록 모드
            console.log("등록 실행", formData);
            await createProduct(formData);
        }
        setOpen(false);
        fetchProduct();
    };

    // 제품 (삭제)판매 상태 함수
    const productDelete = (productId)=>{
        console.log("제품삭제");
        if(window.confirm('정말 삭제 하시겠습니까.')) {

            deleteProduct(productId)
            .then(res =>
                {console.log(res.data);
                  setPageDto(res.data);
                fetchProduct();})
            .catch(err => console.error(err));
        }
    }

    const onPageSelect = (e , value ) =>{
        console.log(value);
        pageInfo.page = value; // 클릭한 페이지번호로 변경
        setPageInfo({ ...pageInfo });   // 새로고침 [ 상태변수의 주소값이 바뀌면 재랜더링 ]
    }


    return (
        <div className="bodyContainer">

            <div className="page-header">
                <h2>제품 관리</h2>
            </div>

            {/* 검색 영역 */}
            <div className="search-panel">
                <form onSubmit={productSearch}>

                    <input
                        type="text"
                        name="keyword"
                        placeholder="제품명 검색"
                        value={keyword}
                        onChange={e => setKeyword(e.target.value)}
                    />

                    <select value={forSale} onChange={e => setForSale(e.target.value)}>
                        <option value="">판매여부 전체</option>
                        <option value="Y">판매중</option>
                        <option value="N">판매종료</option>
                    </select>

                    <select value={category} onChange={e => setCategory(e.target.value)}>
                        <option value="">카테고리 전체</option>
                        <option value="상의">상의</option>
                        <option value="하의">하의</option>
                    </select>

                    <button type="submit">검색</button>
                </form>

                    <button onClick={handleOpenRegister}>제품 등록</button>
            </div>

            {/* 테이블 */}
            <div className="table-panel">
                <table>
                    <thead>
                        <tr>
                        <th>번호</th>
                        <th>제품명</th>
                        <th>카테고리</th>
                        <th>가격</th>
                        <th>판매여부</th>
                        <th>생성일</th>
                        <th>관리</th>
                        </tr>
                    </thead>

                    <tbody>
                        {pageDto.productDtos && pageDto?.productDtos?.map((item, idx) => (
                            <tr key={item.productId}>
                                <td>{item.productId}</td>
                                <td>{item.productName}</td>
                                <td>{item.category}</td>
                                <td>{item.price}원</td>
                                <td>{item.forSale=='Y' ? '판매중' : '판매종료'}</td>
                                <td>{item.createdDt}</td>
                                <td>
                                    <button onClick={() => handleOpenUpdate(item)}>수정</button>
                                    <button onClick={() => productDelete(item.productId)}>삭제</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className="pagination">
                <Pagination
                    count={pageDto.totalPage}
                    page={pageInfo.page}
                    onChange={onPageSelect}
                />
            </div>
            <ProductModal
                open={open}
                setOpen={setOpen}
                onSave={handleSaveOrUpdate}
                data={selectedProduct}
            />
        </div>
    );
}

export default Product;