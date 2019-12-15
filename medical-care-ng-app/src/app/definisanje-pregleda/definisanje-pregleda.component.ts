import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Time } from '@angular/common';

@Component({
  selector: 'app-definisanje-pregleda',
  templateUrl: './definisanje-pregleda.component.html',
  styleUrls: ['./definisanje-pregleda.component.css']
})
export class DefinisanjePregledaComponent implements OnInit {
  tipPregleda: Array<TipPregleda>;
  pregled: Pregled = {
    datumPregleda: new Date(),
    tipPregleda: '',
    vremePregleda: new Date().getTime(),
    trajanjePregleda: 0,
    sala: 0,
    lekar: '',
    cena: 0
  };
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
    this.getTipInitialy();
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
  async getTipInitialy() {
    const apiEndpoint = 'http://localhost:8080/tippregleda';

    this.http.get(apiEndpoint,
      {responseType: 'json'}).subscribe((data) => {
        this.tipPregleda = data as Array<TipPregleda>;
        console.log(this.tipPregleda);
      }, err => {
        console.log('Greska pri pribavljanju tipova pregeda: ');
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
        console.log('Greska pri pribavljanju lekara: ');
        console.log(err);
      });
  }

  async onSubmit() {
    console.log(this.pregled);
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
  email: string;
}

export interface TipPregleda {
  tipPregleda: string;
}

export interface Pregled {
  datumPregleda: Date;
  vremePregleda: number;
  tipPregleda: string;
  trajanjePregleda: number;
  sala: number;
  lekar: string;
  cena: number;
}
