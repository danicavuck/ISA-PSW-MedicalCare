import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-registracija-admina-klinike',
  templateUrl: './registracija-admina-klinike.component.html',
  styleUrls: ['./registracija-admina-klinike.component.css']
})
export class RegistracijaAdminaKlinikeComponent implements OnInit {

   private model: AdminKlinikeBazicnoDTO = {
     ime : '',
     prezime : '',
     email : '',
     lozinka : '',
     id_klinika : 0
      
   }
   
   klinike : Array<KlinikaSveDTO>;
   private lozinkaPonovo: string = null;
   errorStatus: string = null;
  constructor(private http : HttpClient, private router:Router) {
    
   }

  ngOnInit() {
    this.getKlinike();
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
      const apiEndpoint = 'http://localhost:8080/adminkc/dodajAdminaKlinike';

      this.http.post(apiEndpoint, this.model,
        {responseType: 'text',withCredentials:true}).subscribe( data => {
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
    
   this.http.get(apiEndPoint,{ withCredentials: true })
   .subscribe((data) => {
     this.klinike = data as Array<KlinikaSveDTO>;
   },err => {
     console.log('greska pri izlistavanju klinika');
     console.log(err);
   });
  
}

}

export interface KlinikaSveDTO{
  naziv : string;
  id : number;
}

export interface AdminKlinikeBazicnoDTO{
  ime: string;
  prezime: string;
  email: string;
  lozinka: string;
  id_klinika: number;
}
