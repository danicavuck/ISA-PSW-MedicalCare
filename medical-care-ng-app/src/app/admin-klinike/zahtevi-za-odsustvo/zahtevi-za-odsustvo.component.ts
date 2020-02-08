import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatTableDataSource, MatDialog } from '@angular/material';
import { BrisanjeOdsustvaComponent } from 'src/app/dialozi/brisanje-odsustva/brisanje-odsustva.component';
import { ZahtevServiceService } from 'src/app/services/zahtev-service.service';

@Component({
  selector: 'app-zahtevi-za-odsustvo',
  templateUrl: './zahtevi-za-odsustvo.component.html',
  styleUrls: ['./zahtevi-za-odsustvo.component.css']
})
export class ZahteviZaOdsustvoComponent implements OnInit {
  private zahteviColumns: string[] = ['lekar', 'pocetakOdsustva', 'krajOdsustva', 'Akcije'];
  private zahtevi: Array<Zahtev>;
  private zahteviDataSource;
  private postojeZahtevi = false;
  // tslint:disable-next-line: max-line-length
  constructor(private http: HttpClient, private zahteviComponent: BrisanjeOdsustvaComponent, private zahtevDialog: MatDialog, private zahtevService: ZahtevServiceService) {
  }

  ngOnInit() {
    this.getZahteveInitially();
  }

  async getZahteveInitially() {
    const apiEndpoint = 'http://localhost:8080/odsustva';
    this.http.get(apiEndpoint, {responseType: 'json', withCredentials: true}).subscribe(data => {
      this.zahtevi = data as Array<Zahtev>;
      this.zahteviDataSource = new MatTableDataSource(this.zahtevi);
      if (this.zahtevi.length > 0) {
        this.postojeZahtevi = true;
      }
      console.log('Uspesno pribavljanje zahteva');
    }, err => {
      console.log('Neuspesno pribavljanje zahteva');
    });
  }

  async potvrdiZahtev(zahtev) {
    const apiEndpoint = 'http://localhost:8080/odsustva/odobravanje';
    this.http.post(apiEndpoint, zahtev, {withCredentials: true}).subscribe(data => {
      console.log('Zahtev odobren');
      this.getZahteveInitially();
    }, err => {
      console.log('Neuspeno odobravanje');
    });
  }

  async obrisiZahtev(zahtev: Zahtev) {
    const apiEndpoint = 'http://localhost:8080/odsustva/brisanje';
    this.http.post(apiEndpoint, zahtev, {withCredentials: true}).subscribe(data => {
      console.log('Uspesno brisanje');
      this.ngOnInit();
    }, err => {
      console.log('Neuspesno brisanje');
    });
  }

  async openDialogZahtev(zahtev: Zahtev) {
    const odgovor = this.zahtevDialog.open(BrisanjeOdsustvaComponent);
    odgovor.afterClosed().subscribe(result => {
      if (result === 'true') {
        zahtev.opis = this.zahtevService.getOpis();
        this.obrisiZahtev(zahtev);
      }
    });
  }

}

export interface Zahtev {
  idOdsustva: number;
  pocetakOdsustva: string;
  krajOdsustva: string;
  lekar: string;
  opis: string;
}
