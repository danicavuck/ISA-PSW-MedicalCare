import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatTableDataSource } from '@angular/material';

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
  constructor(private http: HttpClient) {
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
    console.log('Potvrda ' + zahtev.pocetakOdsustva);
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

}

export interface Zahtev {
  idOdsustva: number;
  pocetakOdsustva: string;
  krajOdsustva: string;
  lekar: string;
}
