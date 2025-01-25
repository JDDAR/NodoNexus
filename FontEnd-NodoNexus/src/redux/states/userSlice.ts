import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { UserInfo } from "../../models";

export const initialState: UserInfo = {
  id: 0,
  userName: "",
  role: "",
  token: "",
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setUser: (state, action: PayloadAction<UserInfo>) => action.payload,
    resetUser: () => initialState,
  },
});

export const { setUser, resetUser } = userSlice.actions;
export default userSlice.reducer;
