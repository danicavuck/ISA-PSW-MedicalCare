import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dodavanje-operacija',
  templateUrl: './dodavanje-operacija.component.html',
  styleUrls: ['./dodavanje-operacija.component.css']
})
export class DodavanjeOperacijaComponent implements OnInit {
  operacija: Operacija = {
    datumVreme: new Date(),
    trajanjeOperacije: 0,
    pacijentId: 0
  };
  pacijenti: Array<Pacijent>;
  pacijent = 0;
  dateAndTime: Date = new Date();
  trajanjeOperacije = 0;
  settings = {
    bigBanner: true,
        timePicker: true,
    format: 'dd-MM-yyyy',
        defaultOpen: true
  };

  constructor(private http: HttpClient) {
    this.dateAndTime = new Date();
    this.getPacijenteInitialy();
  }

  ngOnInit() {
  }

  async getPacijenteInitialy() {
    const apiEndpoint = 'http://localhost:8080/pacijenti/klinika';

    this.http.get(apiEndpoint,
      {responseType: 'json', withCredentials: true}).subscribe((data) => {
        this.pacijenti = data as Array<Pacijent>;
      }, err => {
        console.log('Greska pri pribavljanju pacijenata: ');
        console.log(err);
      });
  }

  async onSubmit() {
    this.setovanjeVrednosti();
    const apiEndpoint = 'http://localhost:8080/operacije';

    this.http.post(apiEndpoint, this.operacija, {withCredentials: true}).subscribe(data => {
      console.log('Uspesno slanje zahteva');
    }, err => {
      console.log('Neuspesno slanje zahteva');
    });
  }

  async setovanjeVrednosti() {
    this.operacija.datumVreme = this.dateAndTime;
    this.operacija.datumVreme.setHours(this.dateAndTime.getHours() + 1);
    this.operacija.pacijentId = this.pacijent;
    this.operacija.trajanjeOperacije = this.trajanjeOperacije;
  }

}

export interface Pacijent {
  id: number;
  ime: string;
  prezime: string;
}

export interface Operacija {
  datumVreme: Date;
  trajanjeOperacije: number;
  pacijentId: number;
}
