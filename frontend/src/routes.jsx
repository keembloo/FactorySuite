// routes.jsx
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Login from './pages/common/Login';
import NotFound from './pages/common/NotFound';

// 관리자
import AdminLayout from './pages/admin/AdminLayout';
import AdminDashboard from './pages/admin/Dashboard';
import UserManagement from './pages/admin/UserManagement';
import CustomerManagement from './pages/admin/CustomerManagement';

// 작업자
import WorkerLayout from './pages/worker/WorkerLayout';
import WorkerDashboard from './pages/worker/Dashboard';
import WorkOrderList from './pages/worker/WorkOrderList';
import WorkExecution from './pages/worker/WorkExecution';

export default function AppRoutes() {
  return (
    <Routes>
      {/* 로그인 */}
      <Route path="/login" element={<Login />} />

      {/* 관리자 페이지 */}
      <Route path="/admin/*" element={<AdminLayout />}>
        <Route index element={<AdminDashboard />} />
        <Route path="users" element={<UserManagement />} />
        <Route path="customer" element={<CustomerManagement />} />
        <Route path="product" element={<Product />} />
        <Route path="order" element={<Order />} />
        <Route path="process" element={<Process />} />
        {/* 필요시 추가 페이지 */}
      </Route>

      {/* 작업자 페이지 */}
      <Route path="/worker/*" element={<WorkerLayout />}>
        <Route index element={<WorkerDashboard />} />
        <Route path="work-orders" element={<WorkOrderList />} />
        <Route path="execute/:id" element={<WorkExecution />} />
        {/* 필요시 추가 페이지 */}
      </Route>

      {/* 404 NotFound */}
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}