import { Component, OnInit, Injectable } from '@angular/core';

@Component({
  selector: 'app-dijagnoza-service',
  templateUrl: './dijagnoza-service.component.html',
  styleUrls: ['./dijagnoza-service.component.css']
})

@Injectable({
  providedIn: 'root'
})
export class DijagnozaServiceComponent implements OnInit {
  private data : Data = {
    nazivBolesti : '',
    kodBolesti : ''

  };

  constructor() { }

  ngOnInit() {
  }

  public setData(data : Data){
    this.data = data;
  }

  public getData(){
    return this.data;
  }

}

export interface Data {
  nazivBolesti : string;
  kodBolesti : string;
}
