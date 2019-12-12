import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.css']
})
export class SaleComponent implements OnInit {

  sale: Array<SalePretraga>;
  constructor(private http: HttpClient) {
    this.getSaleInitialy();
   }

  ngOnInit() {
  }

  async onSubmit() {
    console.log('Click');
  }

  async getSaleInitialy(){
    const apiEndpoint = 'http://localhost:8080/sale';

    this.http.get(apiEndpoint,
      {responseType: 'json'}).subscribe((data) => {
        this.sale = data as Array<SalePretraga>;
        console.log(this.sale);
      }, err => {
        console.log('Greska pri pribavljanju sala: ');
        console.log(err);
      });
  }

}

export interface SalePretraga {
  brojSale: number;
  datum: Date;
  pocetakTermina: Date;
  krajTermina: Date;
}
