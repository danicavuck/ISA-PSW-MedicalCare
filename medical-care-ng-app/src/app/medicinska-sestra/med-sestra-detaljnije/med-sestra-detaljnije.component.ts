import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-med-sestra-detaljnije',
  templateUrl: './med-sestra-detaljnije.component.html',
  styleUrls: ['./med-sestra-detaljnije.component.css']
})
export class MedSestraDetaljnijeComponent implements OnInit {
  private model: MedicinskaSestra = {
    ime: '',
    prezime: '',
    email: '',
    staraLozinka: '',
    novaLozinka: ''
  };
  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  async izmeniPodatke() {
    const apiEndpoint = 'http://localhost:8080/medsestra/azurirajPodatke';
    this.http.put(apiEndpoint, this.model, {withCredentials: true, responseType: 'json'}).subscribe( data => {
      console.log('Uspesno izmenjeni podaci');
    }, err => {
      console.log('Neuspesno izmenjeni podaci');
    });
  }
}

export interface MedicinskaSestra {
  ime: string;
  prezime: string;
  email: string;
  staraLozinka: string;
  novaLozinka: string;
}
