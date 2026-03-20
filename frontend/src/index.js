// routes.jsx
import { Routes, Route } from "react-router-dom";
import Login from "./pages/common/Login";
import AdminLayout from "./pages/admin/AdminLayout";
import WorkerLayout from "./pages/worker/WorkerLayout";
import NotFound from "./pages/common/NotFound";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/admin/*" element={<AdminLayout />} />
      <Route path="/worker/*" element={<WorkerLayout />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}