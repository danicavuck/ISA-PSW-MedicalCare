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

  constructor() { }

  ngOnInit() {
  }

}
