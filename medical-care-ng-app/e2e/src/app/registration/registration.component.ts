import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  lozinkaPonovo = '';
  isLoading = false;
  errorStatus: string = null;

  model: RegistrationViewModel = {
    email : '',
    lozinka : '',
    ime : '',
    prezime : '',
    grad : '',
    drzava : '',
    adresaPrebivalista : '',
    telefon : '',
    brojOsiguranja : ''
  };

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
  }

  async onSubmit() {
    this.isLoading = true;
    if (this.performCheck()) {
      const apiEndpoint = 'http://localhost:8080/register';

      this.http.post(apiEndpoint, this.model,
        {responseType: 'text'}).subscribe( data => {
        setTimeout(() => {
          this.router.navigateByUrl('/login');
          this.isLoading = false;
        }, 2000);
      }, err => {
        setTimeout(() => {
          this.isLoading = false;
          switch (err.error) {
            case 'Email address already taken':
              this.errorStatus = 'E-mail adresa je vec zauzeta';
              break;
            case 'Internal server error':
              this.errorStatus = 'Greska na serverskoj strani';
              break;
            default: this.errorStatus = 'Greska pri registraciji';
          }
        }, 1000);

      });
    }
  }

  performCheck() {
    // tslint:disable-next-line: max-line-length
    if (this.model.ime === '' || this.model.prezime === '' || this.model.lozinka === '' || this.model.brojOsiguranja === '' || this.model.telefon === '' || this.model.drzava === '' || this.model.adresaPrebivalista === '' || this.model.email === '') {
      alert('Polja ne smeju biti prazna');
      return false;
    }

    if (this.model.lozinka.length < 6) {
      alert('Lozinka mora sadrzati minimalno 6 karaktera');
      return false;
    }

    if (this.model.lozinka !== this.lozinkaPonovo) {
      alert('Lozinke se moraju podudarati!');
      return false;
    }
    return true;
  }
}


export interface RegistrationViewModel {
  email: string;
  lozinka: string;
  ime: string;
  prezime: string;
  adresaPrebivalista: string;
  grad: string;
  drzava: string;
  telefon: string;
  brojOsiguranja: string;
}
