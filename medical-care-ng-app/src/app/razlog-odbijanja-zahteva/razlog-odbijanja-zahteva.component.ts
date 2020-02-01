//app.component.ts
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { RazlogOdbijanjaServiceComponent } from '../services/razlog-odbijanja-service/razlog-odbijanja-service.component';


@Component({
  selector: 'app-razlog-odbijanja-zahteva',
  templateUrl: './razlog-odbijanja-zahteva.component.html',
  styleUrls: ['./razlog-odbijanja-zahteva.component.css']
})
export class RazlogOdbijanjaZahtevaComponent implements OnInit {

  private data : Data = {
    razlog : ""
  };

  constructor(private dataService : RazlogOdbijanjaServiceComponent) { }

  ngOnInit() {
  }

  async onSubmit() {
    console.log(this.data);
    this.dataService.setData(this.data);

  }
};


export interface Data {
  razlog : string;
}
