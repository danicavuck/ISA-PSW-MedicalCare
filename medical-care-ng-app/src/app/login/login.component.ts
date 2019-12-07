import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model : LoginViewModel = {
    email : '',
    lozinka : ''
  };

  constructor( private http : HttpClient, private router : Router ) {

  }

  ngOnInit() {
  }


  async onSubmit(){
    if(this.performCheck()){
      let apiEndpoint = "http://localhost:8080/login";

      this.http.post(apiEndpoint, this.model,
        {responseType: 'text'}).subscribe( data => {
        this.router.navigateByUrl("/home");
      }, err =>{
          alert("Neispravni kredencijali");
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
