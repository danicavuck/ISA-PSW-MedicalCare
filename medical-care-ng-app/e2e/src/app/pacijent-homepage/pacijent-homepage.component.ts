import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-pacijent-homepage',
  templateUrl: './pacijent-homepage.component.html',
  styleUrls: ['./pacijent-homepage.component.css']
})
export class PacijentHomepageComponent implements OnInit {

  constructor(private  http : HttpClient, private router : Router) { }

  ngOnInit() {
  }


  async onLogOut(){
    let apiEndpoint = "http://localhost:8080/logout";

    this.http.post(apiEndpoint,
      {responseType: 'text'}).subscribe( data => {
        console.log(data);
      this.router.navigateByUrl("/login");
    }, err =>{
        console.log(err);
        console.log("Neuspesno odjavljivanje sa sistema");
    });
  }
}
