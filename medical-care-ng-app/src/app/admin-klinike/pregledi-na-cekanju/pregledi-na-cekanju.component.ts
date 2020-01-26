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
  private naziviSala: Array<string> = [];
  private odabranaSala = '';
  private pregledi: Array<Pregled>;
  private odabranaSalaZaSlanje: OdabranaSala = {
    id: 0,
    nazivSale: ''
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
    const apiEnpoint = 'http://localhost:8080/pregledinacekanju';

    this.http.get(apiEnpoint, {responseType: 'json', withCredentials: true}).subscribe(data => {
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
    const apiEnpoint = 'http://localhost:8080/sale';
    this.http.get(apiEnpoint, {responseType: 'json', withCredentials: true}).subscribe(data => {
      this.sale = data as Array<Sala>;
      this.sale.forEach(element => {
        this.naziviSala.push(element.nazivSale);
      });
      console.log('Ucitane sale: ' + data);
      console.log(this.sale);
    }, err => {
      console.log('Neuspesno pribavljanje sala');
    });

  }

  async dodajSaluPregledu(pregled) {
    this.odabranaSalaZaSlanje.nazivSale = this.odabranaSala;
    this.odabranaSalaZaSlanje.id = pregled.id;
    if (this.odabranaSalaZaSlanje.nazivSale !== '') {
      const apiEnpoint = 'http://localhost:8080/pregledinacekanju';
      this.http.post(apiEnpoint, this.odabranaSalaZaSlanje, {responseType: 'json', withCredentials: true}).subscribe(data => {
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
  nazivSale: string;
}

export interface OdabranaSala {
  id: number;
  nazivSale: string;
}
