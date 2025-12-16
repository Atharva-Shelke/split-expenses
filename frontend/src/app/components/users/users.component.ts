
// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { FormsModule } from '@angular/forms';
// import { User, UserService } from './user.service';
// import { Router } from '@angular/router';
// import { AuthService } from '../auth/auth.service';

// @Component({
//   selector: 'app-users',
//   standalone: true,
//   imports: [CommonModule, FormsModule],
//   template: `
//     <div class="users-container">
//       <h2>Users</h2>

//       <button class="add-btn" (click)="openAddModal()">+ Add User</button>

//       <table class="users-table">
//         <thead>
//           <tr>
//             <th>ID</th>
//             <th>Name</th>
//             <th>Email</th>
//             <th>Mobile</th>
//             <th>Actions</th>
//           </tr>
//         </thead>

//         <tbody>
//           <tr *ngFor="let u of users; let i = index">
//             <td>{{ u.userId }}</td>
//             <td>{{ u.username }}</td>
//             <td>{{ u.email }}</td>
//             <td>{{ u.mobileNo }}</td>
//             <td style="padding-left:7%;">
//               <button class="edit-btn" (click)="openEditModal(i)">Edit</button>
//               <button class="delete-btn" (click)="deleteUser(i)">Delete</button>
//             </td>
//           </tr>
//         </tbody>
//       </table>

//       <!-- Add/Edit Modal -->
//       <div class="modal-backdrop" *ngIf="isModalOpen">
//         <div class="modal-box">
//           <h3>{{ editIndex === -1 ? 'Add User' : 'Edit User' }}</h3>

//           <form (ngSubmit)="saveUser()">
//             <table class="edit-table">
//               <tbody>
//               <tr>
//                 <td><label>Name</label></td>
//                 <td><input [(ngModel)]="form.username" name="name" required /></td>
//               <tr>
//                 <td><label>Email</label></td>
//                 <td><input [(ngModel)]="form.email" name="email" required /></td>
//               <tr>
//                 <td><label>Mobile</label></td>
//                 <td><input [(ngModel)]="form.mobileNo" name="mobile" required /></td>
//               </tbody>
//             </table>

//             <div class="modal-buttons">
//               <button type="submit" class="save-btn">Save</button>
//               <button type="button" class="cancel-btn" (click)="closeModal()">Cancel</button>
//             </div>
//           </form>
//         </div>
//       </div>
//     </div>
//   `,
//   styles: [`
//     .users-container {
//       padding: 0px 20px 20px 20px;
//     }

//     h2 {
//       margin-bottom: 15px;
//     }

//     .add-btn {
//       background: #4caf50;
//       color: white;
//       padding: 8px 12px;
//       border: none;
//       cursor: pointer;
//       margin-bottom: 10px;
//       border-radius: 5px;
//     }

//     .users-table {
//       width: 100%;
//       border-collapse: collapse;
//       margin-top: 10px;
//     }

//     .users-table th, .users-table td {
//       padding: 10px;
//       border: 1px solid #ddd;
//     }

//     .edit-btn {
//       background: #ffa500;
//       padding: 5px 10px;
//       border: none;
//       color: white;
//       margin-right: 5px;
//       border-radius: 4px;
//       cursor: pointer;
//     }

//     .delete-btn {
//       background: #e74c3c;
//       padding: 5px 10px;
//       border: none;
//       color: white;
//       border-radius: 4px;
//       cursor: pointer;
//     }

//     /* Modal Styles */
//     .modal-backdrop {
//       position: fixed;
//       top: 0;
//       left: 0;
//       width: 100%;
//       height: 100%;
//       background: rgba(0,0,0,0.5);
//       display: flex;
//       justify-content: center;
//       align-items: center;
//     }

//     .modal-box {
//       background: white;
//       padding: 20px;
//       width: 300px;
//       border-radius: 8px;
//     }
    
//     .edit-table td {
//       padding-left: 20px;
//       padding-right: 30px;
//     }

//     .modal-buttons {
//       margin-top: 10px;
//       display: flex;
//       justify-content: flex-end;
//       gap: 10px;
//     }

//     .save-btn {
//       background: #2196f3;
//       border: none;
//       color: white;
//       padding: 6px 12px;
//       border-radius: 4px;
//       cursor: pointer;
//     }

//     .cancel-btn {
//       background: #777;
//       border: none;
//       color: white;
//       padding: 6px 12px;
//       border-radius: 4px;
//       cursor: pointer;
//     }
//   `]
// })
// export class UsersComponent implements OnInit {

// users: User[] = [];
// newUser: User = { username: '', password: '', email: '', mobileNo: '' };
// searchValue: string = '';
// searchField: string = 'username';

//   constructor(private userService: UserService, private authService: AuthService, private router: Router) {}
//     ngOnInit() {
//     this.loadUsers();
//   }

//   loadUsers() {
//     this.searchValue = '';
//     this.userService.getUsers().subscribe((response) => (
//       this.users = response.users
//     ));
//   }

//   // users = [
//   //   { id: 1, name: 'John Doe', email: 'john@gmail.com', mobile: '9999999999' },
//   //   { id: 2, name: 'Jane Smith', email: 'jane@gmail.com', mobile: '8888888888' }
//   // ];

//   isModalOpen = false;
//   editIndex = -1;

//   form = { username: '', email: '', mobileNo: '' };

//   openAddModal() {
//     this.editIndex = -1;
//     this.form = { username: '', email: '', mobileNo: '' };
//     this.isModalOpen = true;
//   }

//   openEditModal(index: number) {
//     this.editIndex = index;
//     // this.form = { ...this.users[index] };
//     this.isModalOpen = true;
//   }

//   saveUser() {
//     if (this.editIndex === -1) {
//       const newUser = { id: this.users.length + 1, ...this.form };
//       this.users.push(newUser);
//     } else {
//       this.users[this.editIndex] = { ...this.users[this.editIndex], ...this.form };
//     }
//     this.closeModal();
//   }

//   deleteUser(index: number) {
//     this.users.splice(index, 1);
//   }
//   //   deleteUser(id: number) {
// //     this.userService.deleteUser(id).subscribe((rsp) => {
// //       alert(rsp.message);
// //       this.loadUsers();
// //     });
// //   }

//   closeModal() {
//     this.isModalOpen = false;
//   }
// }
