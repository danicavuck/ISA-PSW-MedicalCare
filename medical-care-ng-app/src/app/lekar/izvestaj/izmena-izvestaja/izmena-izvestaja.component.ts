import { Component, OnInit } from '@angular/core';
import { IzvestajServiceComponent } from 'src/app/services/izvestaj-service/izvestaj-service.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-izmena-izvestaja',
  templateUrl: './izmena-izvestaja.component.html',
  styleUrls: ['./izmena-izvestaja.component.css']
})
export class IzmenaIzvestajaComponent implements OnInit {
  model : IzvestajIzmenaDTO = {
    id : 0,
    informacije : '',
    idDijagnoza : 0,
    idLek : []
  }
  lekovi : Array<LekDTO>;
  dijagnoze : Array<DijagnozaDTO>;

  errorStatus: string = null;
  constructor(private service : IzvestajServiceComponent,private http: HttpClient) {
    this.getDijagnoze();
    this.getLekove();
   }

  ngOnInit() {
    this.model.id = this.service.getIzvestajId();
  }
  
  async onSubmit() {
  
    const apiEndpoint = 'http://localhost:8080/izvestaj';
    if (this.isFormValid()) {
    this.http.put(apiEndpoint, this.model,
      {responseType: 'text',withCredentials:true}).subscribe( data => {
        console.log('Uspesna izmena izvestaja o pregledu');
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
      if (this.model.informacije != "" && this.model.idDijagnoza !== null && this.model.idLek !== null) {
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

export interface IzvestajIzmenaDTO{
  id : number;
  informacije : string;
  idDijagnoza : number;
  idLek : Array<number>;

}
