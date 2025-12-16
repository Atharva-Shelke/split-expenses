import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiService } from '../shared/api.service';

export interface Settlements {
  requestId: number; //
  // remark?: string;
  // expenseId?: number;
  // description?: string;
  amount?: number; //
  // memberIds?: any;
  // userId?: number;
  // groupId?: number;
  // createdBy?: string;
  createdOn?: any; //
  // editMode?: boolean;
  // memberNames?: string;
  // splitAmount?: number;
  fromUsername?: string; //
  toUsername?: string; //
  statusCode?: number; //
  statusDescription?: string; //
  modifiedOn?: any; //
}

@Injectable({ providedIn: 'root' })
export class SettlementsService {
  private apiUrl = `${environment.apiUrl}/api/settlement`;

  constructor(private http: HttpClient, private api: ApiService) {}

  getSettlements(groupId: Number, memberId: Number, toggleView: Number): Observable<any> {
    return this.api.getWithParam(
      `${this.apiUrl}/search?groupId=${groupId}&memberId=${memberId}&toggleView=${toggleView}`
    );
  }

  createSettlement(payload: {
    groupId: number;
    memberId: number;
    amount: number;
  }): Observable<any> {
    return this.api.post(`${this.apiUrl}/create`, payload);
  }

  approveSetttlement(payload: {
    groupId: number;
    memberId: number;
    requestId: number;
  }): Observable<any> {
    return this.api.put(`${this.apiUrl}/approve`, payload);
  }

  rejectSetttlement(payload: {
    groupId: number;
    memberId: number;
    requestId: number;
  }): Observable<any> {
    return this.api.put(`${this.apiUrl}/reject`, payload);
  }

  //   deleteExpense(id: number): Observable<any> {
  //     return this.api.delete(`${this.apiUrl}/delete?expenseId=${id}`);
  //   }

  //   getSplit(groupId: Number, memberId: Number): Observable<any> {
  //   return this.api.getWithParam(`${this.apiUrl}Split/search?groupId=${groupId}&memberId=${memberId}`);
  // }
}
