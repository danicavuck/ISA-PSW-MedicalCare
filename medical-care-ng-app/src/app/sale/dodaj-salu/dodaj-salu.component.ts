import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dodaj-salu',
  templateUrl: './dodaj-salu.component.html',
  styleUrls: ['./dodaj-salu.component.css']
})
export class DodajSaluComponent implements OnInit {
  private models: Array<KlinikaDTO>;
  private hasError = false;
  private errorStatus = 'Neispravni podaci';
  sala: SalaDTO = {
    nazivSale: '',
    klinika: 0
  };

  constructor(private http: HttpClient) {
    this.getKlinike();
   }

  ngOnInit() {
  }

  async onSubmit() {
    const apiEndpoint = 'http://localhost:8080/sale';
    this.http.post(apiEndpoint, this.sala,
      {responseType: 'text', withCredentials: true}).subscribe( data => {
        console.log('Uspesno dodavanje sale:' + this.sala.nazivSale + this.sala.klinika);
    }, err => {
        console.log(err);
    });
  }

  async getKlinike() {
    const apiEndpoint = 'http://localhost:8080/klinika/klinike';

    this.http.get(apiEndpoint,
      {responseType: 'json', withCredentials: true}).subscribe((data) => {
        this.models = data as Array<KlinikaDTO>;
        console.log(this.models);
      }, err => {
        console.log('Greska admin_klinike: ');
        console.log(err);
      });
  }
}


export interface KlinikaDTO {
  id: number;
  naziv: string;
  opis: string;
  adresa: string;
}

export interface SalaDTO {
  nazivSale: string;
  klinika: number;
}
