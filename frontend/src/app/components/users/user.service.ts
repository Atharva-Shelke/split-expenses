import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiService } from '../shared/api.service';

export interface User {
  userId?: number;
  username?: string;
  password?: string;
  mobileNo?: string;
  email?: string;
  editMode?: boolean;
  oldPassword?: string;
  newPassword?: string;
}

@Injectable({ providedIn: 'root' })
export class UserService {
  private apiUrl = `${environment.apiUrl}/api/users`;

  constructor(private http: HttpClient, private api: ApiService) {}

  getUsers(): Observable<{ users: User[]; message: string; statusCode: number }> {
    return this.http.get<{ users: User[]; message: string; statusCode: number }>(
      `${this.apiUrl}/search`,
      { withCredentials: true }
    );
  }

  searchUserByParam(param: Partial<User>): Observable<any> {
      return this.api.post(`${this.apiUrl}/searchByParam`,
        param
      );
    }

  createUser(user: User): Observable<any> {
    return this.http.post(`${this.apiUrl}/create`, user);
  }

  updatePassword(user: User): Observable<any> {
    return this.http.put(`${this.apiUrl}/updatePassword`, user);
  }
  
  updateUser(user: User): Observable<any> {
    return this.http.put(`${this.apiUrl}/update`, user, { withCredentials: true });
  }

  deleteUser(id: number) {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, { withCredentials: true });
  }
}
