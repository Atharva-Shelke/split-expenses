import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username = '';
  password = '';
  showPassword = false;
  loading = false;
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) {}

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  onLogin(form: any) {
    if (form.invalid) return;

    this.loading = true;
    this.errorMessage = '';

    const credentials = {
      username: this.username,
      password: this.password,
    };

    this.authService.login(credentials).subscribe({
      next: (response) => {
        if (response.statusCode === 100) {
          this.authService.setUser(response.currentUser);
          this.router.navigate(['/app/groups']);
        } else {
          this.password = '';
          this.errorMessage = response.message || 'Invalid credentials';
        }
        this.loading = false;
      },
      error: (err) => {
        if (err.status === 401 && err.error?.message) {
          this.errorMessage = err.error.message;
        } else {
          this.errorMessage = 'Something went wrong, try again.';
        }
        this.loading = false;
      },
    });
  }
}
