import { RoleInfo } from './role.interface';

export interface UserInfo {
  id: number;
  email: string;
  firstname: string;
  lastname: string;
  phoneNumber: string;
  isActive: boolean;
  role: RoleInfo;
}

export interface UserInput {
  email: string;
  password: string;
  firstname: string;
  lastname: string;
  phoneNumber: string;
  role: string;
}
