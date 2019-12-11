import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  isLoading : boolean = false;
  errorStatus : string = null;

  model : LoginViewModel = {
    email : '',
    lozinka : ''
  };

  constructor( private http : HttpClient, private router : Router ) {

  }

  ngOnInit() {
  }


  async onSubmit(){
    this.isLoading = true;

    if(this.performCheck()){
      let apiEndpoint = "http://localhost:8080/login";

      this.http.post(apiEndpoint, this.model,
        {responseType: 'text'}).subscribe( data => {
        //this.router.navigateByUrl("/home");
        setTimeout(() =>
        {
          switch (data) {
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
        },1500);

      }, err =>{
          this.isLoading = false;
          switch (err.error) {
            case 'Not authorized':
              this.errorStatus = "Nepostojeca e-mail adresa";
              break;
            case 'Incorrect credentials':
              this.errorStatus = "Neispravni kredencijali";
              break;
            default:
              this.errorStatus = "Greska pri prijavljivanju na sistem"
          }
      });
    }
  }

  async performCheck(){
    if(this.model.email == "" || this.model.lozinka == ""){
      alert("Polja ne smeju biti prazna");
      return false;
    }
    return true;
  }

}

export interface LoginViewModel{
  email:string;
  lozinka:string;
}
