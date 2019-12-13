import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoading = false;
  errorStatus = null;

  model: LoginViewModel = {
    email : '',
    lozinka : ''
  };

  userDTO: UserDTO = {
    id : 0,
    user_email : '',
    role : ''
  };

  constructor( private http: HttpClient, private router: Router ) {

  }

  ngOnInit() {
  }


  async onSubmit() {
    this.isLoading = true;

    if (this.performCheck()) {
      const apiEndpoint = 'http://localhost:8080/login';

      this.http.post(apiEndpoint, this.model,
        {responseType: 'json'}).subscribe( data => {
        setTimeout(() => {
          this.userDTO = data as UserDTO;
          switch (this.userDTO.role) {
            case 'admin_klinike' : this.router.navigateByUrl('/adminklinike');
                                   break;
            case 'lekar' : this.router.navigateByUrl('/lekar');
                           break;
            case 'med_sestra' : this.router.navigateByUrl('/medsestra');
                                break;
            case 'pacijent' : this.router.navigateByUrl('/home');
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
          }
      });
    }
  }

  async performCheck() {
    if (this.model.email == '' || this.model.lozinka == '') {
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
}
