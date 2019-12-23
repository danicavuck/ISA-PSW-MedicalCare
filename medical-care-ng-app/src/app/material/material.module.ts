import { NgModule } from '@angular/core';
import {
      MatButtonModule,
      MatFormFieldModule,
      MatInputModule,
      MatTableModule,
      MatDividerModule,
      MatTooltipModule,
      MatDialogModule,
      MatSnackBarModule
    } from '@angular/material';

const MaterialComponents = [
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatTableModule,
  MatDividerModule,
  MatTooltipModule,
  MatDialogModule,
  MatSnackBarModule
];

@NgModule({
  imports: [MaterialComponents],
  exports: [MaterialComponents]
})
export class MaterialModule { }
