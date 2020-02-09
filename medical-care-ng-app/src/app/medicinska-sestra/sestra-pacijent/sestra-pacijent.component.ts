import { Component, OnInit, ViewChild } from '@angular/core';
import { SestraPacijentServiceComponent } from 'src/app/services/sestra-pacijent-service/sestra-pacijent-service.component';
import { HttpClient } from '@angular/common/http';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { Router } from '@angular/router';
import { IzvestajServiceComponent } from 'src/app/services/izvestaj-service/izvestaj-service.component';

@Component({
  selector: 'app-sestra-pacijent',
  templateUrl: './sestra-pacijent.component.html',
  styleUrls: ['./sestra-pacijent.component.css']
})
export class SestraPacijentComponent implements OnInit {
  private postojePregledi = false;
  private preglediDataSource;
  private pacijentID = 0;
  private sestraID = 0;
 
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
  constructor(private pacijentService: SestraPacijentServiceComponent, private service:IzvestajServiceComponent,private http: HttpClient,private router:Router) {
    this.pacijentID = pacijentService.getPacijentID();
    this.sestraID = pacijentService.getSestraID();
    this.getPacijentData();
    
    
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

