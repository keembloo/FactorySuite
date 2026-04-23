import React from 'react';
import { useState, useEffect } from 'react';
import { createOrder, getOrder, putOrder, deleteOrder , getOrderDetail } from '../../api/orderApi';
import { getProductList } from '../../api/productApi';
import { getCustomerList } from '../../api/customerApi';
import './Order.css';
import '../Page.css';
import Pagination from '@mui/material/Pagination';
import useStore from '../../store/useStore';
import OrderModal from './OrderModal';

function Order() {
    // 검색시 사용
    const [key, setKey] = useState('orderNum');
    const [keyword, setKeyword] = useState('');
    //주문 리스트
    const [list, setList] = useState([]);
    // 팝업 창열기
    const [open, setOpen] = useState(false); // 팝업창 여부
    const [selectedOrder, setSelectedOrder] = useState(null); // 수정할 주문 데이터

    // 상태 필터
    const [status, setStatus] = useState('');   //주문상태
    const [startDate, setStartDate] = useState(''); //시작일
    const [endDate, setEndDate] = useState(''); //마감일

    //상세페이지 보기 토글
    const [openRow, setOpenRow] = useState(null);
    //상세페이지 상태
    const [orderItemMap, setOrderItemMap] = useState({});

    //거래처 , 상품 리스트
    const [customers, setCustomers] = useState([]);
    const [products, setProducts] = useState([]);

    // 페이징처리 위한 상태변수 전달받은객체
    let [ pageDto , setPageDto ] = useState({
        orderDtos : [] ,
        totalPage : 0 ,
        totalCount : 0
    });

    // 페이징처리 위한 페이지 번호 상태변수 전달할 객체
    const [ pageInfo , setPageInfo ] = useState({
        page : 1 , key : '', keyword : '' , status : '',startDate : '',endDate : '', view : 10 // view 초기값 10
    });

    // 최초1번+페이지 변경시 실행
    useEffect(() => {
        fetchOrder();
    }, [pageInfo.page, pageInfo.key, pageInfo.keyword, pageInfo.status, pageInfo.startDate, pageInfo.endDate, pageInfo.view]);

    // 랜더링 최초실행
    useEffect(() => {
        fetchData();
    }, []);

    // 조회 기능 함수 API연결
    const fetchOrder = () => {
        console.log("pageInfo:", pageInfo);
        getOrder(pageInfo.page, pageInfo.key , pageInfo.keyword, pageInfo.status,
            pageInfo.startDate, pageInfo.endDate, pageInfo.view )
            .then(res =>
                {console.log(res.data); setPageDto(res.data);})
            .catch(err => console.error(err));
    };


    // 검색
    const orderSearch = (e) => {
        e.preventDefault();


        let processedKeyword = keyword;

        // orderNum 검색일 때 '-' 제거
        if (key == 'orderNum') {
            processedKeyword = keyword.replaceAll('-', '');
        }

        setPageInfo(prev => ({
            ...prev,
            page: 1,
            key: key,
            keyword: processedKeyword ,
            status : status,
            startDate : startDate,
            endDate : endDate
        }));
    };

    //등록 버튼 클릭시 핸들러
    const handleOpenRegister = () => {
        setSelectedOrder(null); // 수정 데이터를 비우고
        setOpen(true);             // 모달 열기
    };


    //  저장/수정 처리 함수
    const handleSaveOrUpdate = async (formData) => {
    console.log("최종 전송 데이터", formData);
        if (selectedOrder) {
            // 수정 모드
            console.log("수정 실행", formData);
            await putOrder(formData);
        } else {
            // 등록 모드
            console.log("등록 실행", formData);
            await createOrder(formData);
        }
            setOpen(false);
            fetchOrder();
    };

    // 주문 (삭제)판매 상태 함수
    const orderDelete = (orderId)=>{
        console.log("주문삭제");
        if(window.confirm('정말 삭제 하시겠습니까.')) {

            deleteOrder(orderId)
            .then(res =>
                {
                    console.log(res.data);
                    setPageDto(res.data);
                    fetchOrder();
                }
            ).catch(err => console.error(err));
        }
    }

    // 페이지 변경 함수
    const onPageSelect = (e , value ) =>{
        console.log(value);
        pageInfo.page = value; // 클릭한 페이지번호로 변경
        setPageInfo({ ...pageInfo });   // 새로고침 [ 상태변수의 주소값이 바뀌면 재랜더링 ]
    }

    //주문상세 페이지 토글 열고닫기
    const toggleRow = async (orderId) => {
        if (openRow === orderId) {
            setOpenRow(null);
            return;
        }

    // 없을 때만 호출 (캐싱)
    if (!orderItemMap[orderId]) {
        try {
            const res = await getOrderDetail(orderId);
            console.log(res.data);
            const data = res?.data?.orderItemDtos ?? [];

            setOrderItemMap(prev => ({
                ...prev,
                [orderId]: data
            }));

            } catch (err) {
                console.error(err);
            }
        }

        setOpenRow(orderId);
    };


    // 주문등록시 셀렉트창 조회 비동기함수
    const fetchData = async () => {
    console.log("fetchData 실행됨");
        const customerRes = await getCustomerList(); // 거래처 리스트 가져오기
        const productRes = await getProductList();  // 다가져오면 제품 리스트 가져오기
        // 다가져오면 가져온 데이터 전달하여 상태저장
        setCustomers(customerRes.data);
        setProducts(productRes.data);

    };

    // 주문번호 출력시 하이픈 표시 함수
    const formatOrderNum = (orderNum) => {
        if (!orderNum) return '';

        const prefix1 = orderNum.substring(0, 3);   // ORD
        const prefix2 = orderNum.substring(3, 6);   // MES
        const date = orderNum.substring(6, 14);    // 20260420
        const seq = orderNum.substring(14);        // 0001

        return `${prefix1}-${prefix2}-${date}-${seq}`;
    };

    // 주문일시 초까지만 표시 함수
    const formatDateTime = (dateString) => {
        if (!dateString) return '';

        return dateString.replace('T', ' ').substring(0, 19);
    };

    return (
        <div className="bodyContainer">

            <div className="page-header">
                <h2>주문 관리</h2>
            </div>

            {/* 검색 영역 */}
            <div className="search-panel">
                <form onSubmit={orderSearch}>
                    <select
                        name="key"
                        value={key}
                        onChange={e => setKey(e.target.value)}
                    >
                        <option value="orderNum">주문번호</option>
                        <option value="customerName">거래처명</option>
                    </select>


                    <input
                        type="text"
                        name="keyword"
                        placeholder="검색"
                        value={keyword}
                        onChange={e => setKeyword(e.target.value)}
                    />

                    {/* 상태 검색 */}
                    <select value={status} onChange={e => setStatus(e.target.value)}>
                        <option value="">상태 전체</option>
                        <option value="대기">대기</option>
                        <option value="승인">승인</option>
                        <option value="출고">출고</option>
                        <option value="배송완료">배송완료</option>
                        <option value="취소">취소</option>
                    </select>

                    {/* 기간 검색 */}
                    <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} />
                    ~
                    <input type="date" value={endDate} onChange={e => setEndDate(e.target.value)} />

                    <button type="submit" onClick={orderSearch}>검색</button>
                </form>

                <button onClick={handleOpenRegister}>주문 등록</button>
            </div>

            {/* 테이블 */}
            <div className="table-panel">
                <table>
                    <thead>
                        <tr>
                            <th>주문번호</th>
                            <th>거래처</th>
                            <th>주문일시</th>
                            <th>가격</th>
                            <th>상태</th>
                            <th>관리</th>
                        </tr>
                    </thead>

                    <tbody>
                        {pageDto.orderDtos && pageDto?.orderDtos?.map((item, idx) => (
                            <React.Fragment key={item.orderId} >

                            <tr key={item.orderId} onClick={() => toggleRow(item.orderId)}>
                                <td>{formatOrderNum(item.orderNum)}</td>
                                <td>{item.customerName}</td>
                                <td>{formatDateTime(item.orderDt)}</td>
                                <td>{Number(item.totalPrice).toLocaleString('ko-KR')}원</td>
                                <td>{item.status}</td>
                                <td>
                                    <button onClick={(e) => { e.stopPropagation(); handleOpenUpdate(item); }}>주문승인</button>
                                    <button onClick={(e) => { e.stopPropagation(); orderDelete(item.orderId); }}>주문취소</button>
                                </td>
                            </tr>

                            {/* 상세 row */}
                            {openRow === item.orderId && (
                                <tr>
                                    <td colSpan="6">
                                        <div className="order-detail-box">

                                            {orderItemMap[item.orderId]?.map(detail => (
                                                <div key={detail.orderItemId} className="detail-row">
                                                    └ {detail.productName} / {detail.quantity}개 / {Number(detail.price).toLocaleString('ko-KR')}원
                                                </div>
                                            ))}

                                        </div>
                                    </td>
                                </tr>
                            )}
                        </React.Fragment>
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
            <OrderModal
                open={open}
                setOpen={setOpen}
                onSave={handleSaveOrUpdate}
                data={selectedOrder}
                customers={customers}
                products={products}
            />
        </div>
    );
}

export default Order;