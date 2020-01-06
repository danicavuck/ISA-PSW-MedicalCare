import { Component, OnInit } from '@angular/core';
import { FullCalendarModule } from '@fullcalendar/angular'; // for FullCalendar!


@Component({
  selector: 'app-admin-calendar',
  templateUrl: './admin-calendar.component.html',
  styleUrls: ['./admin-calendar.component.css']
})
export class AdminCalendarComponent implements OnInit {

  calendarOptions = {
    buttonText: {
      prevYear: new Date().getFullYear() - 1,
      nextYear: new Date().getFullYear() + 1,
    },
    header: {
      left: 'month, agendaWeek, agendaDay',
      center: 'title',
      right: 'today, prevYear, prev, next, nextYear'
    }
  };

  constructor() { }

  ngOnInit() {
  }

}
