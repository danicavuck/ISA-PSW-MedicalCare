import { Component, OnInit } from '@angular/core';
import { LekServiceComponent } from 'src/app/services/lek-service/lek-service.component';

@Component({
  selector: 'app-dodaj-lek',
  templateUrl: './dodaj-lek.component.html',
  styleUrls: ['./dodaj-lek.component.css']
})
export class DodajLekComponent implements OnInit {
  private data : Data = {
    nazivLeka : "",
    kodLeka : ""
   };
  constructor(private dataService : LekServiceComponent) { }

  ngOnInit() {
  }

  async onSubmit() {
    if(this.performCheck){
    console.log(this.data);
    this.dataService.setData(this.data);

    }
  }


  async performCheck() {
    if (this.data.nazivLeka === '' || this.data.kodLeka === '') {
      alert('Polja ne smeju biti prazna');
      return false;
    }
    return true;
  }

}
export interface Data {
  nazivLeka: string;
  kodLeka: string;
}

