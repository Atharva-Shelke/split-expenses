import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AppLayoutComponent } from './components/shared/layout/app-layout.component';
import { UsersComponent } from './components/users/users.component';
import { AuthGuard } from './components/auth/auth.guard';
import { GroupsComponent } from './components/groups/groups.component';
import { GroupMembersComponent } from './components/group-members/group-members.component';
import { ChangePasswordComponent } from './components/users/changePassword/changePassword.component';
import { RegisterComponent } from './components/users/registerNewUser/register.component';
import { AddExpenseComponent } from './components/expenses/add-expense/add-expense.component';
import { ViewExpenseComponent } from './components/expenses/view-expense/view-expense.component';
import { SettlementsComponent } from './components/settlements/settlements.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  {path:'resetPassword', component: ChangePasswordComponent},
  {path:'newUserRegistration',component:RegisterComponent },

  {
    path: 'app',
    component: AppLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'users', component: UsersComponent },
      { path: 'groups', component: GroupsComponent },
      { path: 'group-members', component: GroupMembersComponent },
      { path: 'add-expense', component: AddExpenseComponent },
      { path: 'view-expense', component: ViewExpenseComponent },
      { path: 'settlements', component: SettlementsComponent}

    ],
  },
];
