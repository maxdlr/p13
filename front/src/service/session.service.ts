import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, Subscription } from 'rxjs';
import { UserInfo } from '../interface/user.interface';
import { HttpService } from './http.service';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  public currentUserSubject: BehaviorSubject<UserInfo> =
    new BehaviorSubject<UserInfo>({} as UserInfo);
  public currentUser$ = this.currentUserSubject.asObservable();
  private userList: Map<number, UserInfo> = new Map<number, UserInfo>();
  private http: HttpService = inject(HttpService);

  public loadFixtures(onLoaded?: () => void): Subscription {
    return this.http.loadFixtures().subscribe((users: UserInfo[]) => {
      users.forEach((user: UserInfo) => {
        this.userList.set(user.id, user);
      });
      this.setCurrentUserAsUser();
      if (onLoaded) onLoaded();
    });
  }

  public get isAdmin() {
    if (!this.currentUserSubject.value || !this.currentUserSubject.value.role) {
      return false;
    }

    return this.currentUserSubject.value.role.name === 'ADMIN';
  }

  public setCurrentUserAsUser() {
    this.setUser('USER');
  }

  public setCurrentUserAsAdmin() {
    this.setUser('ADMIN');
  }

  private setUser(roleName: string) {
    const foundUser: UserInfo | undefined = Array.from(
      this.userList.values(),
    ).find((user: UserInfo) => {
      return user.role.name === roleName;
    });
    if (!foundUser) {
      throw new Error('Cannot find user to set from userList');
    }
    this.currentUserSubject.next(foundUser);
  }
}
