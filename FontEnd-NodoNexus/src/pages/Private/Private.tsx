import { Navigate, Route } from "react-router-dom";
import { PrivateRoutes } from "../../models";
import { RouterWithNotFound } from "../../utilities";
import { lazy } from "react";
import { AuthGuard } from "../../guards";

const Dashboard = lazy(() => import("./Dashboard/Dashboard"));
const Home = lazy(() => import("./Home/Home"));

function Private() {
  return (
    <RouterWithNotFound>
      <Route path="/" element={<Navigate to={PrivateRoutes.DASHBOARD} />} />
      <Route element={<AuthGuard />}>
        <Route path={PrivateRoutes.DASHBOARD} element={<Dashboard />} />
        <Route path={PrivateRoutes.HOME} element={<Home />} />
      </Route>
      <div>Dasboard NODO NEXUS</div>
    </RouterWithNotFound>
  );
}

export default Private;
