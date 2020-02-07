import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';

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
  constructor(private http: HttpClient, private snackBar: MatSnackBar) {
    this.dobaviPodatkeOAdminu();
  }

  ngOnInit() {
  }

  async dobaviPodatkeOAdminu() {
    const apiEndpoint = 'http://localhost:8080/adminklinike/detalji';
    this.http.get(apiEndpoint, {withCredentials: true}).subscribe(data => {
      this.model = data as AdminKlinike;
      console.log('Dobavljeni podaci');
    }, err => {
      console.log('Neuspesno dobavljanje podataka o adminu');
    });
  }

  async izmeniPodatke() {
    const apiEndpoint = 'http://localhost:8080/adminklinike';
    this.http.put(apiEndpoint, this.model, {withCredentials: true}).subscribe(data => {
      this.snackBar.open('Uspena izmena podataka', 'X', {duration: 5000});
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
