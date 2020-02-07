import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { IzvestajServiceComponent } from 'src/app/services/izvestaj-service/izvestaj-service.component';

@Component({
  selector: 'app-izvestaj',
  templateUrl: './izvestaj.component.html',
  styleUrls: ['./izvestaj.component.css']
})
export class IzvestajComponent implements OnInit {
  private model : IzvestajOPregleduDTO = {
    informacijeOpregledu : '',
    idLekar : 0,
    idPacijent : 0,
    idDijagnoza: 0,
    idLek : []

  }

  private dijagnoze: Array<DijagnozaDTO>;
  private lekovi : Array<LekDTO>;
  errorStatus: string = null;

  constructor(private http : HttpClient, private router:Router, private service : IzvestajServiceComponent) { }


  ngOnInit() {
    this.getDijagnoze();
    this.getLekove();
  }

  async onSubmit() {
      this.model.idLekar = this.service.getLekarID();
      this.model.idPacijent = this.service.getPacijentID();
      const apiEndpoint = 'http://localhost:8080/izvestaj/dodajIzvestaj';
      if (this.isFormValid()) {
      this.http.post(apiEndpoint, this.model,
        {responseType: 'text',withCredentials:true}).subscribe( data => {
          console.log('Uspesno dodavanje izvestaja o pregledu');
          
        }, error => {
          this.errorStatus = '';
          this.errorStatus = 'Nije validno popunjena forma';
          console.log(this.model)
        });
      }else {
        console.log(this.model)
          console.log('Forma nije validna');
        }
   
      
    }
    isFormValid() {
      // tslint:disable-next-line: max-line-length
      console.log(this.model)
      if (this.model.idDijagnoza !== null && this.model.idLek !== null) {
        return true;
      }
  
      return false;
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
  informacijeOpregledu : string;
  idLekar : number;
  idPacijent : number;
  idDijagnoza : number;
  idLek : Array<number>;

}

