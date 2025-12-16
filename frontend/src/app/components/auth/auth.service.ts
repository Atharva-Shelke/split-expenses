import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  private currentUser = new BehaviorSubject<Number | null>(null);

  constructor(private http: HttpClient) {}

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials, { withCredentials: true });
  }

  checkSession(): Observable<any> {
    return this.http.get(`${this.apiUrl}/session`, { withCredentials: true });
  }

  logout(): Observable<any> {
    return this.http.post(`${this.apiUrl}/logout`, {}, { withCredentials: true });
  }

  setUser(user: any) {
    localStorage.setItem('userId', user.userId);
    localStorage.setItem('username', user.username);
  }

  getUserId() {
    return localStorage.getItem('userId');
  }

  getUsername() {
    return localStorage.getItem('username');
  }
  clearUser() {
    this.currentUser.next(null);
  }

  isLoggedIn(): Observable<boolean> {
    return this.currentUser.asObservable().pipe(map((u) => !!u));
  }
}
