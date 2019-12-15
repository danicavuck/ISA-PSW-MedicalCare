import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registracija-admina-klinike',
  templateUrl: './registracija-admina-klinike.component.html',
  styleUrls: ['./registracija-admina-klinike.component.css']
})
export class RegistracijaAdminaKlinikeComponent implements OnInit {

  klinike : Array<KlinikaDTO>;


  constructor(private http : HttpClient, private router:Router) { }

  ngOnInit() {
    this.getKlinike();
  }

  

  async getKlinike(){
    const apiEndPoint = 'http://localhost:8080/klinika/klinike';
    
   this.http.get(apiEndPoint,{responseType : 'json'})
   .subscribe((data) => {
     this.klinike = data as Array<KlinikaDTO>;
   },err => {
     console.log('greska pri izlistavanju klinika');
     console.log(err);
   });
  
}

}

export interface KlinikaDTO{
  naziv : string;
  adresa : string;
}
