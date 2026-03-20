import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import ItemManage from './pages/ItemManage';
import Customer from './pages/Customer';
import Header from './components/Header';
import './main.css';

ReactDOM.createRoot(document.getElementById('root')).render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<Login />} />

            {/* 로그인 이후 페이지 — 헤더 포함 */}
            <Route path="/*" element={
                <>
                    <Header />
                    <Routes>
                        <Route path="items" element={<ItemManage />} />
                        <Route path="customer" element={<Customer />} />
                    </Routes>
                </>
            }
            />
        </Routes>
    </BrowserRouter>
);