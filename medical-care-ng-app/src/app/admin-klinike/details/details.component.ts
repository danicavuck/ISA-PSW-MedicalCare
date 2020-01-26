import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {
  private model: AdminKlinike = {
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
    const apiEndpoint = 'http://localhost:8080/adminklinike';
    this.http.put(apiEndpoint, this.model, {withCredentials: true}).subscribe(data => {
      console.log('Uspesna izmena podataka');
    }, err => {
      console.log('Neuspesna izmena podataka');
    });
  }

}

export interface AdminKlinike {
  ime: string;
  prezime: string;
  email: string;
  staraLozinka: string;
  novaLozinka: string;
}
