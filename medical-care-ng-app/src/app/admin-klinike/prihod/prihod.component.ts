import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-prihod',
  templateUrl: './prihod.component.html',
  styleUrls: ['./prihod.component.css']
})
export class PrihodComponent implements OnInit {
  private dobijenOdgovor = false;
  private uvid: Uvid = {
    profit: 0,
    brojPregleda: 0
  };
  private prihod: Prihod = {
    datumVreme: [new Date()]
  };
  settings = {
    bigBanner: true,
        timePicker: false,
    format: 'dd-MM-yyyy',
        defaultOpen: true
  };
  constructor(private http: HttpClient, private snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  async onSubmit() {
    const apiEndpoint = 'http://localhost:8080/klinika/prihodi';
    this.inkrementDatuma();
    this.http.post(apiEndpoint, this.prihod, {withCredentials: true}).subscribe(data => {
      this.uvid = data as Uvid;
      this.dobijenOdgovor = true;
      // tslint:disable-next-line: max-line-length
      this.snackBar.open('Prihod klinike za odabrani period je ' + this.uvid.profit + ' RSD za ukupno ' + this.uvid.brojPregleda + ' pregleda' , 'X', {duration: 15000});
    }, err => {
      console.log('Neuspesno slanje zahteva za prihod');
    });
  }

  async inkrementDatuma() {
    this.prihod.datumVreme[0].setDate(this.prihod.datumVreme[0].getDate() + 1);
    this.prihod.datumVreme[1].setDate(this.prihod.datumVreme[1].getDate() + 1);
  }

}

export interface Prihod {
  datumVreme: Array<Date>;
}

export interface Uvid {
  profit: number;
  brojPregleda: number;
}
