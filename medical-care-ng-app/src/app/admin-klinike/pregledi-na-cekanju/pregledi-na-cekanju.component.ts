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
  private postojePregledi = false;
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

  }

  ngOnInit() {
    this.postojePregledi = false;
    this.getPregledeNaCekanjuInitially();
    this.getSaleInitially();
  }

  async getPregledeNaCekanjuInitially() {
    const apiEnpoint = 'http://localhost:8080/pregledinacekanju/' + 5;

    this.http.get(apiEnpoint, {responseType: 'json'}).subscribe(data => {
      this.pregledi = data as Array<Pregled>;
      this.preglediDataSource = new MatTableDataSource(this.pregledi);
      if (this.pregledi.length > 0) {
        this.postojePregledi = true;
      }
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
    if (this.odabranaSalaZaSlanje.brojSale !== 0) {
      const apiEnpoint = 'http://localhost:8080/pregledinacekanju';
      this.http.post(apiEnpoint, this.odabranaSalaZaSlanje, {responseType: 'json'}).subscribe(data => {
      console.log('Uspesno dodeljena sala');
      this.ngOnInit();
    }, err => {
      console.log('Greska pri dodeljivanju sala');
    });
    } else {
      alert('Morate odabrati salu!');
    }
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
