import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-definisanje-pregleda',
  templateUrl: './definisanje-pregleda.component.html',
  styleUrls: ['./definisanje-pregleda.component.css']
})
export class DefinisanjePregledaComponent implements OnInit {

  sale: Array<SalePretraga>;
  sala: SalePretraga = {
    brojSale: 0,
    pocetakTermina: null,
    krajTermina: null
  };

  lekari: Array<Lekar>;


  constructor(private http: HttpClient) {
    this.getSaleInitialy();
    this.getLekareInitialy();
  }

  ngOnInit() {
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

  async getLekareInitialy() {
    const apiEndpoint = 'http://localhost:8080/lekari';

    this.http.get(apiEndpoint,
      {responseType: 'json'}).subscribe((data) => {
        this.lekari = data as Array<Lekar>;
        console.log(this.lekari);
      }, err => {
        console.log('Greska pri pribavljanju sala: ');
        console.log(err);
      });
  }
}

export interface SalePretraga {
  brojSale: number;
  pocetakTermina: Date;
  krajTermina: Date;
}

export interface Lekar {
  id: number;
  ime: string;
  prezime: string;
}
