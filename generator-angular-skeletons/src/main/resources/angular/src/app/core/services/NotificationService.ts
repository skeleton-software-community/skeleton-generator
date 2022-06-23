import { Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";

@Injectable({providedIn:'root'})
export class NotificationService {
  constructor(private snackBar: MatSnackBar) {}

  public info(message:string) {
    this.snackBar.open(message, 'X', {
      duration: 5000,
      verticalPosition: "top",
      horizontalPosition: "center",
      panelClass: ["notification-info-style"]
    });
  }

  public error(message:string) {
    this.snackBar.open(message, 'X', {
      duration: 5000,
      verticalPosition: "top",
      horizontalPosition: "center",
      panelClass: ["notification-error-style"]
    });
  }
}
