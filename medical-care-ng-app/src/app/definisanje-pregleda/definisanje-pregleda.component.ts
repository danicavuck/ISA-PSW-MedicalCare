import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-definisanje-pregleda',
  templateUrl: './definisanje-pregleda.component.html',
  styleUrls: ['./definisanje-pregleda.component.css']
})
export class DefinisanjePregledaComponent implements OnInit {
  tipPregleda: Array<TipPregleda>;

  dateAndTime: Date = new Date();
  settings = {
    bigBanner: true,
        timePicker: false,
    format: 'dd-MM-yyyy',
        defaultOpen: true
  };
  pregled: Pregled = {
    datumVreme: new Date(),
    tipPregleda: '',
    trajanjePregleda: 0,
    sala: 0,
    lekar: 0,
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
    this.dateAndTime = new Date();
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
      }, err => {
        console.log('Greska pri pribavljanju lekara: ');
        console.log(err);
      });
  }

  async onSubmit() {
    const apiEndpoint = 'http://localhost:8080/pregledi';
    this.uvecanjeSatnice(this.pregled);
    this.http.post(apiEndpoint, this.pregled,
      {responseType: 'text'}).subscribe((data) => {
        console.log(data);
      }, err => {
        console.log('Greska pri kreiranju novog pregeda: ');
        console.log(err);
      });
  }

  async uvecanjeSatnice(pregled) {
    pregled.datumVreme.setHours(this.pregled.datumVreme.getHours() + 1, 0, 0, 0);
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
  datumVreme: Date;
  tipPregleda: string;
  trajanjePregleda: number;
  sala: number;
  lekar: number;
  cena: number;
}
