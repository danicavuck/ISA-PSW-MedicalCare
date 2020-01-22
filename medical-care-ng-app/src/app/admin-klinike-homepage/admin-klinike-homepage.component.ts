import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import { KlinikaServiceComponent } from '../services/klinika-service/klinika-service.component';

@Component({
  selector: 'app-admin-klinike-homepage',
  templateUrl: './admin-klinike-homepage.component.html',
  styleUrls: ['./admin-klinike-homepage.component.css'],
})
export class AdminKlinikeHomepageComponent implements OnInit {
  private displayColumns: string[] = ['Naziv', 'Adresa', 'Opis', 'Akcije'];
  private models: Array<KlinikaDTO>;
  private klinika: KlinikaDTO;
  constructor(private router: Router, private http: HttpClient, private dataService: KlinikaServiceComponent) { }

  ngOnInit() {
    this.getTestKlinike();
  }

  async onDodajKliniku() {
    this.router.navigateByUrl('/dodavanjeklinike');
    console.log('click');
  }

  async onDetaljnije(klinika) {
    this.klinika = klinika;
    console.log('Prosledjena klinika: ');
    console.log(this.klinika);
    this.dataService.setData(this.klinika);
  }

  async getTestKlinike() {
    const apiEndpoint = 'http://localhost:8080/adminklinike/klinike';

    this.http.get(apiEndpoint,
      {responseType: 'json'}).subscribe((data) => {
        this.models = data as Array<KlinikaDTO>;
        console.log(this.models);
      }, err => {
        console.log('Greska admin_klinike: ');
        console.log(err);
      });
  }

  async onLogout() {
    const apiEndpoint = 'http://localhost:8080/odjava';
    this.http.post(apiEndpoint, {responseType: 'json'}, {withCredentials: true}).subscribe(() => {
        console.log('Uspesno odjavljivanje');
      }, err => {
        console.log('Neuspesno odjavljivanje: ');
        console.log(err);
      });
  }
}

export interface KlinikaDTO {
  id: number;
  naziv: string;
  opis: string;
  adresa: string;
}


