import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedDataService } from '../../shared/shared-data.service';
import { ExpenseService } from '../expense.service';
import { PopupComponent } from '../../shared/popup/popup.component';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-add-expense',
  imports: [CommonModule, FormsModule, PopupComponent],
  templateUrl: './add-expense.component.html',
  styleUrls: ['../expense.component.css'],
})
export class AddExpenseComponent implements OnInit {
  groupId!: number;
  userId!: number;
  members: any[] = [];
  description: string = '';
  amount: number = 0;

  selectAll: boolean = false;

  groupName: any;

  showPopup = false;
  popupTitle = '';
  popupMessage = '';
  samePage = false;

  constructor(
    private sharedDataService: SharedDataService,
    private router: Router,
    private route: ActivatedRoute,
    private expenseService: ExpenseService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.userId = Number(this.authService.getUserId());

    this.route.queryParams.subscribe((params) => {
      this.groupName = params['groupName'];
      this.groupId = params['groupId'];
    });

    this.members = this.sharedDataService.getMembers().map((m) => ({
      ...m,
      selected: m.memberId === this.userId,
    }));
  }

  toggleSelectAll() {
    this.members.forEach((m) => {
      if (m.memberId !== this.userId) {
        m.selected = this.selectAll;
      }
    });
  }

  get areMembersSelected(): boolean {
    return this.members.some((m) => m.selected);
  }

  saveExpense() {
    const selectedMembers = this.members.filter((m) => m.selected).map((m) => m.memberId);

    const payload = {
      groupId: this.groupId,
      description: this.description,
      amount: this.amount,
      memberIds: selectedMembers,
    };

    this.expenseService.createExpense(payload).subscribe((rsp) => {
      this.showMessage('Success', rsp.message);
    });
  }

  cancel() {
    this.router.navigate(['/app/group-members'], {
      queryParams: { groupId: this.groupId, groupName: this.groupName },
    });
  }

  showMessage(title: string, message: string) {
    this.popupTitle = title;
    this.popupMessage = message;
    this.showPopup = true;
    this.samePage = false;
  }
}
