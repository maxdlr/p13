import { UserInfo } from '../interface/user.interface';

export class UtilService {
  public static getInitials(user: UserInfo): string {
    const initials: string = user.firstname.charAt(0) + user.lastname.charAt(0);
    return initials.toUpperCase();
  }

  public static upperFirst(str: string): string {
    if (!str) {
      return '';
    }
    return str.charAt(0).toUpperCase() + str.substring(1);
  }
}
