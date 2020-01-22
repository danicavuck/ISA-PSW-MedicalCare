import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-registracija-admina-klinike',
  templateUrl: './registracija-admina-klinike.component.html',
  styleUrls: ['./registracija-admina-klinike.component.css']
})
export class RegistracijaAdminaKlinikeComponent implements OnInit {

   private model: AdminKlinike = {
     ime : '',
     prezime : '',
     email : '',
     lozinka : '',
     id_klinike : 0
      
   }
   private klinike : Array<KlinikaDTO>
   private lozinkaPonovo: string = null;
   errorStatus: string = null;
  constructor(private http : HttpClient, private router:Router) {
    this.getKlinike();
   }

  ngOnInit() {
   
  }

  passwordsMatch() {
    return (this.model.lozinka === this.lozinkaPonovo) && this.model.lozinka.length >= 6;
  }

  isFormValid() {
    // tslint:disable-next-line: max-line-length
    if (this.model.ime !== '' && this.model.prezime !== '' && this.model.email !== '' && this.model.lozinka !== '' && this.passwordsMatch()) {
      return true;
    }

    return false;
  }

  async onSubmit() {
    if (this.isFormValid()) {
      const apiEndpoint = 'http://localhost:8080/adminklinike';

      this.http.post(apiEndpoint, this.model,
        {responseType: 'text'}).subscribe( data => {
          console.log('Uspesno dodavanje admina klinike');
          this.router.navigateByUrl('/adminkc');
        }, error => {
          this.errorStatus = '';
          this.errorStatus = 'E-mail adresa je vec zauzeta';
        });
    } else {
      console.log('Forma nije validna');
    }
  }

  async getKlinike(){
    const apiEndPoint = 'http://localhost:8080/klinika/klinikeSve';
    
   this.http.get(apiEndPoint,{responseType : 'json'})
   .subscribe((data) => {
     this.klinike = data as Array<KlinikaDTO>;
   },err => {
     console.log('greska pri izlistavanju klinika');
     console.log(err);
   });
  
}

}

export interface KlinikaDTO{
  id : string
  naziv : string;
}

export interface AdminKlinike{
  ime: string;
  prezime: string;
  email: string;
  lozinka: string;
  id_klinike: number;
}
