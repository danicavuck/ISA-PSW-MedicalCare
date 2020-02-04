import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TipPregledaService {
  private tipPregleda: TipPregleda = {
    tipPregleda: '',
    id: 0
  };
  constructor() { }

  public setTipPregleda(tipPregleda: TipPregleda) {
    this.tipPregleda = tipPregleda;
  }

  public getTipPregleda() {
    return this.tipPregleda;
  }
}

export interface TipPregleda {
  tipPregleda: string;
  id: number;
}
