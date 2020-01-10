import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSort, MatPaginator, MatTableDataSource } from '@angular/material';
import { PacijentServiceComponent } from '../services/pacijent-service/pacijent-service.component';

@Component({
  selector: 'app-lekar',
  templateUrl: './lekar.component.html',
  styleUrls: ['./lekar.component.css']
})
export class LekarComponent implements OnInit {
  private pacijentPretraga = '';
  private displayColumns: string[] = ['ime', 'prezime', 'email', 'Akcije'];
  private models: Array<Pacijent>;
  private modelsDataSource;

  @ViewChild(MatSort, {static: false}) pacijentSort: MatSort;
  @ViewChild(MatPaginator, {static: false}) pacijentPaginator: MatPaginator;

  constructor(private http: HttpClient, private pacijentService: PacijentServiceComponent) {
    this.getPacijenteInitially();
  }

  ngOnInit() {
  }

  async getPacijenteInitially() {
    // U startu cemo dobavljati pacijente za kliniku 5
    const apiEndpoint = 'http://localhost:8080/pacijenti/' + 5;
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

  // Lekar je za sada ID 1 (Milos Petrovic)
  async onDetaljnije(pacijent: Pacijent) {
    this.pacijentService.setPacijentID(pacijent.id);
    this.pacijentService.setLekarID(1);
  }

  async pretraziPacijente() {
    const apiEndpoint = 'http://localhost:8080/pacijenti/pretraga';
    this.http.post(apiEndpoint, this.pacijentPretraga, {responseType: 'json'}).subscribe(data => {
      this.models = data as Array<Pacijent>;
      this.modelsDataSource = new MatTableDataSource(this.models);
    }, err => {
      console.log('Nije pronadjen pacijent');
    });
  }

  async applyFilter(filterValue: string) {
    this.modelsDataSource.filter = filterValue.trim().toLowerCase();
  }
}

export interface Pacijent {
  id: number;
  ime: string;
  prezime: string;
  email: string;
}
