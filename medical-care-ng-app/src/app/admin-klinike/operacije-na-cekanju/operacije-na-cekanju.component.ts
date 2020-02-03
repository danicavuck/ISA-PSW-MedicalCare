import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-operacije-na-cekanju',
  templateUrl: './operacije-na-cekanju.component.html',
  styleUrls: ['./operacije-na-cekanju.component.css']
})
export class OperacijeNaCekanjuComponent implements OnInit {
  private operacijeColumns: string[] = ['pocetakTermina', 'krajTermina', 'sale', 'Akcije'];
  private postojeOperacije = false;
  private sale: Array<Sala>;
  private naziviSala: Array<string> = [];
  private odabranaSala: Sala = {
    id: 0,
    nazivSale: ''
  };
  private slanje: SalaOperacija = {
    salaId: 0,
    operacijaId: 0
  };
  private operacije: Array<Operacija>;
  private odabranaSalaZaSlanje: OdabranaSala = {
    id: 0,
    nazivSale: ''
  };
  constructor(private http: HttpClient) {
    this.getSaleInitially();
    this.getOperacijeInitialy();
   }

  ngOnInit() {
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

  async dodajSaluOperaciji(operacija: Operacija) {
    this.slanje.operacijaId = operacija.id;
    this.slanje.salaId = this.odabranaSala.id;
    // console.log(this.slanje);
    const apiEnpoint = 'http://localhost:8080/operacije';
    this.http.put(apiEnpoint, this.slanje, {responseType: 'json', withCredentials: true}).subscribe(data => {
    console.log('Uspesno dodeljena sala');
    this.getOperacijeInitialy();
  }, err => {
    console.log('Greska pri dodeljivanju sala');
  });
  }

  getOperacijeInitialy() {
    const apiEnpoint = 'http://localhost:8080/operacije';
    this.http.get(apiEnpoint, {withCredentials: true}).subscribe(data => {
      this.operacije = data as Array<Operacija>;
      if (this.operacije.length > 0) {
        this.postojeOperacije = true;
      }
    }, err => {
      console.log('Neuspesno dobavljanje operacija');
    });
  }

}

export interface Sala {
  id: number;
  nazivSale: string;
}

export interface OdabranaSala {
  id: number;
  nazivSale: string;
}

export interface Operacija {
  id: number;
  pocetakTermina: string;
  krajTermina: string;
  imeLekara: string;
  prezimeLekara: string;
}

export interface SalaOperacija {
  salaId: number;
  operacijaId: number;
}

