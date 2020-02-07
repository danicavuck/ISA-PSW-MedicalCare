import { Component, OnInit, Injectable } from '@angular/core';

@Component({
  selector: 'app-sestra-pacijent-service',
  templateUrl: './sestra-pacijent-service.component.html',
  styleUrls: ['./sestra-pacijent-service.component.css']
})
@Injectable({
  providedIn: 'root'
})

export class SestraPacijentServiceComponent implements OnInit {
  private pacijentID: number;
  private sestraID: number;

  constructor() { }

  ngOnInit() {
  }

  public setPacijentID(pacID: number) {
    this.pacijentID = pacID;
  }
  public getPacijentID() {
    return this.pacijentID;
  }
  public setSestraID(sID: number) {
    this.sestraID = sID;
  }
  public getSestraID() {
    return this.sestraID;
  }
}
