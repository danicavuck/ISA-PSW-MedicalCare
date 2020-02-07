import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';

import { SestraPacijentServiceComponent } from '../services/sestra-pacijent-service/sestra-pacijent-service.component';

@Component({
  selector: 'app-medicinska-sestra',
  templateUrl: './medicinska-sestra.component.html',
  styleUrls: ['./medicinska-sestra.component.css']
})
export class MedicinskaSestraComponent implements OnInit {

  private pacijentPretraga = '';
  private displayColumns: string[] = ['ime', 'prezime', 'email', 'Akcije'];
  private models: Array<Pacijent>;
  private modelsDataSource;
  constructor(private http: HttpClient,private pacijentService: SestraPacijentServiceComponent) {
    this.getPacijenteInitially();
   }
  
  @ViewChild(MatSort, {static: false}) pacijentSort: MatSort;
  @ViewChild(MatPaginator, {static: false}) pacijentPaginator: MatPaginator;
  ngOnInit() {

  }

  async getPacijenteInitially() {
    const apiEndpoint = 'http://localhost:8080/pacijenti/klinika';
    this.http.get(apiEndpoint, {withCredentials: true}).subscribe((data) => {
      this.models = data as Array<Pacijent>;
      this.modelsDataSource = new MatTableDataSource(this.models);
      this.modelsDataSource.sort = this.pacijentSort;
      this.modelsDataSource.paginator = this.pacijentPaginator;
    }, err => {
      console.log('Neuspesno odjavljivanje: ');
      console.log(err);
    });
  }

  async onDetaljnije(pacijent: Pacijent) {
    this.pacijentService.setPacijentID(pacijent.id);
    this.pacijentService.setSestraID(1);
  }

  async pretraziPacijente() {
    const apiEndpoint = 'http://localhost:8080/pacijenti/pretraga';
    this.http.post(apiEndpoint, this.pacijentPretraga, {responseType: 'json', withCredentials: true}).subscribe(data => {
      this.models = data as Array<Pacijent>;
      this.modelsDataSource = new MatTableDataSource(this.models);
    }, err => {
      console.log('Nije pronadjen pacijent');
    });
  }

  async applyFilter(filterValue: string) {
    this.modelsDataSource.filter = filterValue.trim().toLowerCase();
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

export interface Pacijent {
  id: number;
  ime: string;
  prezime: string;
  email: string;
}


