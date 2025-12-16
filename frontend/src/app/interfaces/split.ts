export interface Split {
  totalSplitAmount: number;
  statement: string;
  canRequest: number;             // 1 = allow, 0 = no
  requestExists: number;          // 1 = exists, 0 = no
  existingRequestStatus: number;  // 1000/1001/1003 etc.
}
