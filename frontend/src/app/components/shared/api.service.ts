import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ApiService{//} implements OnInit{
  userId:number=0;

  constructor(private http: HttpClient, private authService: AuthService) {}
  // ngOnInit(): void {
  //   this.userId = Number(this.authService.getUserId());
  // }



  post(url: string, body: any = {}) {
    // const userId = this.authService.getUserId();
    this.userId = Number(this.authService.getUserId());
    return this.http.post(url,
      { userId:this.userId, ...body },
      { withCredentials: true }
    );
  }

  get(url: string) {
    this.userId = Number(this.authService.getUserId());
    return this.http.get(`${url}?userId=${this.userId}`,
      { withCredentials: true }
    );
  }
  getWithParam(url: string) {
    // const userId = this.authService.getUserId();
    this.userId = Number(this.authService.getUserId());
    return this.http.get(`${url}&userId=${this.userId}`,
      { withCredentials: true }
    );
  }

  put(url: string, body: any = {}) {
    // const userId = this.authService.getUserId();
    this.userId = Number(this.authService.getUserId());
    return this.http.put(url,
      { ...body, userId:this.userId },
      { withCredentials: true }
    );
  }

  delete(url: string) {
    // const userId = this.authService.getUserId();
    this.userId = Number(this.authService.getUserId());
    return this.http.delete(`${url}&userId=${this.userId}`,
      { withCredentials: true }
    );
  }
}
