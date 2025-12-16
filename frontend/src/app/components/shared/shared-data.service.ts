import { Injectable } from '@angular/core';

// @Injectable({ providedIn: 'root' })
// export class SharedDataService {
//   private members: any[] = [];

//   setMembers(members: any[]) {
//     this.members = members;
//   }

//   getMembers() {
//     console.log("addexp>",this.members);
//     return this.members;
//   }
// }
@Injectable({ providedIn: 'root' })
export class SharedDataService {

  private members: any[] = [];

  setMembers(members: any[]) {
    this.members = members;

    // Save to sessionStorage for refresh support
    sessionStorage.setItem('expenseMembers', JSON.stringify(members));
  }

  getMembers(): any[] {
    // First check service memory
    if (this.members.length > 0) {
      return this.members;
    }

    // If service was cleared (page refresh), load from storage
    const stored = sessionStorage.getItem('expenseMembers');
    return stored ? JSON.parse(stored) : [];
  }

  clearMembers() {
    this.members = [];
    sessionStorage.removeItem('expenseMembers');
  }
}
