import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ZahtevServiceService {
  private opis: string = '';
  constructor() { }

  public setOpis(opis: string) {
    this.opis = opis;
  }

  public getOpis() {
    return this.opis;
  }
}
