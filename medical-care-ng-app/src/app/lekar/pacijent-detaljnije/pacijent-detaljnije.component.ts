import { Component, OnInit, ViewChild } from '@angular/core';
import { PacijentServiceComponent } from 'src/app/services/pacijent-service/pacijent-service.component';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { IzvestajServiceComponent } from 'src/app/services/izvestaj-service/izvestaj-service.component';
import { Router } from '@angular/router';

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
  private izvestajiColumns : string[] = ['Ime pacijenta','Prezime pacijenta','Email pacijenta','Akcije']
  @ViewChild(MatSort, {static: false}) izvestajiSort: MatSort;
  @ViewChild(MatPaginator, {static: false}) izvestajiPaginator: MatPaginator;
  private izvestajiDataSource;
  private izvestaji : Array<IzvestajDTO>;
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
  constructor(private router:Router,private pacijentService: PacijentServiceComponent, private http: HttpClient,private service:IzvestajServiceComponent) {
    this.pacijentID = pacijentService.getPacijentID();
    this.lekarID = pacijentService.getLekarID();
    this.getPacijentData();
    this.getPreglediData();
    this.getIzvestajeInitialy();
  }

  ngOnInit() {
  }

  async getPacijentData() {
    if (this.pacijentID !== 0 && this.pacijentID !== undefined) {
      const apiEnpoint = 'http://localhost:8080/pacijenti/pacijent/' + this.pacijentID;
      this.http.get(apiEnpoint, {withCredentials: true}).subscribe((data) => {
        this.pacijent = data as Pacijent;
      }, err => {
        console.log('Greska pri dobavljanju informacija o pacijentu');
      });
    }
  }
  async getIzvestajeInitialy() {
    if (this.pacijentID !== 0 && this.pacijentID !== undefined){
    const apiEndpoint = 'http://localhost:8080/izvestaj/dobavi/ ' + this.pacijentID;
    this.http.get(apiEndpoint,
      {responseType: 'json', withCredentials: true}).subscribe((data) => {
        this.izvestaji = data as Array<IzvestajDTO>;
        this.izvestajiDataSource = new MatTableDataSource(this.izvestaji);
        this.izvestajiDataSource.sort = this.izvestajiSort;
        this.izvestajiDataSource.paginator = this.izvestajiPaginator;
      }, err => {
        console.log('Greska pri pribavljanju izvestaja! ');
        console.log(err);
      });
    }
  }

  async editIzvestaj(izvestaj : IzvestajDTO){
    this.service.setIzvestajId(izvestaj.id);
    this.router.navigateByUrl('lekar/zakazivanje/izmenaIzvestaja')
  }


  async getPreglediData() {
    if (this.pacijentID !== 0 && this.pacijentID !== undefined) {
      const apiEnpoint = 'http://localhost:8080/pregledi/pacijent/' + this.pacijentID;
      this.http.get(apiEnpoint, {withCredentials: true}).subscribe((data) => {
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

export interface IzvestajDTO{
  id : number;
  imePacijenta : string;
  prezimePacijenta : string;
  emailPacijenta : string;
  idPacijent : number;
}
