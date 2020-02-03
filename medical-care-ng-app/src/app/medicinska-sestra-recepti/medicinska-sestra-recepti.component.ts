import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";


@Component({
  selector: 'app-medicinska-sestra-recepti',
  templateUrl: './medicinska-sestra-recepti.component.html',
  styleUrls: ['./medicinska-sestra-recepti.component.css']
})
export class MedicinskaSestraReceptiComponent implements OnInit {
  private postojiModel = false;
  private models : Array<ReceptDTO>;
  private columnsToDisplay: string[] = ['kodLeka','Status'];

  constructor(private router: Router , private http : HttpClient) { }

  ngOnInit() {
    this.getRecepti();
  }

  async getRecepti(){
    const apiEndPoint = 'http://localhost:8080/medsestra/recepti';
    
   this.http.get(apiEndPoint,{withCredentials : true})
   .subscribe((data) => {
     this.postojiModel = true;
     this.models = data as Array<ReceptDTO>;
   },err => {
     console.log('greska pri izlistavanju recepata');
     console.log(err);
   });
  }
  
  async overiRecept(model:ReceptDTO){
    const url = 'http://localhost:8080/medsestra/overiRecept ';
    
    console.log('prihvacen')
    
    
    this.http.put(url,model.id)
    .subscribe((data) => {
     
    },err => {
      console.log('greska pri prihvatanju');
      console.log(err);
    });
  
   
  }


}


export interface ReceptDTO{
  id : number;
  idLeka : string;
  idMedSestre : number

}
