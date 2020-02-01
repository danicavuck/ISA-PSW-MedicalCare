import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registracija-klinike',
  templateUrl: './registracija-klinike.component.html',
  styleUrls: ['./registracija-klinike.component.css']
})
export class RegistracijaKlinikeComponent implements OnInit {
  
  model : KlinikaDTO = {
    naziv : "",
    adresa : "",
    opis : "",
    selLekari : "",
    selSestre : "",
    selSale : "",
    selAdmini: ""
  };

  lekari : Set<LekarDTO>;
  sestre : Set<MedicinskaSestraDTO>;
  sale : Set<SalaDTO>;
  admini : Set<AdminKlinikeDTO>;
  isLoading : boolean = false;
  errorStatus : string = null;

  constructor(private http: HttpClient , private router:Router) { }

  ngOnInit() {
    this.getLekari();
    this.getSestre();
    this.getSale();
    this.getAdmini();
  }

  async onSubmit(){
    this.isLoading = true;
    console.log(this.model);

      // let apiEndpoint = "http://localhost:8080/adminkc/dodajKliniku";
      
      // this.http.post(apiEndpoint, this.model,
      //   {responseType: 'text'}).subscribe( data => {
      //   setTimeout(() =>
      //   {
      //     this.router.navigateByUrl('/adminkc');
      //     this.isLoading = false;
      //   },2000);
      // }, err =>{
      //   setTimeout(()=>{
      //     this.isLoading = false;
      //     switch (err.error) {
      //       case 'Klinika sa tim nazivom vec postoji':
      //         this.errorStatus = "Klinika s tim nazivom vec postoji";
      //         break;
      //       case 'Internal server error':
      //         this.errorStatus = "Greska na serverskoj strani";
      //         break;
      //       default: this.errorStatus = "Greska pri registraciji";
      //     }
      //   },1000);

      // });
    
  }

  async getLekari(){
    const apiEndPoint = 'http://localhost:8080/klinika/lekari';
    
   this.http.get(apiEndPoint,{responseType : 'json'})
   .subscribe((data) => {
     this.lekari = data as Set<LekarDTO>;
   },err => {
     console.log('greska pri izlistavanju lekara');
     console.log(err);
   });
  
}
async getSestre(){
  const apiEndPoint = 'http://localhost:8080/klinika/medsestre';
  
 this.http.get(apiEndPoint,{responseType : 'json'})
 .subscribe((data) => {
   this.sestre = data as Set<MedicinskaSestraDTO>;
 },err => {
   console.log('greska pri izlistavanju sestara ');
   console.log(err);
 });

}
async getSale(){
  const apiEndPoint = 'http://localhost:8080/klinika/sale';
  
 this.http.get(apiEndPoint,{responseType : 'json'})
 .subscribe((data) => {
   this.sale = data as Set<SalaDTO>;
 },err => {
   console.log('greska pri izlistavanju sala');
   console.log(err);
 });

}
async getAdmini(){
  const apiEndPoint = 'http://localhost:8080/klinika/lekari';
  
 this.http.get(apiEndPoint,{responseType : 'json'})
 .subscribe((data) => {
   this.admini = data as Set<AdminKlinikeDTO>;
 },err => {
   console.log('greska pri izlistavanju admina');
   console.log(err);
 });

}


}


export interface LekarDTO{
  ime : string,
  prezime : string;
}
export interface MedicinskaSestraDTO{
  ime : string,
  prezime : string;
}

export interface SalaDTO{
 brojSale: number;
}
export interface AdminKlinikeDTO{
  ime : string,
  prezime : string;
}

export interface KlinikaDTO{
  naziv : string;
  adresa : string;
  opis : string;
  selLekari : string;
  selSestre : string;
  selSale : string;
  selAdmini : string;
}
