import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-admin-klinike-homepage',
  templateUrl: './admin-klinike-homepage.component.html',
  styleUrls: ['./admin-klinike-homepage.component.css']
})
export class AdminKlinikeHomepageComponent implements OnInit {

  private models: Array<KlinikaDTO>;

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit() {
    this.getTestKlinike();
  }

  async onDodajKliniku() {
    this.router.navigateByUrl('/dodavanjeklinike');
    console.log('click');
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

  async getLogout() {
    const apiEndpoint = 'http://localhost:8080/logout';

    this.http.get(apiEndpoint).subscribe(() => {
        console.log('Uspesno odjavljivanje');
        this.router.navigate(['/login']);
      }, err => {
        console.log('Neuspesno odjavljivanje: ');
        console.log(err);
      });
  }
}

export interface KlinikaDTO {
  naziv: string;
  opis: string;
  adresa: string;
}
