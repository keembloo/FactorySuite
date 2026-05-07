import React from 'react';
import { useState, useEffect } from 'react';
import { getProcessFlow } from '../../api/processFlowApi';
import { getProductList} from '../../api/productApi';
import './ProcessFlow.css';
import '../Page.css';
import Pagination from '@mui/material/Pagination';
import useStore from '../../store/useStore';
import ProcessFlowModal from './ProcessFlowModal'

function ProcessFlow(){
    const [key, setKey] = useState('productName');
    const [keyword, setKeyword] = useState('');
    const [list, setList] = useState([]);
    const [open, setOpen] = useState(false);
    const count = useStore(s => s.count);
    const increase = useStore(s => s.increase);
    const [selectedProcess, setSelectedProcess] = useState(null);
    const [inspectionYn, setInspectionYn] = useState('');   // 검수여부 Y, N
    const [useYn, setUseYn] = useState(''); // 사용여부
    const [flowList, setFlowList] = useState([]);
    const [selectedProduct, setSelectedProduct] = useState(null); // 제품
    const [productList, setProductList] = useState([]); // 제품 리스트

    // 페이징처리 위한 상태변수 전달받은객체
    let [ pageDto , setPageDto ] = useState({
        processDtos : [] ,
        totalPage : 0 ,
        totalCount : 0 ,
    });

    // 페이징처리 위한 페이지 번호 상태변수 전달할 객체
    const [ pageInfo , setPageInfo ] = useState({
        page : 1 , key : 'productName' , keyword : '' , view : 10 , useYn :'' // view 초기값 10
    });

    // 처음 전체조회
    useEffect(() => {
        fetchProductList();
    }, [pageInfo.page, pageInfo.key, pageInfo.keyword, pageInfo.view ]);


    // 검색
    const productSearch = (e) => {
        e.preventDefault();
        setPageInfo(prev => ({
            ...prev,
            page: 1,
            key: key ,
            keyword: keyword
        }));
    };

    // 제품 리스트 조회 함수
    const fetchProductList = () => {
        console.log("제품 리스트 조회 실행");

        getProductList(pageInfo.page, pageInfo.key, pageInfo.keyword, pageInfo.view)
            .then(res => {
                console.log("제품 리스트:", res.data);
                setProductList(res.data);
            })
            .catch(err => console.error(err));
    };


    // 제품 선택시 공정 호출
    const handleSelectProduct = (productId,productName) =>{
        console.log("함수 실행",productId);
        setSelectedProduct(productName); // 선택된 제품 저장

        getProcessFlow(productId)
            .then(res => {
                console.log("공정 호출:", res.data);
                //setProductList(res.data);
            })
            .catch(err => console.error(err));
    }

   //등록 버튼 클릭시 핸들러
    const handleOpenRegister = () => {
        setSelectedProcess(null); // 수정 데이터를 비우고
        setOpen(true);             // 모달 열기
    };

    //수정 버튼 클릭 핸들러 (item 데이터를 인자로 받음)
    const handleOpenUpdate = (item) => {
        setSelectedProcess(item); // 클릭한 행의 데이터를 저장하고
        setOpen(true);             // 모달 열기
    };

    //  저장/수정 처리 함수
    const handleSaveOrUpdate = async (formData) => {
        if (selectedProcess) {
            // 수정 모드
            console.log("수정 실행", formData);
            await putProcess(formData);
        } else {
            // 등록 모드
            console.log("등록 실행", formData);
            await createProcess(formData);
        }
        setOpen(false);
        fetchProcess();
    };

    // 제품 (삭제)판매 상태 함수
    const processDelete = (processId)=>{
        console.log("공정삭제");
        if(window.confirm('정말 삭제 하시겠습니까.')) {

            deleteProcess(processId)
            .then(res =>
                {console.log(res.data);
                  setPageDto(res.data);
                fetchProcess();})
            .catch(err => console.error(err));
        }
    }

    const onPageSelect = (e , value ) =>{
        console.log(value);
        pageInfo.page = value; // 클릭한 페이지번호로 변경
        setPageInfo({ ...pageInfo });   // 새로고침 [ 상태변수의 주소값이 바뀌면 재랜더링 ]
    }

    const handleFlowSave = ()=>{
        console.log("등록 실행");
    }



    return(
        <div className="flowContainer">

            <div className="page-header">
                <h2>공정순서 관리</h2>
            </div>

            {/* 전체 영역 */}
            <div className="flow-body">

                {/* 좌측 - 제품 리스트 */}
                <div className="product-panel">

                    <div className="search-box">
                        <input
                        type="text"
                        placeholder="제품명 검색"
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                        />
                        <button onClick={productSearch}>검색</button>
                    </div>

                    <div className="product-list">
                        {productList?.map((p) => (
                            <div
                            key={p.productId}
                            className={`product-item ${
                            selectedProduct?.productId === p.productId ? 'active' : ''
                            }`}
                            onClick={()=>handleSelectProduct(p.productId,p.productName)}
                            >
                            {p.productName}
                            </div>
                        ))}
                    </div>

                </div>

                {/* 우측 - 공정순서 */}
                <div className="flow-panel">

                    {!selectedProduct ? (
                    <div className="empty-box">제품을 선택하세요</div>
                    ) : ( <>
                    <div className="flow-header">
                        선택된 제품: <b>{selectedProduct}</b>
                        <button onClick={handleOpenRegister}>공정 추가</button>
                    </div>

                    <div className="flow-table">
                        <table>
                        <thead>
                            <tr>
                                <th>순서</th>
                                <th>공정명</th>
                                <th>표준시간</th>
                                <th>이동</th>
                                <th>삭제</th>
                            </tr>
                        </thead>

                        <tbody>
                        {flowList.map((item, index) => (
                            <tr key={index}>
                                <td>{index + 1}</td>
                                <td>{item.processName}</td>

                                <td>
                                    <input
                                    type="number"
                                    value={item.standardTime}
                                    onChange={(e) =>
                                    handleTimeChange(index, e.target.value)
                                    }
                                    />
                                </td>

                                <td>
                                    <button className="btn-up" onClick={() => moveUp(index)}>↑</button>
                                    <button className="btn-down" onClick={() => moveDown(index)}>↓</button>
                                </td>

                                <td>
                                    <button className="btn-delete" onClick={() => handleDelete(index)}>
                                        삭제
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                <div className="flow-footer">
                    <button onClick={handleFlowSave}>저장</button>
                </div>
                </>
                )}

                </div>

            </div>


            <ProcessFlowModal
                open={open}
                setOpen={setOpen}
                onSave={handleSaveOrUpdate}
                data={selectedProcess}
            />

        </div>

    );

}

export default ProcessFlow;