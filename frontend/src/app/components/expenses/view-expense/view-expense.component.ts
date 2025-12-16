import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { User } from '../../users/user.service';
import { PopupComponent } from '../../shared/popup/popup.component';
import { GroupMembers, GroupMembersService } from '../../group-members/group-members.service';
import { Expense, ExpenseService } from '../expense.service';
import { Split } from '../../../interfaces/split';
import { SettlementsService } from '../../settlements/settlements.service';

@Component({
  selector: 'app-groups',
  standalone: true,
  imports: [CommonModule, FormsModule, PopupComponent],
  templateUrl: './view-expense.component.html',
  styleUrls: ['./view-expense.component.css'],
})
export class ViewExpenseComponent implements OnInit {
  groupMembers: GroupMembers[] = [];
  expense: Expense[] = [];
  user: User[] = [];

  searchValue: string = '';
  searchField: string = 'memberName';
  groupId!: number;
  groupName!: string;
  memberName!: string;
  memberId!: number;
  userId: number = 0;
  toggleView: boolean = false;

  split!: Split;
  isSettlementModalOpen = false;

  form: User = { userId: 0, username: '' };
  form1: Expense = { expenseId: 0 };

  showPopup = false;
  popupTitle = '';
  popupMessage = '';

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private authService: AuthService,
    private expenseService: ExpenseService,
    private settlementsService: SettlementsService
  ) {}

  ngOnInit() {
    this.userId = Number(this.authService.getUserId());

    this.activeRoute.queryParams.subscribe((params) => {
      this.groupName = params['groupName'];
      this.groupId = params['groupId'];
      this.memberName = params['memberName'];
      this.memberId = params['memberId'];
    });
    this.loadExpense();
  }

  loadExpense() {
    this.searchValue = '';
    const toggleView = this.toggleView ? 0 : 1;

    this.expenseService
      .getExpense(this.groupId, this.memberId, toggleView)
      .subscribe((response) => {
        this.expense = response.expense;
        if (response.statusCode === 101) {
          this.showMessage('Sucess', response.message);
        }
      });
  }

  toggleSplitView() {
    this.toggleView = !this.toggleView;
    this.loadExpense();
  }

  openAddSettlementModal() {
    this.expenseService.getSplit(this.groupId, this.memberId).subscribe((res) => {
      this.split = res.split;
      this.isSettlementModalOpen = true;
    });
  }

  deleteExpense(index: number) {
    this.form1 = { ...this.expense[index] };

    this.expenseService.deleteExpense(this.form1.expenseId).subscribe((rsp) => {
      if (rsp.statusCode === 100) {
        this.showMessage('Success', rsp.message);
        this.loadExpense();
      } else {
        this.showMessage('Error', rsp.message);
      }
    });
  }

  requestSettlement() {
    const payload = {
      groupId: this.groupId,
      memberId: this.memberId,
      amount: this.split.totalSplitAmount,
    };

    this.settlementsService.createSettlement(payload).subscribe((rsp) => {
      this.showMessage('Success', rsp.message);
    });

    this.closeModal();
  }

  closeModal() {
    this.isSettlementModalOpen = false;
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
