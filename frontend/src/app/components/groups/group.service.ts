import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiService } from '../shared/api.service';

export interface Group {
  userId?: number;
  groupId: number;
  groupName: string;
  createdBy?: string;
  createdOn?: any;
  editMode?: boolean;
}

@Injectable({ providedIn: 'root' })
export class GroupService {
  private apiUrl = `${environment.apiUrl}/api/groups`;

  constructor(private http: HttpClient, private api: ApiService) {}

  getGroups(): Observable<any> {
    return this.api.get(`${this.apiUrl}/search`);
  }

  // searchGroupsByParam(param: Partial<Group>) {
  //   return this.api.post(`${this.apiUrl}/searchByParam`,
  //     param
  //   );
  // }

  createGroup(group: Group): Observable<any> {
    return this.api.post(`${this.apiUrl}/create`,
      group
    );
  }

  updateGroup(group: Group): Observable<any> {
    return this.api.put(`${this.apiUrl}/update`,
      group
    );
  }

  deleteGroup(id: number): Observable<any> {
    return this.api.delete(`${this.apiUrl}/delete?groupId=${id}`);
  }
}
