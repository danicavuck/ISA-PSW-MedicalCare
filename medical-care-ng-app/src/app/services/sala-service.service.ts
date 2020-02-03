import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SalaServiceService {
  private sala: Sala = {
    id: 0,
    nazivSale: ''
  };
  constructor() { }

  public setSala(sala: Sala) {
    this.sala = sala;
  }

  public getSala() {
    return this.sala;
  }

}
export interface Sala {
  id: number;
  nazivSale: string;
}
