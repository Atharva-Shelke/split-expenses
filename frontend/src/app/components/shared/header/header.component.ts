import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  template: `
    <nav class="navbar" *ngIf="isLoggedIn">
      <div class="nav-left">
        <span class="logo">SplitExpenses</span>
        <a routerLink="/app/users">Users</a>
        <a routerLink="/app/groups">Groups</a>
        <a routerLink="/app/expenses">Expenses</a>
      </div>

      <button class="logout-btn" (click)="logout()">Logout</button>
    </nav>
  `,
  styles: [
    `
      .navbar {
        height: 50px;
        background: #282c34;
        color: white;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 15px;
      }

      .nav-left {
        display: flex;
        align-items: center;
        gap: 20px;
      }

      .logo {
        font-weight: bold;
        font-size: 18px;
      }

      a {
        color: #61dafb;
        text-decoration: none;
        font-size: 15px;
      }

      a:hover {
        text-decoration: underline;
      }

      .logout-btn {
        background: #ff4444;
        border: none;
        padding: 6px 14px;
        color: white;
        border-radius: 4px;
        cursor: pointer;
      }

      .logout-btn:hover {
        background: #cc0000;
      }
    `,
  ],
})
export class HeaderComponent {
  isLoggedIn = false;

  constructor(private auth: AuthService, private router: Router) {
    this.auth.isLoggedIn().subscribe((state) => {
      this.isLoggedIn = state;
    });
  }

  logout() {
    this.auth.logout().subscribe(() => {
      this.auth.clearUser();
      this.router.navigate(['/login']);
    });
  }
}
