import React from 'react';
import { useState, useEffect } from 'react';
import { createProcess , getProcess , putProcess , deleteProcess ,getProcessList} from '../../api/processApi';
import './Process.css';
import '../Page.css';
import Pagination from '@mui/material/Pagination';
import useStore from '../../store/useStore';
import ProcessModal from './ProcessModal'

function Process(){
    const [key, setKey] = useState('');
    const [keyword, setKeyword] = useState('');
    const [list, setList] = useState([]);
    const [open, setOpen] = useState(false);
    const count = useStore(s => s.count);
    const increase = useStore(s => s.increase);
    const [selectedProcess, setSelectedProcess] = useState(null);
    const [inspectionYn, setInspectionYn] = useState('');   // 검수여부 Y, N
    const [useYn, setUseYn] = useState(''); // 사용여부
    // 페이징처리 위한 상태변수 전달받은객체
    let [ pageDto , setPageDto ] = useState({
        processDtos : [] ,
        totalPage : 0 ,
        totalCount : 0
    });

    // 페이징처리 위한 페이지 번호 상태변수 전달할 객체
    const [ pageInfo , setPageInfo ] = useState({
        page : 1 , key : '' , keyword : '' , view : 10 // view 초기값 10
    });

    // 처음 전체조회
    useEffect(() => {
        fetchProcess();
    }, [pageInfo.page, pageInfo.key, pageInfo.keyword, pageInfo.view]);


    // 검색
    const processSearch = (e) => {
        e.preventDefault();
        setPageInfo(prev => ({
            ...prev,
            page: 1,
            key: key ,
            keyword: keyword ,
            view: view
        }));
    };

    // 조회 함수
    const fetchProcess = () => {
        console.log("pageInfo:", pageInfo);
        getProcess(pageInfo.page, pageInfo.key, pageInfo.keyword, pageInfo.view )
        .then(res =>
            {console.log(res.data); setPageDto(res.data);})
        .catch(err => console.error(err));
    };

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
    const processDelete = (productId)=>{
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

    return(
         <div className="bodyContainer">

                <div className="page-header">
                    <h2>공정정의</h2>
                </div>

               {/* 검색 영역 */}
                <div className="search-panel">
                    <form onSubmit={processSearch}>
                        <select
                            name="key"
                            value={key}
                            onChange={e => setKey(e.target.value)}
                        >
                            <option value="processName">공정명</option>
                            <option value="processCode">공정코드</option>
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
                    <button onClick={handleOpenRegister}>공정 등록</button>
                </div>

                 {/* 테이블 */}
                <div className="table-panel">
                    <table>
                        <thead>
                            <tr>
                                <th>번호</th>
                                <th>공정코드</th>
                                <th>공정명</th>
                                <th>검사여부</th>
                                <th>사용여부</th>
                                <th>비고</th>
                            </tr>
                        </thead>

                        <tbody>
                        {pageDto.processDtos && pageDto?.processDtos?.map((item, idx) => (
                            <tr key={item.processId}>
                                <td>{item.processId}</td>
                                <td>{item.processName}</td>
                                <td>{item.processCode}</td>
                                <td>{item.inspectionYn}</td>
                                <td>{item.useYn}</td>
                                <td>
                                    <button onClick={() => handleOpenUpdate(item)}>사용전환</button>
                                    <button onClick={() => processDelete(item.processId)}>삭제</button>
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

                <ProcessModal
                    open={open}
                    setOpen={setOpen}
                    onSave={handleSaveOrUpdate}
                    data={selectedProcess}
                />
            </div>

    );

}

export default Process;