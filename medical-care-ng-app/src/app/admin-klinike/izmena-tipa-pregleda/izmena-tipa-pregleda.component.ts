import { Component, OnInit } from '@angular/core';
import { TipPregledaService } from 'src/app/services/tip-pregleda.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-izmena-tipa-pregleda',
  templateUrl: './izmena-tipa-pregleda.component.html',
  styleUrls: ['./izmena-tipa-pregleda.component.css']
})
export class IzmenaTipaPregledaComponent implements OnInit {
  private tipPregleda: TipPregleda;
  constructor(private tipPregledaService: TipPregledaService, private http: HttpClient) {
    this.tipPregleda = tipPregledaService.getTipPregleda();
  }

  ngOnInit() {
  }

  async izmeniPodatke() {
    const apiEndpoint = 'http://localhost:8080/tippregleda';
    this.http.put(apiEndpoint, this.tipPregleda, {withCredentials: true}).subscribe(data => {
      console.log('Uspesno slanje zahteva');
    }, err => {
      console.log('Neuspesno slanje zahteva');
    });
  }

}

export interface TipPregleda {
  tipPregleda: string;
  id: number;
}
