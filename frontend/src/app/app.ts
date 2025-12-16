import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from './components/auth/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `<router-outlet></router-outlet>`,
})
export class AppComponent {
  constructor(private authService: AuthService, private router: Router) {}
  ngOnInit() {
    this.authService.checkSession().subscribe({
      next: (res) => {
        if (res.loggedIn) {
          this.authService.setUser(res);
          this.router.navigate(['/app/groups']);
        }
      },
      error: () => {
        this.authService.clearUser();
        this.router.navigate(['/login']);
      },
    });
  }
}
