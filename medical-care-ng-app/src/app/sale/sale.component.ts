import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.css']
})
export class SaleComponent implements OnInit {

  sale: Array<SalePretraga>;
  sala: SalePretraga = {
    brojSale: 0,
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
    const apiEndpoint = 'http://localhost:8080/sale';
    const params = new HttpParams().set('brojSale', sala.brojSale);
    console.log(sala);
  }

}

export interface SalePretraga {
  brojSale: number;
  pocetakTermina: Date;
  krajTermina: Date;
}
