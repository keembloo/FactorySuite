import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './header.css';

function Header(){
    const navigate = useNavigate();   // 🔥 이거 추가

    return (


        <header className="erp-header">
            <div className="header-inner">

                {/* 로고 영역 */}
                <div className="logo">
                FactorySuite ERP
                </div>

                {/* 상단 메뉴 */}
                <nav className="main-menu">
                    <ul>
                        <li onClick={() => navigate('/customer')}> 거래처관리</li>
                        <li onClick={() => navigate('/product')}>제품관리</li>
                        <li onClick={() => navigate('/order')}>주문관리</li>
                        <li onClick={() => navigate('/process')}>공정정의</li>
                        <li>생산지시관리</li>
                        <li>재고관리</li>
                    </ul>
                </nav>

                {/* 사용자 영역 */}
                <div className="user-area">
                    <span className="user-name">관리자</span>
                    <button className="logout-btn">로그아웃</button>
                </div>

            </div>
        </header>
    )
}
export default Header;