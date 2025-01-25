import { Navigate, Route } from "react-router-dom";
import { PrivateRoutes } from "../../models";
import { RouterWithNotFound } from "../../utilities";
import { lazy } from "react";

const Dashboard = lazy(() => import("./Dashboard/Dashboard"));
const Home = lazy(() => import("./Home/Home"));

function Private() {
  return (
    <RouterWithNotFound>
      <Route path="/" element={<Navigate to={PrivateRoutes.DASHBOARD} />} />
      <Route path={PrivateRoutes.DASHBOARD} element={<Dashboard />} />
      <Route path={PrivateRoutes.HOME} element={<Home />} />
      <div>Dasboard NODO NEXUS</div>
    </RouterWithNotFound>
  );
}

export default Private;
