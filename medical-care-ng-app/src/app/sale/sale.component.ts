import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.css']
})
export class SaleComponent implements OnInit {

  sale: Array<SalePretraga>;
  sala: BrisanjeSale = {
    brojSale: 0,
    datum: null,
    pocetakTermina: null,
    krajTermina: null
  };
  constructor(private http: HttpClient) {
    this.getSaleInitialy();
   }

  ngOnInit() {
  }

  async onSubmit() {
    console.log('Click');
  }

  async getSaleInitialy() {
    const apiEndpoint = 'http://localhost:8080/sale';

    this.http.get(apiEndpoint,
      {responseType: 'json'}).subscribe((data) => {
        this.sale = data as Array<SalePretraga>;
        console.log(this.sale);
      }, err => {
        console.log('Greska pri pribavljanju sala: ');
        console.log(err);
      });
  }

  async obrisiSalu(sala) {
    console.log(sala);

    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: sala,
    };
    const apiEndpoint = 'http://localhost:8080/sale';
    this.http.delete(apiEndpoint, options).subscribe((data) => {
      console.log('Uspenso brisanje klinike');
      this.getSaleInitialy();
    }, err => {
      console.log(err);
    });
  }

}

export interface SalePretraga {
  brojSale: number;
  pocetakTermina: Date;
  krajTermina: Date;
}

export interface BrisanjeSale {
  brojSale: number;
  datum: Date;
  pocetakTermina: Date;
  krajTermina: Date;
}
