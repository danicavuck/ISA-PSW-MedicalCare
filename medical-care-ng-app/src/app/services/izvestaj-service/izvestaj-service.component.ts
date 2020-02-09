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
private pregledID : number;
private izvestajId : number;
private tipPregleda: string;

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
  public getPregledID(){
    return this.pregledID;
  }
  public setPregledID(id : number){
    this.pregledID = id;
  }
  public getIzvestajId(){
    return this.izvestajId;
  }
  public setIzvestajId(id:number){
    this.izvestajId = id;
  }
  public getTipPregleda(){
    return this.tipPregleda;
  }

  public setTipPregleda(tip : string){
    this.tipPregleda = tip;
  }
}
