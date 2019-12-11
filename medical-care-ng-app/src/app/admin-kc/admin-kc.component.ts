import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-admin-kc',
  templateUrl: './admin-kc.component.html',
  styleUrls: ['./admin-kc.component.css']
})
export class AdminKcComponent implements OnInit {

  private models : Array<RegistracijaPacijentaDTO>;
  private postojiModel = false;
  private admin : LoginViewModel;

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit() {
    this.getZahtevi();
  }


  async getAdminKC(){
    const apiEndPoint = 'http://localhost:8080/adminkc/prvologovanje';
    
   this.http.get(apiEndPoint,{responseType : 'json'})
   .subscribe((data) => {
     this.postojiModel = true;
     this.models = data as Array<RegistracijaPacijentaDTO>;
   },err => {
     console.log('greska pri izlistavanju zahteva');
     console.log(err);
   });

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
}

export interface LoginViewModel{
  email:string;
  lozinka:string;
}


export interface RegistracijaPacijentaDTO{
  ime : string;
  prezime : string;
  email : string;

}
