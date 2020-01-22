import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import { MatDialog, MatDialogConfig,MatDialogRef } from '@angular/material';
import { RazlogOdbijanjaZahtevaComponent } from '../razlog-odbijanja-zahteva/razlog-odbijanja-zahteva.component';
import { FormBuilder, FormGroup } from '@angular/forms';



@Component({
  selector: 'app-admin-kc',
  templateUrl: './admin-kc.component.html',
  styleUrls: ['./admin-kc.component.css']
})



export class AdminKcComponent implements OnInit {

  private models : Array<RegistracijaPacijentaDTO>;
  private model : RegistracijaPacijentaDTO;

  private poruka : string;
 
  private temp : RegistracijaPacijentaDTO
  private postojiModel = false;
  private admin : LoginViewModel;
  private columnsToDisplay: string[] = ['ime','prezime','email','Status'];

  private formBuilder: FormBuilder;
  private dialogRef: MatDialogRef<RazlogOdbijanjaZahtevaComponent>;
  private form: FormGroup;




  constructor(private router: Router, private http: HttpClient, public dialog: MatDialog ) { }
  save() {
    this.dialogRef.close(this.form.value);
}

close() {
    this.dialogRef.close();
  }

  openDialog() {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

  
    const dialogRef = this.dialog.open(RazlogOdbijanjaZahtevaComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(value => {
      console.log(`Dialog sent: ${value}`); 
    });
  }



  ngOnInit() {
     this.getZahtevi();
     
  }




  async getZahtevi(){
    const apiEndPoint = 'http://localhost:8080/adminkc/zahtevi';
    
   this.http.get(apiEndPoint,{responseType : 'json'})
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
  
  console.log('prihvacen')
  model.aktivan = false;
  
  this.http.put(url,model.id)
  .subscribe((data) => {
   
  },err => {
    console.log('greska pri prihvatanju');
    console.log(err);
  });

 
}

async odbijZahtev(model:RegistracijaPacijentaDTO){
  const url = 'http://localhost:8080/adminkc/odbijZahtev';
  
    this.openDialog();

    

    // this.http.put(url,model.id)
    // .subscribe((data) => {
    
    // },err => {
    // console.log('greska pri odbijanju');
    // console.log(err);
    // });
  

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
