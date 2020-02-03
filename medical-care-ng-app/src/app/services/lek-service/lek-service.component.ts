import { Component, OnInit, Injectable } from '@angular/core';

@Component({
  selector: 'app-lek-service',
  templateUrl: './lek-service.component.html',
  styleUrls: ['./lek-service.component.css']
})

@Injectable({
  providedIn: 'root'
})
export class LekServiceComponent implements OnInit {
  private data : Data = {
    nazivLeka : '',
    kodLeka : ''

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
  nazivLeka : string;
  kodLeka : string;
}

