import { Navigate, Route } from "react-router-dom";
import { lazy } from "react";
import { RouterWithNotFound } from "../utilities";
import { PrivateRoutes } from "./routes";
import { RoleGuard } from "../components/Auth";

const Home = lazy(() => import("../pages/Home/Home"));
const Dashboard = lazy(() => import("../pages/Dashboard/Dashboard"));
const AdminDashboard = lazy(() => import("../pages/Dashboard/AdminDashboard"));
const ClientDashboard = lazy(
  () => import("../pages/Dashboard/ClientDashboard"),
);

function Private() {
  return (
    <RouterWithNotFound>
      <Route path="/" element={<Navigate to={PrivateRoutes.DASHBOARD} />} />
      <Route path={PrivateRoutes.DASHBOARD} element={<Dashboard />} />
      <Route path={PrivateRoutes.HOME} element={<Home />} />
      <Route
        path={PrivateRoutes.ADMINDASBOARD}
        element={
          <RoleGuard rol="ADMIN">
            <AdminDashboard />
          </RoleGuard>
        }
      />
      <Route
        path={PrivateRoutes.CLIENTDASHBOARD}
        element={
          <RoleGuard rol="CLIENT">
            <ClientDashboard />
          </RoleGuard>
        }
      />
    </RouterWithNotFound>
  );
}

export default Private;
