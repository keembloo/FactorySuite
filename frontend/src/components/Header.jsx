import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './header.css';

function Header(){


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
                        <li>거래처관리</li>
                        <li>제품관리</li>
                        <li>주문관리</li>
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