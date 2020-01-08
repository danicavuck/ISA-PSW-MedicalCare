import { Component, OnInit } from '@angular/core';
import { PacijentServiceComponent } from 'src/app/services/pacijent-service/pacijent-service.component';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-pacijent-detaljnije',
  templateUrl: './pacijent-detaljnije.component.html',
  styleUrls: ['./pacijent-detaljnije.component.css']
})
export class PacijentDetaljnijeComponent implements OnInit {
  private displayColumns: string[] = ['tipPregleda', 'sala', 'pocetakTermina', 'lekarImeIPrezime', 'cena'];
  private postojePregledi = false;
  private preglediDataSource;
  private pacijentID = 0;
  private lekarID = 0;
  private pacijent: Pacijent = {
    id: 0,
    ime: '',
    prezime: '',
    email: '',
    adresa: '',
    grad: '',
    drzava: '',
    brojTelefona: '',
    brojOsiguranja: ''
  };
  private preglediPacijenta: Array<Pregledi>;
  constructor(private pacijentService: PacijentServiceComponent, private http: HttpClient) {
    this.pacijentID = pacijentService.getPacijentID();
    this.lekarID = pacijentService.getLekarID();
    this.getPacijentData();
    this.getPreglediData();
  }

  ngOnInit() {
  }

  async getPacijentData() {
    if (this.pacijentID !== 0 && this.pacijentID !== undefined) {
      const apiEnpoint = 'http://localhost:8080/pacijenti/pacijent/' + this.pacijentID;
      this.http.get(apiEnpoint).subscribe((data) => {
        this.pacijent = data as Pacijent;
      }, err => {
        console.log('Greska pri dobavljanju informacija o pacijentu');
      });
    }
  }

  async getPreglediData() {
    if (this.pacijentID !== 0 && this.pacijentID !== undefined) {
      const apiEnpoint = 'http://localhost:8080/pregledi/pacijent/' + this.pacijentID;
      this.http.get(apiEnpoint).subscribe((data) => {
        this.preglediPacijenta = data as Array<Pregledi>;
        if (this.preglediPacijenta.length !== 0) {
          this.postojePregledi = true;
          this.preglediDataSource = new MatTableDataSource(this.preglediPacijenta);
        }
      }, err => {
        console.log('Greska pri dobavljanju informacija o pregledima Pacijenata');
      });
    }
  }
}

export interface Pacijent {
  id: number;
  ime: string;
  prezime: string;
  email: string;
  adresa: string;
  grad: string;
  drzava: string;
  brojTelefona: string;
  brojOsiguranja: string;
}

export interface Pregledi {
  trajanjePregleda: number;
  cena: number;
  popust: number;
  sala: number;
  tipPregleda: string;
  lekarImeIPrezime: string;
  pocetakTermina: string;
  krajTermina: string;
}
