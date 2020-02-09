import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { Router } from '@angular/router';
import { IzvestajServiceComponent } from 'src/app/services/izvestaj-service/izvestaj-service.component';

@Component({
  selector: 'app-pregled-i-operacije',
  templateUrl: './pregled-i-operacije.component.html',
  styleUrls: ['./pregled-i-operacije.component.css']
})
export class PregledIOperacijeComponent implements OnInit {
  private preglediColumns: string[] = ['Broj Sale', 'Tip pregleda', 'Pocetak pregleda', 'Kraj pregleda', 'Lekar', 'Cena', 'Akcije'];
  private preglediDataSource; 
  private izvestajiColumns : string[] = ['Ime pacijenta','Prezime pacijenta','Email pacijenta','Akcije']
  private izvestajiDataSource;
  private izvestaji : Array<IzvestajDTO>;
  private pregledi: Array<PregledDTO>;
  @ViewChild(MatSort, {static: false}) izvestajiSort: MatSort;
  @ViewChild(MatPaginator, {static: false}) izvestajiPaginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) preglediSort: MatSort;
  @ViewChild(MatPaginator, {static: false}) preglediPaginator: MatPaginator;
  constructor(private http: HttpClient, private router:Router, private service:IzvestajServiceComponent) { 
    this.getPregledeInitialy();
    
  }

  ngOnInit() {
  }

  async getPregledeInitialy() {
    const apiEndpoint = 'http://localhost:8080/pregledi';
    this.http.get(apiEndpoint,
      {responseType: 'json', withCredentials: true}).subscribe((data) => {
        this.pregledi = data as Array<PregledDTO>;
        this.preglediDataSource = new MatTableDataSource(this.pregledi);
        this.preglediDataSource.sort = this.preglediSort;
        this.preglediDataSource.paginator = this.preglediPaginator;
      }, err => {
        console.log('Greska pri pribavljanju pregleda! ');
        console.log(err);
      });
  }
 

  async zapocniPregled(pregled:PregledDTO) {
   
    this.service.setLekarID(pregled.lekar);
    console.log(pregled.pacijent);
    this.service.setPacijentID(pregled.pacijent)
    this.router.navigateByUrl('/lekar/izvestaj')
  }

  async editIzvestaj(izvestaj : IzvestajDTO){
    this.service.setIzvestajId(izvestaj.id);
    this.router.navigateByUrl('lekar/zakazivanje/izmenaIzvestaja')
  }


}



export interface PregledDTO {
  pacijent : number;
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


export interface IzvestajDTO{
  id : number;
  imePacijenta : string;
  prezimePacijenta : string;
  emailPacijenta : string;
  idPacijent : number;
}
