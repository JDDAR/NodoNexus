import { RootState } from "../store";

export const selectUserRole = (state: RootState) => state.user.role;
export const selectUserName = (state: RootState) => state.user.userName;
