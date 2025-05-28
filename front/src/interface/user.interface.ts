import { RoleInfo } from './role.interface';

export interface UserInfo {
  id: string;
  email: string;
  firstname: string;
  lastname: string;
  phoneNumber: string;
  isActive: boolean;
  role: RoleInfo;
}
