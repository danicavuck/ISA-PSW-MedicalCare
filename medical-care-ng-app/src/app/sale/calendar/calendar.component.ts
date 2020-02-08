import { Component, OnInit } from '@angular/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/daygrid';
import list from '@fullcalendar/daygrid';
import bootstrap from '@fullcalendar/daygrid';
import { HttpClient } from '@angular/common/http';
import { SalaServiceService } from 'src/app/services/sala-service.service';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {
  header: {
    left: 'prev,next today',
    center: 'title',
    right: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth'
  };
  private sala: Sala = {
    id: 0,
    nazivSale: ''
  };
  calendarPlugins = [dayGridPlugin, timeGridPlugin, list, bootstrap];
  calendarEvents: any[] = [];
  constructor(private http: HttpClient, private salaService: SalaServiceService) {
    this.sala = salaService.getSala();
    this.getPregledeIOperacijeInitialy();
   }

  ngOnInit() {
  }

  async getPregledeIOperacijeInitialy() {
    const apiEndpoint = 'http://localhost:8080/sale/' + this.sala.id;
    this.http.get(apiEndpoint, {withCredentials: true}).subscribe(data => {
      console.log('Uspesno dobavljeni pregledi i operacije');
      this.calendarEvents = data as any;
    }, err => {
      console.log('Neuspesno dobavljanje pregleda i operacija');
    });
  }

}

export interface PreglediOperacije {
  start: Date;
  end: Date;
  title: string;
}

export interface Sala {
  id: number;
  nazivSale: string;
}



