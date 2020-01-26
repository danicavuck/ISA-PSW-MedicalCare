import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-lekar-detaljnije',
  templateUrl: './lekar-detaljnije.component.html',
  styleUrls: ['./lekar-detaljnije.component.css']
})
export class LekarDetaljnijeComponent implements OnInit {
  private model: Lekar = {
    ime: '',
    prezime: '',
    email: '',
    staraLozinka: '',
    novaLozinka: ''
  };
  private potvrdaLozinke: '';
  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  async izmeniPodatke() {
    const apiEndpoint = 'http://localhost:8080/lekari';
    this.http.put(apiEndpoint, this.model, {withCredentials: true}).subscribe( data => {
      console.log('Uspesno izmenjeni podaci');
    }, err => {
      console.log('Neuspesno izmenjeni podaci');
    });
  }

}

export interface Lekar {
  ime: string;
  prezime: string;
  email: string;
  staraLozinka: string;
  novaLozinka: string;
}
