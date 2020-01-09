import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-odsustva-i-odmor',
  templateUrl: './odsustva-i-odmor.component.html',
  styleUrls: ['./odsustva-i-odmor.component.css']
})
export class OdsustvaIOdmorComponent implements OnInit {
  private min = new Date();
  private odsustvo: Odsustvo = {
    datumVreme: [new Date()]
  };
  settings = {
    bigBanner: true,
        timePicker: false,
    format: 'dd-MM-yyyy',
        defaultOpen: true
  };
  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  async onSubmit() {
    const apiEndpoint = 'http://localhost:8080/odsustva';
    this.inkrementDatuma();
    this.http.post(apiEndpoint, this.odsustvo, {responseType: 'text'}).subscribe(data => {
      console.log('Uspesno slanje zahteva za odsustvo');
    }, err => {
      console.log('Neuspesno slanje zahteva za odsustvo');
    });
  }

  async inkrementDatuma() {
    this.odsustvo.datumVreme[0].setDate(this.odsustvo.datumVreme[0].getDate() + 1);
    this.odsustvo.datumVreme[1].setDate(this.odsustvo.datumVreme[1].getDate() + 1);
  }
}

export interface Odsustvo {
  datumVreme: Array<Date>;
}

