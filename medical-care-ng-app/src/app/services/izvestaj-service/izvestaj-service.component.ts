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
private tipPregleda : string;
private izvestajId : number;

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
  public getTip(){
    return this.tipPregleda;
  }
  public setTip(tipPregle : string){
    this.tipPregleda = tipPregle;
  }
  public getIzvestajId(){
    return this.izvestajId;
  }
  public setIzvestajId(id:number){
    this.izvestajId = id;
  }

}
