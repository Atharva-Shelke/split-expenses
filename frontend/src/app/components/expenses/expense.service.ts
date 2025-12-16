import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiService } from '../shared/api.service';

export interface Expense {
  expenseId: number;
  description?: string;
  amount?: number;
  memberIds?: any;
  userId?: number;
  groupId?: number;
  createdBy?: string;
  createdOn?: any;
  editMode?: boolean;
  memberNames?: string;
  splitAmount?: number;
}

@Injectable({ providedIn: 'root' })
export class ExpenseService {
  private apiUrl = `${environment.apiUrl}/api/expense`;

  constructor(private http: HttpClient, private api: ApiService) {}

  getExpense(groupId: Number, memberId: Number, toggleView: Number): Observable<any> {
    return this.api.getWithParam(
      `${this.apiUrl}/search?groupId=${groupId}&memberId=${memberId}&toggleView=${toggleView}`
    );
  }

  createExpense(payload: {
    groupId: number;
    description: string;
    amount: number;
    memberIds: any[];
  }): Observable<any> {
    return this.api.post(`${this.apiUrl}/create`, payload);
  }

  deleteExpense(id: number): Observable<any> {
    return this.api.delete(`${this.apiUrl}/delete?expenseId=${id}`);
  }

  getSplit(groupId: Number, memberId: Number): Observable<any> {
    return this.api.getWithParam(
      `${this.apiUrl}Split/search?groupId=${groupId}&memberId=${memberId}`
    );
  }
}
