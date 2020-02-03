import { Component, OnInit, Inject } from '@angular/core';
import { ZahtevServiceService } from 'src/app/services/zahtev-service.service';

@Component({
  selector: 'app-brisanje-odsustva',
  templateUrl: './brisanje-odsustva.component.html',
  styleUrls: ['./brisanje-odsustva.component.css']
})

export class BrisanjeOdsustvaComponent implements OnInit {
  private opis: '';
  constructor(private zahtevService: ZahtevServiceService) { }

  ngOnInit() {
  }

  async saljiOpis() {
    this.zahtevService.setOpis(this.opis);
  }

}
