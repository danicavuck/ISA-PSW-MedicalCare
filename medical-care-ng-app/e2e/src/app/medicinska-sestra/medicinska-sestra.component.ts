import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-medicinska-sestra',
  templateUrl: './medicinska-sestra.component.html',
  styleUrls: ['./medicinska-sestra.component.css']
})
export class MedicinskaSestraComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  async odjava() {
    const apiEndpoint = 'http://localhost:8080/odjava';
    this.http.post(apiEndpoint, null, {withCredentials: true}).subscribe(data => {
      console.log('Uspesna odjava');
    }, err => {
      console.log('Neuspesna odjava');
    });
  }

}


