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
  private profit = 0;
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
      this.profit = data as number;
      this.dobijenOdgovor = true;
      this.snackBar.open('Prihod klinike za odabrani period je ' + this.profit + ' RSD', 'X', {duration: 15000});
      console.log(this.profit);
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
