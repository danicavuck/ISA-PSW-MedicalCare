import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { SalaDialogComponent } from '../dialozi/sala-dialog/sala-dialog.component';
import { MatSnackBar, MatDialog, MatTableDataSource, MatInputModule } from '@angular/material';


@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.css']
})
export class SaleComponent implements OnInit {

  private displayColumns: string[] = ['Broj Sale', 'Pocetak termina', 'Kraj termina', 'Akcije'];
  filtriraj = false;
  sale: Array<SalePretraga>;
  private saleDataSource;
  // private nadjenaSalaDataSource;
  nadjenaSala: SalePretraga;
  sala: SalaDTO = {
    brojSale: 0,
    datum: null,
    pocetakTermina: null,
    krajTermina: null
  };
  salaPretraga: SalaDTO = {
    brojSale: 101,
    datum: null,
    pocetakTermina: null,
    krajTermina: null
  };
  constructor(private http: HttpClient, private salaDialog: MatDialog, private snackBar: MatSnackBar) {
    this.filtriraj = false;
    this.getSaleInitialy();
   }

  ngOnInit() {
  }

  async pretraziSale() {
    const apiEndpoint = 'http://localhost:8080/sale/pretraga';

    this.http.post(apiEndpoint, this.salaPretraga, {responseType: 'json'}).subscribe((data) => {
        this.nadjenaSala = data as SalePretraga;
        //this.nadjenaSalaDataSource = new MatTableDataSource(this.nadjenaSala);
        this.filtriraj = true;
        console.log(this.filtriraj);
        console.log(this.nadjenaSala);
      }, err => {
        console.log('Greska pri pribavljanju sala: ');
        console.log(err);
        console.log(this.salaPretraga);
      });
  }

  async getSaleInitialy() {
    const apiEndpoint = 'http://localhost:8080/sale';
    this.filtriraj = false;

    this.http.get(apiEndpoint,
      {responseType: 'json'}).subscribe((data) => {
        this.sale = data as Array<SalePretraga>;
        this.saleDataSource = new MatTableDataSource(this.sale);
        console.log(this.sale);
      }, err => {
        console.log('Greska pri pribavljanju sala: ');
        console.log(err);
      });
  }

  async obrisiSalu(sala) {
    console.log(sala);

    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: sala,
    };
    const apiEndpoint = 'http://localhost:8080/sale';
    this.http.delete(apiEndpoint, options).subscribe((data) => {
      console.log('Uspenso brisanje klinike');
      this.getSaleInitialy();
    }, err => {
      console.log(err);
    });
  }


  async openDialog(sala) {
    const odgovor = this.salaDialog.open(SalaDialogComponent);
    odgovor.afterClosed().subscribe(result => {
      if (result === 'true') {
        this.obrisiSalu(sala);
        this.snackBar.open('Sala izbrisana', 'X', {duration: 2000});
      }
    });

  }

  async applyFilter(filterValue: string) {
    this.saleDataSource.filter = filterValue.trim().toLowerCase();
  }
}

export interface SalePretraga {
  brojSale: number;
  pocetakTermina: Date;
  krajTermina: Date;
}

export interface SalaDTO {
  brojSale: number;
  datum: Date;
  pocetakTermina: Date;
  krajTermina: Date;
}
