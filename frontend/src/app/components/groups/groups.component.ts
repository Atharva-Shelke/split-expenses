import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { Group, GroupService } from './group.service';
import { PopupComponent } from '../shared/popup/popup.component';

@Component({
  selector: 'app-groups',
  standalone: true,
  imports: [CommonModule, FormsModule, PopupComponent],
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css'],
})

export class GroupsComponent implements OnInit {
  groups: Group[] = [];
  username = '';

  showPopup = false;
  popupTitle = '';
  popupMessage = '';

  constructor(
    private groupService: GroupService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.username = String(this.authService.getUsername());
    this.loadGroups();
  }

  loadGroups() {
    this.groupService.getGroups().subscribe((response) => (this.groups = response.groups));
  }

  isModalOpen = false;
  editIndex = -1;
  form: Group = { groupId: 0, groupName: '', createdBy: '', createdOn: '' };

  openAddModal() {
    this.editIndex = -1;
    this.form = { groupId: 0, groupName: '', createdBy: '', createdOn: '' };
    this.isModalOpen = true;
  }

  openEditModal(index: number) {
    this.editIndex = index;
    this.form = { ...this.groups[index] };
    this.isModalOpen = true;
  }

  saveGroup() {
    if (this.editIndex === -1) {
      this.groupService.createGroup(this.form).subscribe((rsp) => {
        this.showMessage('Success', rsp.message);
        this.loadGroups();
      });
    } else {
      this.groupService.updateGroup(this.form).subscribe((rsp) => {
        this.showMessage('Success', rsp.message);
        this.loadGroups();
      });
    }
    this.closeModal();
  }

  deleteGroup(index: number) {
    this.form = { ...this.groups[index] };
    this.groupService.deleteGroup(this.form.groupId).subscribe((rsp) => {
      this.showMessage('Success', rsp.message);
      this.loadGroups();
    });
  }

  viewGroup(index: number) {
    this.form = { ...this.groups[index] };
    this.router.navigate(
      ['/app/group-members'],
      { queryParams: { groupName: this.form.groupName, groupId: this.form.groupId } }
    );
  }

  closeModal() {
    this.isModalOpen = false;
  }

  showMessage(title: string, message: string) {
    this.popupTitle = title;
    this.popupMessage = message;
    this.showPopup = true;
  }
}
