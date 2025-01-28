import { BrowserRouter, Navigate, Route } from "react-router-dom";
import "./App.css";
import { RouterWithNotFound } from "./utilities";
import { lazy, Suspense } from "react";
import { Provider } from "react-redux";
import store from "./store/store";
import AuthGuard from "./components/Auth/auth.guard";
import AdminDashboard from "./pages/Dashboard/AdminDashboard.tsx";
import { PrivateRoutes, PublicRoutes } from "./router/routes.ts";
import ClientDashboard from "./pages/Dashboard/ClientDashboard.tsx";

const Login = lazy(() => import("./pages/Auth/Login"));
const Private = lazy(() => import("./router/Private.tsx"));

function App() {
  return (
    <>
      <div className="App">
        <Suspense fallback={<>CARGANDO APP NODOSNEXUS</>}>
          <Provider store={store}>
            <BrowserRouter>
              <RouterWithNotFound>
                <Route
                  path="/"
                  element={<Navigate to={PublicRoutes.LOGIN} />}
                />
                <Route path={PublicRoutes.LOGIN} element={<Login />} />
                <Route element={<AuthGuard privateValidation={true} />}>
                  <Route
                    path={`${PrivateRoutes.PRIVATE}/*`}
                    element={<Private />}
                  />
                  <Route
                    path={PrivateRoutes.ADMINDASBOARD}
                    element={<AdminDashboard />}
                  />
                  <Route
                    path={PrivateRoutes.CLIENTDASHBOARD}
                    element={<ClientDashboard />}
                  />
                </Route>
              </RouterWithNotFound>
            </BrowserRouter>
          </Provider>
        </Suspense>
      </div>
    </>
  );
}

export default App;
