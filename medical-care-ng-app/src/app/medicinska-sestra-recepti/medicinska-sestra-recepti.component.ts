import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import { MatSort, MatPaginator, MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-medicinska-sestra-recepti',
  templateUrl: './medicinska-sestra-recepti.component.html',
  styleUrls: ['./medicinska-sestra-recepti.component.css']
})
export class MedicinskaSestraReceptiComponent implements OnInit {
  private postojiModel = false;
  private recepti : Array<ReceptDTO>;
  private receptiDataSource;
  @ViewChild(MatSort, {static: false}) receptiSort: MatSort;
  @ViewChild(MatPaginator, {static: false}) receptiPaginator: MatPaginator;
  private columnsToDisplay: string[] = ['kodLeka','nazivLeka','Status'];

  constructor(private router: Router , private http : HttpClient) {
    this.getRecepti();
   }

  ngOnInit() {
    this.getRecepti();
  }

  async getRecepti() {
    const apiEndpoint = 'http://localhost:8080/medsestra/recepti';
    this.http.get(apiEndpoint,
      {responseType: 'json', withCredentials: true}).subscribe((data) => {
        this.postojiModel = true;
        this.recepti = data as Array<ReceptDTO>;
        this.receptiDataSource = new MatTableDataSource(this.recepti);
        this.receptiDataSource.sort = this.receptiSort;
        console.log(this.recepti)
        //this.receptiDataSource.paginator = this.receptiPaginator;
      }, err => {
        console.log('Greska pri pribavljanju recepata! ');
        console.log(err);
      });
  }
  
  async overiRecept(model:ReceptDTO){
    const url = 'http://localhost:8080/medsestra/overiRecept ';
    
    console.log('prihvacen')
    
    
    this.http.put(url,model.id,{withCredentials : true})
    .subscribe((data) => {
      this.ngOnInit();
    },err => {
      this.ngOnInit();
    });
   
  }

  async odjaviSe() {
    const apiEndpoint = 'http://localhost:8080/odjava';
    this.http.post(apiEndpoint, {responseType: 'json', withCredentials: true}).subscribe(data => {
      console.log('Uspesno odjavljivanje sa sistema');
    }, err => {
      console.log(err);
    });
  }
}




export interface ReceptDTO{
  id : number;
  idLeka : string;
  nazivLeka: string;
  kodLeka : string
  idMedSestre : number

}
