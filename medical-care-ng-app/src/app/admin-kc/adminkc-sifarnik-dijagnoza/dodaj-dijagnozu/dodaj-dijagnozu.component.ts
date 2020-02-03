import { Component, OnInit } from '@angular/core';
import { DijagnozaServiceComponent } from 'src/app/services/dijagnoza-service/dijagnoza-service.component';

@Component({
  selector: 'app-dodaj-dijagnozu',
  templateUrl: './dodaj-dijagnozu.component.html',
  styleUrls: ['./dodaj-dijagnozu.component.css']
})
export class DodajDijagnozuComponent implements OnInit {
  private data : Data = {
   nazivBolesti : "",
   kodBolesti : ""
  };

  constructor(private dataService : DijagnozaServiceComponent) { }

  ngOnInit() {
  }
  
  async onSubmit() {
    if(this.performCheck){
    console.log(this.data);
    this.dataService.setData(this.data);

    }
  }

  async performCheck() {
    if (this.data.nazivBolesti === '' || this.data.kodBolesti === '') {
      alert('Polja ne smeju biti prazna');
      return false;
    }
    return true;
  }

}
export interface Data {
  nazivBolesti: string;
  kodBolesti: string;
}
