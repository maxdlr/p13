import { inject, Injectable } from '@angular/core';
import { Observable } from '@apollo/client/utilities';
import { BehaviorSubject, Subject } from 'rxjs';
import { UserInfo, UserInput } from '../interface/user.interface';
import { Apollo } from 'apollo-angular';
import { LoggerService } from './logger.service';
import { FIXTURES } from '../gql-requests/Fixtures';
import { FixturesResponse } from '../interface/responses.interface';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private currentUserSubject: Subject<UserInfo> = new Subject<UserInfo>();
  private currentUser$: Observable<UserInfo> =
    this.currentUserSubject.asObservable();
  private apollo: Apollo = inject(Apollo);
  private userList: Map<number, UserInfo> = new Map<number, UserInfo>();

  private loadUserFixtures() {
    this.apollo
      .mutate<FixturesResponse>({
        mutation: FIXTURES,
        variables: {
          user: {} as UserInput,
          admin: {} as UserInput,
        },
      })
      .subscribe({
        next: (result) => {
          const user: UserInfo | undefined = result.data?.CreateUser;
          const admin: UserInfo | undefined = result.data?.CreateAdmin;
          if (user && admin) {
            this.currentUserSubject.next(user);
            this.userList.set(user.id, user);
            this.userList.set(admin.id, admin);
          }
        },
        error: (error) => LoggerService.error(error),
      });
  }

  public setCurrentUserAsUser() {
    this.setUser('USER');
  }

  public setCurrentUserAsAdmin() {
    this.setUser('ADMIN');
  }

  private setUser(roleName: string) {
    const foundUser: UserInfo = Object.values(this.userList.values).find(
      (user: UserInfo) => user.role.name === roleName,
    );
    this.currentUserSubject.next(foundUser);
  }
}
