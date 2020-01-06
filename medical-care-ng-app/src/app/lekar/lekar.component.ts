import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSort, MatPaginator, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-lekar',
  templateUrl: './lekar.component.html',
  styleUrls: ['./lekar.component.css']
})
export class LekarComponent implements OnInit {
  private displayColumns: string[] = ['ime', 'prezime', 'email', 'Akcije'];
  private models: Array<Pacijent>;
  private modelsDataSource;

  @ViewChild(MatSort, {static: false}) pacijentSort: MatSort;
  @ViewChild(MatPaginator, {static: false}) pacijentPaginator: MatPaginator;

  constructor(private http: HttpClient) {
    this.getPacijenteInitially();
  }

  ngOnInit() {
  }

  async getPacijenteInitially() {
    // U startu cemo dobavljati pacijente za kliniku 5
    const apiEndpoint = 'http://localhost:8080/pacijenti/' + 5;
    this.http.get(apiEndpoint).subscribe((data) => {
      console.log('Uspesno odjavljivanje');
      this.models = data as Array<Pacijent>;
      this.modelsDataSource = new MatTableDataSource(this.models);
      this.modelsDataSource.sort = this.pacijentSort;
      this.modelsDataSource.paginator = this.pacijentPaginator;
      console.log(this.models);
    }, err => {
      console.log('Neuspesno odjavljivanje: ');
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
