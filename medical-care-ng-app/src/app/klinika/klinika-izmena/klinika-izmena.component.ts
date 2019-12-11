import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-klinika-izmena',
  templateUrl: './klinika-izmena.component.html',
  styleUrls: ['./klinika-izmena.component.css']
})
export class KlinikaIzmenaComponent implements OnInit {

  model : Klinika = {
    naziv : '',
    adresa : '',
    opis : ''
  };

  constructor() { }

  ngOnInit() {
  }

  async onSubmit(){
    console.log(this.model);
  }

}


export interface Klinika {
  naziv : string,
  adresa : string,
  opis : string
}
