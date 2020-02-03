import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import { MatDialog, MatDialogConfig,MatDialogRef } from '@angular/material';
import { RazlogOdbijanjaZahtevaComponent } from '../razlog-odbijanja-zahteva/razlog-odbijanja-zahteva.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RazlogOdbijanjaServiceComponent } from '../services/razlog-odbijanja-service/razlog-odbijanja-service.component';



@Component({
  selector: 'app-admin-kc',
  templateUrl: './admin-kc.component.html',
  styleUrls: ['./admin-kc.component.css']
})



export class AdminKcComponent implements OnInit {

  private models : Array<RegistracijaPacijentaDTO>;
  private model : RegistracijaPacijentaDTO;

 
  private temp : RegistracijaPacijentaDTO
  private postojiModel = false;
  private admin : LoginViewModel;
  private columnsToDisplay: string[] = ['ime','prezime','email','Status'];



  constructor(private dataService : RazlogOdbijanjaServiceComponent,private router: Router, private http: HttpClient, public dialog: MatDialog ) { }


  ngOnInit() {
     this.getZahtevi();
     
  }

  async getZahtevi(){
    const apiEndPoint = 'http://localhost:8080/adminkc/zahtevi';
    
   this.http.get(apiEndPoint, {withCredentials : true})
   .subscribe((data) => {
     this.postojiModel = true;
     this.models = data as Array<RegistracijaPacijentaDTO>;
   },err => {
     console.log('greska pri izlistavanju zahteva');
     console.log(err);
   });

  
}


async prihvatiZahtev(model:RegistracijaPacijentaDTO){
  const url = 'http://localhost:8080/adminkc/prihvatiZahtev';
  
  this.http.put(url,model, { withCredentials: true })
  .subscribe((data) => {
  },err => {
    this.ngOnInit();
    console.log(err)
  });

 
}

async odbijZahtev(model:RegistracijaPacijentaDTO){
  const url = 'http://localhost:8080/adminkc/odbijZahtev';
  
  const dialogConfig = new MatDialogConfig();

  dialogConfig.disableClose = true;
  dialogConfig.autoFocus = true;


  const dialogRef = this.dialog.open(RazlogOdbijanjaZahtevaComponent, dialogConfig);

  dialogRef.afterClosed().subscribe(value => {
    if(value == true){
      model.poruka = this.dataService.getData().razlog;
      this.http.put(url,model,{ withCredentials: true })
    .subscribe((data) => {
     
    },err => {
    this.ngOnInit();
    });
  
    }
  });

    

}





}

export interface LoginViewModel{
  email:string;
  lozinka:string;
}


export interface RegistracijaPacijentaDTO{
  id : number;
  ime : string;
  prezime : string;
  email : string;
  aktivan : boolean;
  poruka : string;
}
