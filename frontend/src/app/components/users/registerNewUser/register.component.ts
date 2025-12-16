import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { User, UserService } from '../user.service';
import { PopupComponent } from '../../shared/popup/popup.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, PopupComponent],
  templateUrl: './register.component.html',
  styleUrl: '../../login/login.component.css',
})
export class RegisterComponent {
  errorMessage = '';
  showPassword = false;
  form: User = { username: '', email: '', mobileNo: '', password: '' };

  showPopup = false;
  popupTitle = '';
  popupMessage = '';
  samePage = false;
  loading = false;

  constructor(private userService: UserService, private router: Router) {}

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  onRegister(form: any) {
    this.loading = true;
    if (form.invalid) return;
    this.userService.createUser(this.form).subscribe({
      next: (res) => {
        if (res.statusCode === 100) {
          this.showMessage('Success', res.message);
          this.router.navigate(['/login']);
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
