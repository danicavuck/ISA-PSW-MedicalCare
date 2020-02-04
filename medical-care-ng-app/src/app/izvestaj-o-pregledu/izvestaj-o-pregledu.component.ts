import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-izvestaj-o-pregledu',
  templateUrl: './izvestaj-o-pregledu.component.html',
  styleUrls: ['./izvestaj-o-pregledu.component.css']
})
export class IzvestajOPregleduComponent implements OnInit {
  
  private model: IzvestajOPregleduDTO = {
   idPacijent : 0,
   informacijeOPregledu : "",
   idDijagnoza : 0,
   idLek : []
  }

  private dijagnoze: Array<DijagnozaDTO>;
  private lekovi : Array<LekDTO>;
  errorStatus: string = null;

  constructor(private http : HttpClient, private router:Router) { }


  ngOnInit() {
    this.getDijagnoze();
    this.getLekove();
  }

  async onSubmit() {
   
      const apiEndpoint = 'http://localhost:8080/izvestaj/dodajIzvestaj';

      this.http.post(apiEndpoint, this.model,
        {responseType: 'text',withCredentials:true}).subscribe( data => {
          console.log('Uspesno dodavanje izvestaja o pregledu');
          console.log(this.model)
          
        }, error => {
          console.log(this.model)
         
        });
   
      
  }

  async getLekove(){
    const apiEndPoint = 'http://localhost:8080/adminkc/lekovi';
    
   this.http.get(apiEndPoint,{ withCredentials: true })
   .subscribe((data) => {
     this.lekovi = data as Array<LekDTO>;
   },err => {
     console.log('greska pri izlistavanju lekova');
     console.log(err);
   });
  
}

async getDijagnoze(){
  const apiEndPoint = 'http://localhost:8080/adminkc/dijagnoze';
  
 this.http.get(apiEndPoint,{ withCredentials: true })
 .subscribe((data) => {
   this.dijagnoze = data as Array<DijagnozaDTO>;
 },err => {
   console.log('greska pri izlistavanju dijagnoza');
   console.log(err);
 });

}
}

export interface LekDTO{
  id : number;
  kodLeka : string;
  nazivLeka : string;
  aktivan : boolean;
}


export interface DijagnozaDTO{
  id : number;
  kodBolesti : string;
  nazivBolesti : string;
  aktivan : boolean;
}

export interface IzvestajOPregleduDTO{
  idPacijent : number
  informacijeOPregledu : string;
  idDijagnoza : number;
  idLek : Array<number>;
}



