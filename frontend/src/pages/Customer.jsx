import { useState, useEffect } from 'react';
import { getCustomer } from '../api/customerApi';
import './Customer.css';
import './Page.css';

function Customer() {

    const [key, setKey] = useState('customerName');
    const [keyword, setKeyword] = useState('');
    const [list, setList] = useState([]);

    // 페이징처리 위한 페이지 번호 상태변수 전달할 객체
    const [ pageInfo , setPageInfo ] = useState({
        page : 1 , key : 'customerName' , keyword : '' , view : 10 // view 초기값 10
    });

    // 조회 함수
    const fetchCustomer = () => {
        getCustomer(pageInfo.page, pageInfo.key, pageInfo.keyword, pageInfo.view)
        .then(res =>
            {console.log(res.data.customerDtos); setList(res.data.customerDtos);})
        .catch(err => console.error(err));
    };

    // 처음 전체조회
    useEffect(() => {
        fetchCustomer();
    }, [pageInfo.page, pageInfo.key, pageInfo.keyword, pageInfo.view]);


    // 검색
    const handleSearch = (e) => {
        e.preventDefault();
        setPageInfo(prev => ({
            ...prev,
            page: 1,
            key: key,
            keyword: keyword
        }));
    };

  return (
    <div className="bodyContainer">

      <div className="page-header">
        <h2>거래처 관리</h2>
      </div>

      {/* 검색 영역 */}
      <div className="search-panel">
        <form onSubmit={handleSearch}>
          <select
            name="key"
            value={key}
            onChange={e => setKey(e.target.value)}
          >
            <option value="customerName">거래처명</option>
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
            {Array.isArray(list) && list.map((item, idx) => (
              <tr key={item.customerId}>
                <td>{idx + 1}</td>
                <td>{item.customerName}</td>
                <td>{item.phone}</td>
                <td>{item.address}</td>
                <td>
                  <button>수정</button>
                  <button>삭제</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

    </div>
  );
}

export default Customer;