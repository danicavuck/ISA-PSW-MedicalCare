import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dodavanje-tipa-pregleda',
  templateUrl: './dodavanje-tipa-pregleda.component.html',
  styleUrls: ['./dodavanje-tipa-pregleda.component.css']
})
export class DodavanjeTipaPregledaComponent implements OnInit {
  private model: TipPregleda = {
    tipPregleda: '',
  };

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  async onSubmit(){
    const apiEndpoint = 'http://localhost:8080/tippregleda';
    this.http.post(apiEndpoint, this.model, {responseType: 'text'}).subscribe((data) => {
      console.log('Uspeh');
    }, err => {
      console.log('Greska pri pribavljanju sala: ');
      console.log(err);
      console.log(this.model);
    });
  }

}

export interface TipPregleda {
  tipPregleda: string;
}
