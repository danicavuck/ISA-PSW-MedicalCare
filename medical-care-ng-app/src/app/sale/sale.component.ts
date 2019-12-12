import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.css']
})
export class SaleComponent implements OnInit {

  sala: SalePretraga = {
    naziv: '',
    datum: null
  };
  constructor() { }

  ngOnInit() {
  }

  async onSubmit() {
    console.log(this.sala.naziv);
    console.log(this.sala.datum);
  }

}

export interface SalePretraga {
  naziv: string;
  datum: Date;
}
