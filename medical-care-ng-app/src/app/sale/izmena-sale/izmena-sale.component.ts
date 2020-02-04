import { Component, OnInit } from '@angular/core';
import { SalaServiceService } from 'src/app/services/sala-service.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-izmena-sale',
  templateUrl: './izmena-sale.component.html',
  styleUrls: ['./izmena-sale.component.css']
})
export class IzmenaSaleComponent implements OnInit {
  private model: Sala = {
    id: 0,
    nazivSale: ''
  };
  constructor(private salaService: SalaServiceService, private http: HttpClient) {
   }

  ngOnInit() {
    this.model = this.salaService.getSala();
  }
  async izmeniPodatke() {
    const apiEndpoint = 'http://localhost:8080/sale';
    this.http.put(apiEndpoint, this.model, {withCredentials: true}).subscribe(data => {
      console.log('Uspesna izmena');
    }, err => {
      console.log('Izmena nije dozvoljena');
    });
  }
}

export interface Sala {
  id: number;
  nazivSale: string;
}
