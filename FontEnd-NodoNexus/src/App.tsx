import { BrowserRouter, Navigate, Route } from "react-router-dom";
import "./App.css";
import { PrivateRoutes, PublicRoutes } from "./models";
import { AuthGuard } from "./guards";
import { RouterWithNotFound } from "./utilities";
import { lazy, Suspense } from "react";
import { Provider } from "react-redux";
import store from "./redux/store";

const Login = lazy(() => import("./pages/Login/Login"));
const Private = lazy(() => import("./pages/Private/Private"));

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
                  element={<Navigate to={PrivateRoutes.PRIVATE} />}
                />
                <Route path={PublicRoutes.LOGIN} element={<Login />} />
                <Route element={<AuthGuard />}>
                  <Route
                    path={`${PrivateRoutes.PRIVATE}/*`}
                    element={<Private />}
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
