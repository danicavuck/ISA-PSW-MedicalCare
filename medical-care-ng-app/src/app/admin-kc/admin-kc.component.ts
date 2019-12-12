import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";


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


async prihvatiZahtev(model:RegistracijaPacijentaDTO,i:number){
  const url = 'http://localhost:8080/adminkc/prihvatiZahtev';
  
  this.temp = this.models[i];
  console.log('prihvacen')
  model.aktivan = false;

  const params = new HttpParams()
                    .set('id', model.id.toString() );

  model.aktivan = false;
    return this.http.put(url, {params} )
 
}

async odbijZahtev(model:RegistracijaPacijentaDTO,i:number){
  const url = 'http://localhost:8080/adminkc/odbijZahtev';
  
  const params = new HttpParams()
                    .set('id', model.id.toString());

  model.aktivan = false;
    return this.http.put(url,null, {params} )

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
}
