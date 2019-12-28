import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource, MatSortModule, MatSort, MatPaginator } from '@angular/material';

@Component({
  selector: 'app-pregled-pacijenata',
  templateUrl: './pregled-pacijenata.component.html',
  styleUrls: ['./pregled-pacijenata.component.css']
})
export class PregledPacijenataComponent implements OnInit {
  private preglediColumns: string[] = ['ime', 'prezime', 'email', 'brojTelefona', 'Akcije'];
  private pacijentiDataSource;
  private pacijenti: Array<Pacijent>;
  @ViewChild(MatSort, {static: false}) pacijentiSort: MatSort;
  @ViewChild(MatPaginator, {static: false}) pacijentiPaginator: MatPaginator;

  constructor(private http: HttpClient) {
    this.getPacijenteInitialy();
   }

  ngOnInit() {
  }

  async getPacijenteInitialy() {
    const apiEndpoint = 'http://localhost:8080/pacijenti/' + 5;

    this.http.get(apiEndpoint,
      {responseType: 'json'}).subscribe((data) => {
        this.pacijenti = data as Array<Pacijent>;
        console.log(this.pacijenti);
        this.pacijentiDataSource = new MatTableDataSource(this.pacijenti);
        console.log(this.pacijentiDataSource);
        this.pacijentiDataSource.sort = this.pacijentiSort;
        this.pacijentiDataSource.paginator = this.pacijentiPaginator;
      }, err => {
        console.log('Greska pri pribavljanju pacijenata: ');
        console.log(err);
      });
  }

}

export interface Pacijent {
  ime: string;
  prezime: string;
  email: string;
  adresa: string;
  grad: string;
  drzava: string;
  brojTelefona: string;
  brojOsiguranja: string;
}
