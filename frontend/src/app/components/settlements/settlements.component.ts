import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { User } from '../users/user.service';
import { PopupComponent } from '../shared/popup/popup.component';
import { GroupMembers, GroupMembersService } from '../group-members/group-members.service';
import { Settlements, SettlementsService } from './settlements.service';

@Component({
  selector: 'app-groups',
  standalone: true,
  imports: [CommonModule, FormsModule, PopupComponent],
  templateUrl: './settlements.component.html',
  styleUrls: ['./settlements.component.css'],
})
export class SettlementsComponent implements OnInit {
  settlements: Settlements[] = [];
  groupId!: number;
  groupName!: string;
  memberName!: string;
  memberId!: number;
  userId: number = 0;
  toggleView: boolean = false;

  isRejectModalOpen = false;

  form: Settlements = { requestId: 0};

  showPopup = false;
  popupTitle = '';
  popupMessage = '';

  constructor(
    private settlementService: SettlementsService,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.userId = Number(this.authService.getUserId());

    this.activeRoute.queryParams.subscribe((params) => {
      this.groupName = params['groupName'];
      this.groupId = params['groupId'];
      this.memberName = params['memberName'];
      this.memberId = params['memberId'];
    });
    this.loadSettlements();
  }

  loadSettlements() {
    const toggleView = this.toggleView ? 1 : 0;

    this.settlementService
      .getSettlements(this.groupId, this.memberId, toggleView)
      .subscribe((response) => {
        this.settlements = response.settlement;
        if (response.statusCode === 101) {
          this.showMessage('Sucess', response.message);
        }
      });
  }

  toggleSettlementsView() {
    this.toggleView = !this.toggleView;
    this.loadSettlements();
  }

  approveSettlement(index: number) {
    this.form = { ...this.settlements[index] };
    const payload = {
      groupId: this.groupId,
      memberId: this.memberId,
      requestId: this.form.requestId,
    };

    this.settlementService.approveSetttlement(payload).subscribe((rsp) => {
      if (rsp.statusCode === 100) {
        this.showMessage('Success', rsp.message);
        this.loadSettlements();
      } else {
        this.showMessage('Error', rsp.message);
      }
    });
  }

  openRejectModal(index: number) {
    this.form = { ...this.settlements[index] };
    this.isRejectModalOpen = true;
  }

  rejectSettlement(index: number) {
    this.form = { ...this.settlements[index] };
    const payload = {
      groupId: this.groupId,
      memberId: this.memberId,
      requestId: this.form.requestId,
    };

    this.settlementService.rejectSetttlement(payload).subscribe((rsp) => {
      this.showMessage('Success', rsp.message);
      this.loadSettlements();
    });
  }

  closeModal() {
    this.isRejectModalOpen = false;
  }

  previousPage() {
    this.router.navigate(['/app/group-members'], {
      queryParams: { groupName: this.groupName, groupId: this.groupId },
    });
  }

  showMessage(title: string, message: string) {
    this.popupTitle = title;
    this.popupMessage = message;
    this.showPopup = true;
  }
}
