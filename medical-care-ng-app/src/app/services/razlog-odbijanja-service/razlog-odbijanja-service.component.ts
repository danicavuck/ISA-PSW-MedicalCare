import { Component, OnInit, Injectable } from '@angular/core';

@Component({
  selector: 'app-razlog-odbijanja-service',
  templateUrl: './razlog-odbijanja-service.component.html',
  styleUrls: ['./razlog-odbijanja-service.component.css']
})

@Injectable({
  providedIn: 'root'
})
export class RazlogOdbijanjaServiceComponent implements OnInit {

  private data : Data = {
    razlog : ''

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
  razlog : string;
}


