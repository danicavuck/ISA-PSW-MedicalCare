import { Component, OnInit } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/daygrid';
import list from '@fullcalendar/daygrid';
import bootstrap from '@fullcalendar/daygrid';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-lekar-calendar',
  templateUrl: './lekar-calendar.component.html',
  styleUrls: ['./lekar-calendar.component.css']
})
export class LekarCalendarComponent implements OnInit {
  calendarPlugins = [dayGridPlugin, timeGridPlugin, list, bootstrap];
  calendarEvents: any[] = [];

  constructor(private http: HttpClient) {
    this.getPregledeIOperacijeInitialy();
   }

  ngOnInit() {
  }

  async getPregledeIOperacijeInitialy() {
    const apiEndpoint = 'http://localhost:8080/lekari/zauzece';
    this.http.get(apiEndpoint, {withCredentials: true}).subscribe(data => {
      this.calendarEvents = data as any;
    }, err => {
      console.log('Greska pri dobavljanju pregleda i operacija');
    });
  }

}

export interface Dogadjaj {
  start: Date;
  end: Date;
  title: string;
}
