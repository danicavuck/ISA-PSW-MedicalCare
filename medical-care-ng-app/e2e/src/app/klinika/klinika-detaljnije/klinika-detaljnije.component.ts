import { Component, OnInit, ViewChild } from '@angular/core';
import { KlinikaServiceComponent } from 'src/app/services/klinika-service/klinika-service.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog, MatSnackBar, MatSort, MatTableDataSource, MatPaginator } from '@angular/material';
import { SalaDialogComponent } from 'src/app/dialozi/sala-dialog/sala-dialog.component';
import { LekarDialogComponent } from 'src/app/dialozi/lekar-dialog/lekar-dialog.component';


@Component({
  selector: 'app-klinika-detaljnije',
  templateUrl: './klinika-detaljnije.component.html',
  styleUrls: ['./klinika-detaljnije.component.css']
})

export class KlinikaDetaljnijeComponent implements OnInit {
  private displayColumns: string[] = ['nazivSale', 'Akcije'];
  private lekariColumns: string[] = ['ime', 'prezime', 'prosecnaOcena', 'email', 'Akcije'];
  private preglediColumns: string[] = ['Broj Sale', 'Tip pregleda', 'Pocetak pregleda', 'Kraj pregleda', 'Lekar', 'Akcije'];

  private lekariDataSource;
  private saleDataSource;
  private preglediDataSource;

  private pregledi: Array<Pregled>;
  private klinika: KlinikaDTO = {
    id: 0,
    naziv: '',
    opis: '',
    adresa: '',
    prosecnaOcena: 0
  };
  private klinikaStara: KlinikaDTO;
  private sale: Array<SalePretraga>;
  lekari: Array<Lekar>;

  @ViewChild(MatSort, {static: false}) lekariSort: MatSort;
  @ViewChild(MatSort, {static: false}) saleSort: MatSort;
  @ViewChild(MatSort, {static: false}) preglediSort: MatSort;
  @ViewChild(MatPaginator, {static: false}) lekariPaginator: MatPaginator;
  @ViewChild(MatPaginator, {static: false}) salePaginator: MatPaginator;
  @ViewChild(MatPaginator, {static: false}) preglediPaginator: MatPaginator;

  // tslint:disable-next-line: max-line-length
  constructor(private dataService: KlinikaServiceComponent, private http: HttpClient, private salaDialog: MatDialog, private snackBar: MatSnackBar,
              private lekarDialog: MatDialog) {
    if (this.dataService.getData() !== undefined) {
      this.klinika = this.dataService.getData();
      this.klinikaStara = this.klinika;
    }

    // Metode
    this.getLekareInitialy();
    this.getSaleInitialy();
    this.getPregledeInitialy();
   }

  ngOnInit() {
  }

  async isInvalid() {
    // tslint:disable-next-line: max-line-length
    return (this.klinika.naziv === this.klinikaStara.naziv) && (this.klinika.opis === this.klinikaStara.opis) && (this.klinika.adresa === this.klinikaStara.adresa);
  }
  async getLekareInitialy() {
    const apiEndpoint = 'http://localhost:8080/lekari';

    this.http.get(apiEndpoint,
      {responseType: 'json', withCredentials: true}).subscribe((data) => {
        this.lekari = data as Array<Lekar>;
        this.lekariDataSource = new MatTableDataSource(this.lekari);
        this.lekariDataSource.sort = this.lekariSort;
        this.lekariDataSource.paginator = this.lekariPaginator;
      }, err => {
        console.log('Greska pri pribavljanju lekara: ');
        console.log(err);
      });
  }

  async getPregledeInitialy() {
    const apiEndpoint = 'http://localhost:8080/pregledi';
    this.http.get(apiEndpoint,
      {responseType: 'json', withCredentials: true}).subscribe((data) => {
        this.pregledi = data as Array<Pregled>;
        this.preglediDataSource = new MatTableDataSource(this.pregledi);
        this.preglediDataSource.sort = this.preglediSort;
        this.preglediDataSource.paginator = this.preglediPaginator;
      }, err => {
        console.log('Greska pri pribavljanju pregleda! ');
        console.log(err);
      });
  }



  async getSaleInitialy() {
    const apiEndpoint = 'http://localhost:8080/sale';

    this.http.get(apiEndpoint,
      {responseType: 'json', withCredentials: true}).subscribe((data) => {
        this.sale = data as Array<SalePretraga>;
      }, err => {
        console.log('Greska pri pribavljanju sala: ');
        console.log(err);
      });
  }

  async onSubmit() {
    const apiEndpoint = 'http://localhost:8080/klinika';

    this.http.put(apiEndpoint, this.klinika, {responseType: 'text', withCredentials: true}).subscribe((data) => {
    }, err => {
      console.log('Greska');
      console.log(err);
    });

    console.log(this.klinika);
  }

  async obrisiSalu(sala) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: sala,
      withCredentials: true
    };
    const apiEndpoint = 'http://localhost:8080/sale';
    this.http.delete(apiEndpoint, options).subscribe((data) => {
      console.log('Uspenso brisanje klinike');
      this.snackBar.open('Sala izbrisana', 'X', {duration: 2000});
      this.getSaleInitialy();
    }, err => {
      this.snackBar.open('Ne moze se izbrisati sala koja ima aktivne preglede', 'X', {duration: 2000});
    });
  }

  async openDialog(sala) {
    const odgovor = this.salaDialog.open(SalaDialogComponent);
    odgovor.afterClosed().subscribe(result => {
      if (result === 'true') {
        this.obrisiSalu(sala);
      }
    });
  }

  async obrisiPregled(pregled) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: pregled,
      withCredentials: true
    };
    const apiEndpoint = 'http://localhost:8080/pregledi';
    this.http.delete(apiEndpoint, options).subscribe((data) => {
      console.log('Uspenso brisanje pregleda');
      this.snackBar.open('Pregled izbrisan', 'X', {duration: 2000});
      this.getPregledeInitialy();
    }, err => {
      this.snackBar.open('Neuspesno brisanje pregleda', 'X', {duration: 2000});
    });
  }


  async openLekarDialog(lekar) {
    const odgovor = this.lekarDialog.open(LekarDialogComponent);
    odgovor.afterClosed().subscribe(result => {
      if (result === 'true') {
        this.obrisiLekara(lekar);
        this.snackBar.open('Lekar izbrisan', 'X', {duration: 2000});
      } else {
        console.log('Ne moze da se brise: ' + lekar);
      }
    });
  }

  async obrisiLekara(lekar) {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: lekar,
      withCredentials: true
    };
    const apiEndpoint = 'http://localhost:8080/lekari';
    this.http.delete(apiEndpoint, options).subscribe((data) => {
      console.log('Uspenso brisanje lekara');
      this.getLekareInitialy();
    }, err => {
      console.log(err);
    });
  }

  async openSnackBar(message, action) {
    this.snackBar.open(message, action);
  }
}

export interface KlinikaDTO {
  id: number;
  naziv: string;
  opis: string;
  adresa: string;
  prosecnaOcena: number;
}

export interface Lekar {
  id: number;
  ime: string;
  prezime: string;
  email: string;
  prosecnaOcena: number;
}

export interface SalePretraga {
  brojSale: number;
  id: number;
}

export interface Pregled {
  datumVreme: Date;
  trajanjePregleda: number;
  cena: number;
  popust: number;
  sala: string;
  tipPregleda: string;
  lekar: number;
  lekarImeIPrezime: string;
  pocetakTermina: string;
  krajTermina: string;
}

