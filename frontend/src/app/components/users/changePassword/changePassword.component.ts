import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { User, UserService } from '../user.service';
import { Router, RouterLink } from '@angular/router';
import { PopupComponent } from '../../shared/popup/popup.component';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, PopupComponent],
  templateUrl: './changePassword.component.html',
  styleUrl: '../../login/login.component.css',
})
export class ChangePasswordComponent {
  user: User[] = [];
  confirmPassword = '';
  errorMessage = '';
  showPassword = false;
  form: User = { username: '', password: '' };

  showPopup = false;
  popupTitle = '';
  popupMessage = '';
  samePage = false;
  loading = false;

  constructor(private userService: UserService, private router: Router) {}

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  onChangePassword(form: any) {
    this.loading = true;
    if (form.invalid || this.form.password !== this.confirmPassword) return;

    this.userService.updatePassword(this.form).subscribe({
      next: (res) => {
        if (res.statusCode === 100) {
          this.showMessage('Success', res.message);
        } else {
          this.errorMessage = res.message;
        }
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Something went wrong.';
        this.loading = false;
      },
    });
  }

  showMessage(title: string, message: string) {
    this.popupTitle = title;
    this.popupMessage = message;
    this.showPopup = true;
    this.samePage = false;
  }
}
