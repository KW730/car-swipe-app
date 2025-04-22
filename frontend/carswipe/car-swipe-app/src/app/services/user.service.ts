import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, catchError, Observable, tap} from 'rxjs';
import {User} from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = "http://localhost:8080/api/user"
  private userSub = new BehaviorSubject<User | null>(null);
  private isLoggedInSub = new BehaviorSubject<boolean>(this.checkIfLoggedIn());
  public isLoggedIn$ = this.isLoggedInSub.asObservable();

  constructor(private http: HttpClient) {}

  private checkIfLoggedIn(): boolean{
    return !!sessionStorage.getItem('username');
  }

  isLoggedIn(): boolean{
    return this.checkIfLoggedIn();
  }

  getUserId(): number | null {
    const id = sessionStorage.getItem('userId');
    return id ? parseInt(id, 10) : null;
  }

  getUsername(): string | null{
    return sessionStorage.getItem('username')
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  register(username: string, email: string, password:string): Observable<User>{
    return this.http.post<User>(`${this.apiUrl}/register`, {username, email, password}).pipe(
      tap(user => {
        this.userSub.next(user);
        localStorage.setItem('user', JSON.stringify(user));
      })
    );
  }

  login(email: string, password: string): Observable<any>{
    const payload = {
      email: email,
      password: password
    };
    return this.http.post(`${this.apiUrl}/login`, payload).pipe(
      tap((res: any)=> {
        sessionStorage.setItem('token', res.token || res);
        sessionStorage.setItem('username', res.username);
        sessionStorage.setItem('userId', res.userId);
        this.isLoggedInSub.next(true);
      })
    );
  }

  logout(): Observable<any>{
    return this.http.get(`${this.apiUrl}/logout`).pipe(
      tap(() => {
        sessionStorage.clear();
        this.isLoggedInSub.next(false);
      })
    );
  }



}
