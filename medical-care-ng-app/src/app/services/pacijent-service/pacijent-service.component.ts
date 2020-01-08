import { Component, OnInit, Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';


@Component({
  selector: 'app-pacijent-service',
  templateUrl: './pacijent-service.component.html',
  styleUrls: ['./pacijent-service.component.css']
})

@Injectable({
  providedIn: 'root'
})

export class PacijentServiceComponent implements OnInit {
  private pacijentID: number;
  private lekarID: number;

  constructor() { }

  ngOnInit() {
  }

  public setPacijentID(pacID: number) {
    this.pacijentID = pacID;
  }
  public getPacijentID() {
    return this.pacijentID;
  }
  public setLekarID(lekID: number) {
    this.lekarID = lekID;
  }
  public getLekarID() {
    return this.lekarID;
  }

}
