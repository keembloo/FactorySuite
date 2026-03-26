import { useState, useEffect } from 'react';
import { createCustomer, getCustomer, putCustomer, deleteCustomer } from '../../api/customerApi';
import './ItemManage.css';
import '../Page.css';
import Pagination from '@mui/material/Pagination';
import useStore from '../../store/useStore';


function ItemManage() {

    const [key, setKey] = useState('customerName');
    const [keyword, setKeyword] = useState('');
    const [list, setList] = useState([]);
    const [open, setOpen] = useState(false);
    const count = useStore(s => s.count);
    const increase = useStore(s => s.increase);
    const [selectedCustomer, setSelectedCustomer] = useState(null);

    // 페이징처리 위한 상태변수 전달받은객체
    let [ pageDto , setPageDto ] = useState({
        customerDtos : [] ,
        totalPage : 0 ,
        totalCount : 0
    });

    // 페이징처리 위한 페이지 번호 상태변수 전달할 객체
    const [ pageInfo , setPageInfo ] = useState({
        page : 1 , key : 'customerName' , keyword : '' , view : 10 // view 초기값 10
    });


    // 조회 함수
    const fetchCustomer = () => {
        getCustomer(pageInfo.page, pageInfo.key, pageInfo.keyword, pageInfo.view)
        .then(res =>
            {console.log(res.data); setPageDto(res.data);})
        .catch(err => console.error(err));
    };

    // 처음 전체조회
    useEffect(() => {
        fetchCustomer();
    }, [pageInfo.page, pageInfo.key, pageInfo.keyword, pageInfo.view]);


    // 검색
    const customerSearch = (e) => {
        e.preventDefault();
        setPageInfo(prev => ({
            ...prev,
            page: 1,
            key: key,
            keyword: keyword
        }));
    };

    //등록 버튼 클릭시 핸들러
    const handleOpenRegister = () => {
        setSelectedCustomer(null); // 수정 데이터를 비우고
        setOpen(true);             // 모달 열기
    };

    //수정 버튼 클릭 핸들러 (item 데이터를 인자로 받음)
    const handleOpenUpdate = (item) => {
        setSelectedCustomer(item); // 클릭한 행의 데이터를 저장하고
        setOpen(true);             // 모달 열기
    };

    //  저장/수정 처리 함수
    const handleSaveOrUpdate = async (formData) => {
        if (selectedCustomer) {
            // 수정 모드
            console.log("수정 실행", formData);
            await putCustomer(formData);
        } else {
            // 등록 모드
            console.log("등록 실행", formData);
            await createCustomer(formData);
        }
        setOpen(false);
        fetchCustomer();
    };

    // 거래처 삭제 함수
    const customerDelete = (customerId)=>{
        console.log("삭제실행");
        if(window.confirm('정말 삭제하시겠습니까? 거래처를 삭제하면 복구 할 수 없습니다.')) {

            deleteCustomer(customerId)
            .then(res =>
                {console.log(res.data);
                  setPageDto(res.data);
                fetchCustomer();})
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
        <form onSubmit={itemSearch}>
          <select
            name="key"
            value={key}
            onChange={e => setKey(e.target.value)}
          >
            <option value="itemName">거래처명</option>
            <option value="phone">전화번호</option>
          </select>

          <input
            type="text"
            name="keyword"
            placeholder="검색어 입력"
            value={keyword}
            onChange={e => setKeyword(e.target.value)}
          />

          <button type="submit">검색</button>
        </form>
        <button onClick={handleOpenRegister}>신규 등록</button>
      </div>

      {/* 테이블 */}
      <div className="table-panel">
        <table>
          <thead>
            <tr>
              <th>번호</th>
              <th>거래처명</th>
              <th>전화번호</th>
              <th>주소</th>
              <th>관리</th>
            </tr>
          </thead>

          <tbody>
            {pageDto.customerDtos && pageDto?.customerDtos?.map((item, idx) => (
              <tr key={item.customerId}>
                <td>{item.customerId}</td>
                <td>{item.customerName}</td>
                <td>{item.phone}</td>
                <td>{item.address}</td>
                <td>
                  <button onClick={() => handleOpenUpdate(item)}>수정</button>
                  <button onClick={() => customerDelete(item.customerId)}>삭제</button>
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
    <CustomerModal
        open={open}
        setOpen={setOpen}
        onSave={handleSaveOrUpdate}
        data={selectedCustomer}
    />
    </div>
  );
}

export default ItemManage;