import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dodavanje-lekara',
  templateUrl: './dodavanje-lekara.component.html',
  styleUrls: ['./dodavanje-lekara.component.css']
})
export class DodavanjeLekaraComponent implements OnInit {
  private model: Lekar = {
    ime : '',
    prezime: '',
    email: '',
    lozinka: ''
  };
  private lozinkaPonovo: string = null;
  errorStatus: string = null;
  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
  }

  async onSubmit() {
    if (this.isFormValid()) {
      const apiEndpoint = 'http://localhost:8080/lekari';

      this.http.post(apiEndpoint, this.model,
        {responseType: 'text'}).subscribe( data => {
          console.log('Uspesno dodavanje lekara');
          this.router.navigateByUrl('/adminklinike');
        }, error => {
          this.errorStatus = '';
          this.errorStatus = 'E-mail adresa je vec zauzeta';
        });
    } else {
      console.log('Forma nije validna');
    }
  }

  isFormValid() {
    // tslint:disable-next-line: max-line-length
    if (this.model.ime !== '' && this.model.prezime !== '' && this.model.email !== '' && this.model.lozinka !== '' && this.passwordsMatch()) {
      return true;
    }

    return false;
  }

  resetovanjeForme() {
    this.model.ime = '';
    this.model.prezime = '';
    this.model.email = '';
    this.model.lozinka = '';
    this.lozinkaPonovo = '';
  }

  passwordsMatch() {
    return (this.model.lozinka === this.lozinkaPonovo) && this.model.lozinka.length >= 6;
  }

}

export interface Lekar {
  ime: string;
  prezime: string;
  email: string;
  lozinka: string;
}
