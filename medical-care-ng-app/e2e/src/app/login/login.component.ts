import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoading = false;
  errorStatus = null;

  model: LoginViewModel = {
    email: '',
    lozinka: ''
  };

  userDTO: UserDTO = {
    id: 0,
    user_email: '',
    role: '',
    prvoLogovanje: false
  };

  constructor(private http: HttpClient, private router: Router) {

  }

  ngOnInit() {
  }


  async onSubmit() {
    this.isLoading = true;

    if (this.performCheck()) {
      const apiEndpoint = 'http://localhost:8080/login';

      this.http.post(apiEndpoint, this.model,
        { responseType: 'json', withCredentials: true }).subscribe(data => {
          setTimeout(() => {
            this.userDTO = data as UserDTO;
            switch (this.userDTO.role) {
              case 'adminkc': this.router.navigateByUrl('/adminkc');
                              break;
              case 'admin_klinike':
                if (this.userDTO.prvoLogovanje === false){
                  this.router.navigateByUrl('/adminklinike');
                } else {
                  this.router.navigateByUrl('/prvologovanje');
                }
                            break;
              case 'lekar':
                if (this.userDTO.prvoLogovanje === false) {
                  this.router.navigateByUrl('/lekar');
                } else {
                  this.router.navigateByUrl('/prvologovanje');
                }
                            break;
              case 'med_sestra':
              if (this.userDTO.prvoLogovanje === false) {
                this.router.navigateByUrl('/medsestra');
              } else {
                this.router.navigateByUrl('/prvologovanje');
              }
                                 break;
              case 'pacijent':
                if (this.userDTO.prvoLogovanje === false) {
                  this.router.navigateByUrl('/home');
                } else {
                  this.router.navigateByUrl('/prvologovanje');
                }
                               break;
              default: console.log(data);
            }
            this.isLoading = false;
          }, 1500);

        }, err => {
          this.isLoading = false;
          switch (err.error) {
            case 'Not authorized':
              this.errorStatus = 'Nepostojeca e-mail adresa';
              break;
            case 'Incorrect credentials':
              this.errorStatus = 'Neispravni kredencijali';
              break;
            default:
              this.errorStatus = 'Greska pri prijavljivanju na sistem';
              console.log(err);
          }
        });
    }
  }

  async performCheck() {
    if (this.model.email === '' || this.model.lozinka === '') {
      alert('Polja ne smeju biti prazna');
      return false;
    }
    return true;
  }

}

export interface LoginViewModel {
  email: string;
  lozinka: string;
}

export interface UserDTO {
  id: number;
  user_email: string;
  role: string;
  prvoLogovanje: boolean;
}
