import { Component, OnInit } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/daygrid';
import list from '@fullcalendar/daygrid';
import bootstrap from '@fullcalendar/daygrid';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-kalendar',
  templateUrl: './kalendar.component.html',
  styleUrls: ['./kalendar.component.css']
})
export class KalendarComponent implements OnInit {
  calendarPlugins = [dayGridPlugin, timeGridPlugin, list, bootstrap];
  calendarEvents: any[] = [];

  constructor(private http:HttpClient) {
    this.getOdsustva();
   }

  ngOnInit() {
  }

  async getOdsustva() {
    const apiEndpoint = 'http://localhost:8080/medsestra/odsusva';
    this.http.get(apiEndpoint, {withCredentials: true}).subscribe(data => {
      this.calendarEvents = data as any;
    }, err => {
      console.log('Greska pri dobavljanju odsusava');
    });
  }

}
