import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { GroupMembers, GroupMembersService } from './group-members.service';
import { User, UserService } from '../users/user.service';
import { SharedDataService } from '../shared/shared-data.service';
import { PopupComponent } from '../shared/popup/popup.component';

@Component({
  selector: 'app-groups',
  standalone: true,
  imports: [CommonModule, FormsModule, PopupComponent],
  templateUrl: './group-members.component.html',
  styleUrls: ['./group-members.component.css'],
})
export class GroupMembersComponent implements OnInit {
  groupMembers: GroupMembers[] = [];
  groupId!: number;
  groupName!: string;

  userId: number = 0;
  isOwner: boolean = false;
  isMember: boolean = false;

  isMemberModalOpen = false;

  form1: GroupMembers = { memberId: 0, memberName: '', memberType: '', addedOn: '' };
  form: User = { userId: 0, username: '' };

  showPopup = false;
  popupTitle = '';
  popupMessage = '';
  samePage!: boolean;

  constructor(
    private groupMembersService: GroupMembersService,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private userService: UserService,
    private authService: AuthService,
    private sharedDataService: SharedDataService
  ) {}

  ngOnInit() {
    this.userId = Number(this.authService.getUserId());

    this.activeRoute.queryParams.subscribe((params) => {
      this.groupName = params['groupName'];
      this.groupId = params['groupId'];
    });
    this.loadGroupMembers();
  }

  loadGroupMembers() {
    this.groupMembersService.getGroupMembers(this.groupId).subscribe((response) => {
      this.groupMembers = response.members;

      const me = this.groupMembers.find((m) => m.memberId === this.userId);

      if (me) {
        this.isOwner = me.memberType === 'OWNER';
        this.isMember = me.memberType === 'MEMBER';
      } else {
        this.isOwner = false;
        this.isMember = false;
      }
    });
  }

  openAddMemberModal() {
    this.form = { userId: 0, username: '' };
    this.isMemberModalOpen = true;
  }

  openAddExpensePage() {
    this.sharedDataService.setMembers(this.groupMembers);

    this.router.navigate(['/app/add-expense'], {
      queryParams: { groupId: this.groupId, groupName: this.groupName },
    });
  }

  saveGroupMember() {
    this.userService.searchUserByParam(this.form).subscribe((rsp) => {
      if (rsp.statusCode === 100) {
        this.form1 = { groupId: this.groupId, memberId: rsp.users[0].userId };
        this.groupMembersService.createGroupMember(this.form1).subscribe((response) => {
          if (response.statusCode === 100) {
            this.showMessage('Success', response.message);
            this.loadGroupMembers();
          } else {
            this.showMessage('Error', response.message);
          }
        });
      } else {
        this.showMessage('Error', rsp.message);
      }
    });
    this.closeModal();
  }

  deleteGroupMember(index: number) {
    this.form1 = { ...this.groupMembers[index] };

    this.groupMembersService
      .deleteGroupMember(this.groupId, this.form1.memberId)
      .subscribe((rsp) => {
        if (rsp.statusCode === 100) {
          this.showMessage('Success', rsp.message);
          this.loadGroupMembers();
        } else {
          this.showMessage('Error', rsp.message);
        }
      });
  }

  exitGroup() {
    this.groupMembersService.deleteGroupMember(this.groupId, this.userId).subscribe((rsp) => {
      if (rsp.statusCode === 100) {
        this.showMessageAndBack('Success', 'You are out!!');
        this.loadGroupMembers();
      } else {
        this.showMessage('Error', rsp.message);
      }
    });
  }

  viewExpense(index: number) {
    this.form1 = { ...this.groupMembers[index] };

    this.router.navigate(['/app/view-expense'], {
      queryParams: {
        groupId: this.groupId,
        groupName: this.groupName,
        memberId: this.form1.memberId,
        memberName: this.form1.memberName,
      },
    });
  }

  closeModal() {
    this.isMemberModalOpen = false;
  }

  showMessage(title: string, message: string) {
    this.popupTitle = title;
    this.popupMessage = message;
    this.showPopup = true;
    this.samePage = true;
  }

  showMessageAndBack(title: string, message: string) {
    this.popupTitle = title;
    this.popupMessage = message;
    this.showPopup = true;
    this.samePage = false;
  }

  viewSettlement() {
    this.router.navigate(['/app/settlements'], {
      queryParams: {
        userId: this.userId,
        groupId: this.groupId,
        groupName: this.groupName,
      },
    });
  }

  previousPage() {
    this.router.navigate(['/app/groups']);
  }
}
