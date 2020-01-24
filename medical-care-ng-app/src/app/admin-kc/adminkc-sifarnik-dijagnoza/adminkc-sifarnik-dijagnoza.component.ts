import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { DodajDijagnozuComponent } from './dodaj-dijagnozu/dodaj-dijagnozu.component';
import { Router } from '@angular/router';
import { DijagnozaServiceComponent } from 'src/app/services/dijagnoza-service/dijagnoza-service.component';


@Component({
  selector: 'app-adminkc-sifarnik-dijagnoza',
  templateUrl: './adminkc-sifarnik-dijagnoza.component.html',
  styleUrls: ['./adminkc-sifarnik-dijagnoza.component.css']
})
export class AdminkcSifarnikDijagnozaComponent implements OnInit {

  private model : DijagnozaDodavanjeDTO;  
  private columnsToDisplay: string[] = ['nazivBolesti','kodBolesti'];
  private models : Array<DijagnozaDTO>;

  constructor(public dialog: MatDialog,private dataService : DijagnozaServiceComponent,private router: Router, private http: HttpClient ) { }

  ngOnInit() {
    this.getDijagnoze();
  }

  async getDijagnoze(){
    const apiEndPoint = 'http://localhost:8080/adminkc/dijagnoze';
    
   this.http.get(apiEndPoint,{responseType : 'json'})
   .subscribe((data) => {

     this.models = data as Array<DijagnozaDTO>;
   },err => {
     console.log('greska pri izlistavanju dijagnoza');
     console.log(err);
   });

}

  async dodajDijagnozu(){

    const url = 'http://localhost:8080/adminkc/dodajDijagnozu';
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
  
  
    const dialogRef = this.dialog.open(DodajDijagnozuComponent, dialogConfig);
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

export interface DijagnozaDodavanjeDTO{
  nazivBolesti : string;
  kodBolesti : string;
}

export interface DijagnozaDTO{
  id : number;
  kodBolesti : string;
  nazivBolesti : string;
  aktivan : boolean;
}

