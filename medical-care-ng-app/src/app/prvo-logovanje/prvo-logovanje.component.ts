import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-prvo-logovanje',
  templateUrl: './prvo-logovanje.component.html',
  styleUrls: ['./prvo-logovanje.component.css']
})
export class PrvoLogovanjeComponent implements OnInit {
  private model: PromenaLozinke = {
    staraLozinka: '',
    novaLozinka: '',
    role: ''
  };
  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
  }

  async izmeniPodatke() {
    const apiEndpoint = 'http://localhost:8080/login';
    this.http.put(apiEndpoint, this.model, {withCredentials: true}).subscribe(data => {
      this.model = data as PromenaLozinke;
      switch (this.model.role)
      {
        case 'adminklinike': this.router.navigateByUrl('/adminklinike');
          break;
        case 'pacijent': this.router.navigateByUrl('/home');
          break;
        case 'lekar': this.router.navigateByUrl('/lekar');
          break;
        case 'med_sestra': this.router.navigateByUrl('/medsestra');
          break;
        default: this.router.navigateByUrl('/login');
      }
    }, err => {
      console.log('Neuspesna promena lozinke');
    });
  }

}

export interface PromenaLozinke {
  staraLozinka: string;
  novaLozinka: string;
  role: string;
}
