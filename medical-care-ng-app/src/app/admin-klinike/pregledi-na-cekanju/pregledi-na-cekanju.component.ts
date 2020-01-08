import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-pregledi-na-cekanju',
  templateUrl: './pregledi-na-cekanju.component.html',
  styleUrls: ['./pregledi-na-cekanju.component.css']
})
export class PreglediNaCekanjuComponent implements OnInit {
  private preglediColumns: string[] = ['pocetakTermina', 'krajTermina', 'tipPregleda', 'sale', 'Akcije'];
  private sale: Array<Sala>;
  private brojeviSala: Array<number> = [];
  private odabranaSala = 0;
  private pregledi: Array<Pregled>;
  private odabranaSalaZaSlanje: OdabranaSala = {
    id: 0,
    brojSale: 0
  };
  private preglediDataSource;
  constructor(private http: HttpClient) {
    this.getPregledeNaCekanjuInitially();
    this.getSaleInitially();
  }

  ngOnInit() {
  }

  async getPregledeNaCekanjuInitially() {
    const apiEnpoint = 'http://localhost:8080/pregledinacekanju/' + 5;

    this.http.get(apiEnpoint, {responseType: 'json'}).subscribe(data => {
      this.pregledi = data as Array<Pregled>;
      this.preglediDataSource = new MatTableDataSource(this.pregledi);
      console.log(this.pregledi);
    }, err => {
      console.log('Neuspesno pribavljanje pregleda na cekanju');
    });
  }

  async getSaleInitially() {
    const apiEnpoint = 'http://localhost:8080/sale/' + 5;
    this.http.get(apiEnpoint, {responseType: 'json'}).subscribe(data => {
      this.sale = data as Array<Sala>;
      this.sale.forEach(element => {
        this.brojeviSala.push(element.brojSale);
      });
      console.log(this.sale);
    }, err => {
      console.log('Neuspesno pribavljanje sala');
    });

  }

  async dodajSaluPregledu(pregled) {
    this.odabranaSalaZaSlanje.brojSale = this.odabranaSala;
    this.odabranaSalaZaSlanje.id = pregled.id;
    console.log(this.odabranaSalaZaSlanje);
  }

}

export interface Pregled {
  pocetakTermina: string;
  krajTermina: string;
}

export interface Sala {
  brojSale: number;
}

export interface OdabranaSala {
  id: number;
  brojSale: number;
}
