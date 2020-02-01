import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { DodajLekComponent } from './dodaj-lek/dodaj-lek.component';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { LekServiceComponent } from 'src/app/services/lek-service/lek-service.component';

@Component({
  selector: 'app-adminkc-sifarnik-lekova',
  templateUrl: './adminkc-sifarnik-lekova.component.html',
  styleUrls: ['./adminkc-sifarnik-lekova.component.css']
})
export class AdminkcSifarnikLekovaComponent implements OnInit {

  private models : Array<LekDTO>;
  private columnsToDisplay: string[] = ['nazivLeka','kodLeka'];
  constructor(public dialog: MatDialog,private dataService : LekServiceComponent,private router: Router, private http: HttpClient) { }
  private model : LekDTO;
  ngOnInit() {
    this.getDijagnoze();
  }

  async getDijagnoze(){
    const apiEndPoint = 'http://localhost:8080/adminkc/lekovi';
    
   this.http.get(apiEndPoint,{responseType : 'json'})
   .subscribe((data) => {

     this.models = data as Array<LekDTO>;
   },err => {
     console.log('greska pri izlistavanju lekova');
     console.log(err);
   });

}
async dodajDijagnozu(){

  const url = 'http://localhost:8080/adminkc/dodajLek';
  const dialogConfig = new MatDialogConfig();

  dialogConfig.disableClose = true;
  dialogConfig.autoFocus = true;


  const dialogRef = this.dialog.open(DodajLekComponent, dialogConfig);
  dialogRef.afterClosed().subscribe(value => {
    if(value == true){
      this.model = this.dataService.getData();
      this.http.post(url,this.model,{ withCredentials: true })
    .subscribe((data) => {
      this.ngOnInit();
    },err => {
      this.ngOnInit();
   
    });
  
    }
  });



}

}

export interface LekDTO{
  nazivLeka : string;
  kodLeka : string;
}
