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
    novaLozinka: ""
  };
  constructor(private http: HttpClient) {
    this.dobavljanjePodataka();
   }

  ngOnInit() {
  }

  async dobavljanjePodataka() {
    const apiEndpoint = 'http://localhost:8080/medsestra/detalji';
    this.http.get(apiEndpoint, {withCredentials: true}).subscribe(data => {
      this.model = data as MedicinskaSestra;
    }, err => {
      console.log('Neuspesno dobavljanje podataka o medicinksoj sestri');
    });
  }

  async izmeniPodatke() {
    const apiEndpoint = 'http://localhost:8080/medsestra/azurirajPodatke';
    this.http.put(apiEndpoint, this.model, {withCredentials: true, responseType: 'json'}).subscribe( data => {
      console.log('Uspesno izmenjeni podaci');
    }, err => {
      console.log('Neuspesno izmenjeni podaci');
      console.log(this.model)
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
