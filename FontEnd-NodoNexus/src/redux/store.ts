import { configureStore } from "@reduxjs/toolkit";
import userReducer, {
  initialState as userInitialState,
} from "./states/userSlice";
import { UserInfo } from "../models";

export interface AppStore {
  user: UserInfo;
}

const persistedUser = localStorage.getItem("token")
  ? { ...userInitialState, token: localStorage.getItem("token") as string }
  : userInitialState;

const store = configureStore<AppStore>({
  reducer: {
    user: userReducer,
  },
  preloadedState: {
    user: persistedUser,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
