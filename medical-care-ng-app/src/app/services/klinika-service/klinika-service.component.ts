import { Component, OnInit, Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-klinika-service',
  templateUrl: './klinika-service.component.html',
  styleUrls: ['./klinika-service.component.css']
})
@Injectable({
  providedIn: 'root'
})
export class KlinikaServiceComponent implements OnInit {

  private data: KlinikaDTO;

  constructor() { }

  ngOnInit() {

  }

  public setData(data: KlinikaDTO) {
    this.data = data;
  }

  public getData() {
    return this.data;
  }

}
export interface KlinikaDTO {
  id: number;
  naziv: string;
  opis: string;
  adresa: string;
  prosecnaOcena: number;
}
