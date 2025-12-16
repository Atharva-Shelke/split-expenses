import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiService } from '../shared/api.service';


export interface GroupMembers {
  userId?: number;
  groupId?: number;
  memberId: number;
  groupName?: string;
  memberName?: string;
  memberType?: string;
  addedOn?: any;
}

@Injectable({ providedIn: 'root' })
export class GroupMembersService {
  private apiUrl = `${environment.apiUrl}/api/groupMembers`;

  constructor(private http: HttpClient, private api: ApiService) {}

  getGroupMembers(groupId: Number): Observable<any> {
    return this.api.getWithParam(`${this.apiUrl}/search?groupId=${groupId}`);
  }

  // searchGroupMemberByParam(param: Partial<GroupMembers>): Observable<any> {
  //   return this.api.post(`${this.apiUrl}/searchByParam`,
  //     param
  //   );
  // }

  createGroupMember(groupMembers: GroupMembers): Observable<any> {
    return this.api.post(`${this.apiUrl}/create`,
      groupMembers
    );
  }

  // updateGroupMember(groupMembers: GroupMembers): Observable<any> {
  //   return this.api.put(`${this.apiUrl}/update`,
  //     groupMembers
  //   );
  // }

  deleteGroupMember(groupId: number, memberId: number): Observable<any> {
    return this.api.delete(`${this.apiUrl}/delete?groupId=${groupId}&memberId=${memberId}`);
  }
}
