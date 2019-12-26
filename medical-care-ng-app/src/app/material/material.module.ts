import { NgModule } from '@angular/core';
import {
      MatButtonModule,
      MatFormFieldModule,
      MatInputModule,
      MatTableModule,
      MatDividerModule,
      MatTooltipModule,
      MatDialogModule,
      MatSnackBarModule,
      MatPaginatorModule,
      MatSelectModule,
      MatToolbarModule,
      MatSortModule
    } from '@angular/material';

const MaterialComponents = [
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatTableModule,
  MatDividerModule,
  MatTooltipModule,
  MatDialogModule,
  MatSnackBarModule,
  MatPaginatorModule,
  MatSelectModule,
  MatToolbarModule,
  MatSortModule
];

@NgModule({
  imports: [MaterialComponents],
  exports: [MaterialComponents]
})
export class MaterialModule { }
