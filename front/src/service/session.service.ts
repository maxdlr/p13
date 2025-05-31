import { inject, Injectable } from '@angular/core';
import { Observable } from '@apollo/client/utilities';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';
import { UserInfo, UserInput } from '../interface/user.interface';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environments';
import { LoggerService } from './logger.service';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private currentUserSubject: Subject<UserInfo> = new BehaviorSubject<UserInfo>(
    {} as UserInfo,
  );
  public currentUser$ = this.currentUserSubject.asObservable();
  private userList: Map<number, UserInfo> = new Map<number, UserInfo>();
  private http: HttpClient = inject(HttpClient);

  public loadUserFixtures(onLoaded: () => void): Subscription {
    return this.http
      .get<UserInfo[]>(environment.apiUrl + 'fixtures/load', {
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      })
      .subscribe((users: UserInfo[]) => {
        users.forEach((user: UserInfo) => {
          this.userList.set(user.id, user);
        });
        this.setCurrentUserAsUser();
        if (onLoaded) {
          onLoaded();
        }
      });
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
