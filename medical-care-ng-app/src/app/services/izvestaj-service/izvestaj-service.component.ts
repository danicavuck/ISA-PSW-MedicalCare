import { Component, OnInit, Injectable } from '@angular/core';

@Component({
  selector: 'app-izvestaj-service',
  templateUrl: './izvestaj-service.component.html',
  styleUrls: ['./izvestaj-service.component.css']
})
@Injectable({
  providedIn: 'root'
})

export class IzvestajServiceComponent implements OnInit {
private pacijentID : number;
private lekarID : number;

  constructor() { }

  ngOnInit() {
  }

  public setPacijentID(pacID: number) {
    this.pacijentID = pacID;
  }
  public getPacijentID() {
    return this.pacijentID;
  }
  public setLekarID(sID: number) {
    this.lekarID = sID;
  }
  public getLekarID() {
    return this.lekarID;
  }



}
