import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FullCalendarModule } from '@fullcalendar/angular';
import { AgmCoreModule } from '@agm/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule, Routes} from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PacijentHomepageComponent } from './pacijent-homepage/pacijent-homepage.component';
import { LoadingSpinnerComponent} from '../../shared/loading-spinner/loading-spinner.component';
import { AdminKlinikeHomepageComponent } from './admin-klinike-homepage/admin-klinike-homepage.component';
import { LekarComponent } from './lekar/lekar.component';
import { MedicinskaSestraComponent } from './medicinska-sestra/medicinska-sestra.component';
import { AdminKcComponent } from './admin-kc/admin-kc.component';
import { RegistracijaAdminaKlinikeComponent } from './registracija-admina-klinike/registracija-admina-klinike.component';
import { RegistracijaKlinikeComponent } from './registracija-klinike/registracija-klinike.component';

import { DefinisanjePregledaComponent } from './definisanje-pregleda/definisanje-pregleda.component';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TimepickerModule } from 'ngx-bootstrap';
import { KlinikaDetaljnijeComponent } from './klinika/klinika-detaljnije/klinika-detaljnije.component';
import { SaleComponent } from './sale/sale.component';
import { DodajSaluComponent } from './sale/dodaj-salu/dodaj-salu.component';
import { DodavanjeTipaPregledaComponent } from './definisanje-pregleda/dodavanje-tipa-pregleda/dodavanje-tipa-pregleda.component';
import { KlinikaServiceComponent } from './services/klinika-service/klinika-service.component';
import { AngularDateTimePickerModule } from 'angular2-datetimepicker';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { MaterialModule } from './material/material.module';
import { SalaDialogComponent } from './dialozi/sala-dialog/sala-dialog.component';
import { NavbarComponent } from './admin-klinike/navbar/navbar.component';
import { LekarDialogComponent } from './dialozi/lekar-dialog/lekar-dialog.component';
import { DodavanjeLekaraComponent } from './dodavanje-lekara/dodavanje-lekara.component';
import { PregledPacijenataComponent } from './admin-klinike/pregled-pacijenata/pregled-pacijenata.component';
import { AdminCalendarComponent } from './admin-klinike/admin-calendar/admin-calendar.component';
import { LekarNavbarComponent } from './lekar/lekar-navbar/lekar-navbar.component';
import { DodavanjePregledaComponent } from './lekar/dodavanje-pregleda/dodavanje-pregleda.component';
import { RadniKalendarComponent } from './lekar/radni-kalendar/radni-kalendar.component';
import { OdsustvaIOdmorComponent } from './lekar/odsustva-i-odmor/odsustva-i-odmor.component';
import { PregledIOperacijeComponent } from './lekar/pregled-i-operacije/pregled-i-operacije.component';
import { PacijentServiceComponent } from './services/pacijent-service/pacijent-service.component';
import { PacijentDetaljnijeComponent } from './lekar/pacijent-detaljnije/pacijent-detaljnije.component';
import { PreglediNaCekanjuComponent } from './admin-klinike/pregledi-na-cekanju/pregledi-na-cekanju.component';
import { ZahteviZaOdsustvoComponent } from './admin-klinike/zahtevi-za-odsustvo/zahtevi-za-odsustvo.component';
import { MedicinskaSestraReceptiComponent } from './medicinska-sestra-recepti/medicinska-sestra-recepti.component';
import { RazlogOdbijanjaZahtevaComponent } from './razlog-odbijanja-zahteva/razlog-odbijanja-zahteva.component';
import { RazlogOdbijanjaServiceComponent } from './services/razlog-odbijanja-service/razlog-odbijanja-service.component';
import { AdminkcSifarnikDijagnozaComponent } from './admin-kc/adminkc-sifarnik-dijagnoza/adminkc-sifarnik-dijagnoza.component';
import { AdminkcSifarnikLekovaComponent } from './admin-kc/adminkc-sifarnik-lekova/adminkc-sifarnik-lekova.component';
import { DodajDijagnozuComponent } from './admin-kc/adminkc-sifarnik-dijagnoza/dodaj-dijagnozu/dodaj-dijagnozu.component';
import { DijagnozaServiceComponent } from './services/dijagnoza-service/dijagnoza-service.component';
import { DodajLekComponent } from './admin-kc/adminkc-sifarnik-lekova/dodaj-lek/dodaj-lek.component';
import { LekServiceComponent } from './services/lek-service/lek-service.component';
import { OdsustvaComponent } from './medicinska-sestra/odsustva/odsustva.component';
import { DetailsComponent } from './admin-klinike/details/details.component';
import { PrvoLogovanjeComponent } from './prvo-logovanje/prvo-logovanje.component';
import { LekarDetaljnijeComponent } from './lekar/lekar-detaljnije/lekar-detaljnije.component';
import { MedSestraDetaljnijeComponent } from './medicinska-sestra/med-sestra-detaljnije/med-sestra-detaljnije.component';
import { MedSestraNavbarComponent } from './medicinska-sestra/med-sestra-navbar/med-sestra-navbar.component';
import { IzvestajOPregleduComponent } from './izvestaj-o-pregledu/izvestaj-o-pregledu.component';
import { IzmenaSaleComponent } from './sale/izmena-sale/izmena-sale.component';
import { BrisanjeOdsustvaComponent } from './dialozi/brisanje-odsustva/brisanje-odsustva.component';
import { IzmenaTipaPregledaComponent } from './admin-klinike/izmena-tipa-pregleda/izmena-tipa-pregleda.component';
import { DodavanjeOperacijaComponent } from './lekar/dodavanje-operacija/dodavanje-operacija.component';
import { OperacijeNaCekanjuComponent } from './admin-klinike/operacije-na-cekanju/operacije-na-cekanju.component';
import { CalendarComponent } from './sale/calendar/calendar.component';
import { LekarCalendarComponent } from './lekar/lekar-calendar/lekar-calendar.component';
import { KalendarComponent } from './medicinska-sestra/kalendar/kalendar.component';



const appRouts: Routes = [
  { path: 'login', component : LoginComponent },
  { path: 'registration', component : RegistrationComponent },
  { path: 'home', component : PacijentHomepageComponent },
  { path: 'adminklinike', component: AdminKlinikeHomepageComponent},
  { path: 'adminklinike/defpregleda', component: DefinisanjePregledaComponent},
  { path: 'adminklinike/klinika', component: KlinikaDetaljnijeComponent},
  { path: 'adminklinike/sale', component: SaleComponent},
  { path: 'adminklinike/dodavanjetipa', component: DodavanjeTipaPregledaComponent},
  { path: 'adminklinike/dodajsalu', component: DodajSaluComponent},
  { path: 'adminklinike/pregledpacijenata', component : PregledPacijenataComponent},
  { path: 'adminklinike/dodavanjelekara', component : DodavanjeLekaraComponent},
  { path: 'adminklinike/pregledinacekanju', component: PreglediNaCekanjuComponent},
  { path: 'adminklinike/operacijenacekanju', component: OperacijeNaCekanjuComponent},
  { path: 'adminklinike/zahtevizaodsustvo', component: ZahteviZaOdsustvoComponent},
  { path: 'adminklinike/details', component: DetailsComponent},
  { path: 'adminklinike/izmenatipa', component: IzmenaTipaPregledaComponent},
  { path: 'adminklinike/sala', component: IzmenaSaleComponent},
  { path: 'adminklinike/sala/calendar', component: CalendarComponent},
  { path: 'adminklinike/calendar', component: AdminCalendarComponent},
  { path: 'lekar', component: LekarComponent },
  { path: 'lekar/dodavanjepregleda', component : DodavanjePregledaComponent},
  { path: 'lekar/zakazivanje', component : PregledIOperacijeComponent},
  { path: 'lekar/operacije', component : DodavanjeOperacijaComponent},
  { path: 'lekar/odsustva', component : OdsustvaIOdmorComponent},
  { path: 'lekar/kalendar', component : LekarCalendarComponent},
  { path: 'lekar/pacijent', component : PacijentDetaljnijeComponent},
  { path: 'lekar/detaljnije', component : LekarDetaljnijeComponent},
  { path: 'medsestra', component: MedicinskaSestraComponent },
  { path: 'medsestra/svirecepti', component : MedicinskaSestraReceptiComponent},
  { path: 'medsestra/odsustva', component : OdsustvaComponent},
  { path: 'medsestra/kalendar',component : KalendarComponent},
  { path: 'adminkc/registracija-admina', component : RegistracijaAdminaKlinikeComponent},
  { path: 'adminkc/registracija-klinike', component : RegistracijaKlinikeComponent},
  { path: 'adminkc/sifarnik-dijagnoza', component:AdminkcSifarnikDijagnozaComponent},
  { path: 'adminkc/sifarnik-lekova',component: AdminkcSifarnikLekovaComponent},
  { path: 'medsestra/detaljnije', component: MedSestraDetaljnijeComponent },

  { path: 'adminkc', component : AdminKcComponent},
  { path: 'prvologovanje', component: PrvoLogovanjeComponent },
  { path: 'registracija-admina', component : RegistracijaAdminaKlinikeComponent},
  { path: 'registracija-klinike', component : RegistracijaKlinikeComponent},
  { path: 'izvestajOpregledu',component: IzvestajOPregleduComponent},
  { path: '', redirectTo : '/login', pathMatch : 'full' },
  { path: '**', component : PageNotFoundComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    PageNotFoundComponent,
    PacijentHomepageComponent,
    LoadingSpinnerComponent,
    AdminKlinikeHomepageComponent,
    LekarComponent,
    MedicinskaSestraComponent,
    AdminKcComponent,
    RegistracijaAdminaKlinikeComponent,
    RegistracijaKlinikeComponent,
    DefinisanjePregledaComponent,
    KlinikaDetaljnijeComponent,
    SaleComponent,
    DodajSaluComponent,
    DodavanjeTipaPregledaComponent,
    KlinikaServiceComponent,
    SalaDialogComponent,
    NavbarComponent,
    LekarDialogComponent,
    DodavanjeLekaraComponent,
    PregledPacijenataComponent,
    AdminCalendarComponent,
    LekarNavbarComponent,
    DodavanjePregledaComponent,
    RadniKalendarComponent,
    OdsustvaIOdmorComponent,
    PregledIOperacijeComponent,
    PacijentServiceComponent,
    PacijentDetaljnijeComponent,
    PreglediNaCekanjuComponent,
    ZahteviZaOdsustvoComponent,
    MedicinskaSestraReceptiComponent,
    RazlogOdbijanjaZahtevaComponent,
    RazlogOdbijanjaServiceComponent,
    AdminkcSifarnikDijagnozaComponent,
    AdminkcSifarnikLekovaComponent,
    DodajDijagnozuComponent,
    DijagnozaServiceComponent,
    DodajLekComponent,
    LekServiceComponent,
    OdsustvaComponent,
    DetailsComponent,
    PrvoLogovanjeComponent,
    LekarDetaljnijeComponent,
    MedSestraDetaljnijeComponent,
    MedSestraNavbarComponent,
    IzmenaSaleComponent,
    BrisanjeOdsustvaComponent,
    IzmenaTipaPregledaComponent,
    DodavanjeOperacijaComponent,
    OperacijeNaCekanjuComponent,
    CalendarComponent,
    LekarCalendarComponent,
    MedSestraNavbarComponent,
    IzvestajOPregleduComponent,
    KalendarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    BsDatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    AngularDateTimePickerModule,
    MaterialModule,
    FullCalendarModule,
    AgmCoreModule.forRoot(
      {apiKey: 'AIzaSyBiRvZ7NUPcXpFCE9YaO6ts9sDJh7cg7gs'}
    ),
    RouterModule.forRoot(appRouts)
  ],
  providers: [BrisanjeOdsustvaComponent],
  bootstrap: [AppComponent],
  entryComponents: [SalaDialogComponent, LekarDialogComponent, RazlogOdbijanjaZahtevaComponent, DodajDijagnozuComponent, DodajLekComponent, BrisanjeOdsustvaComponent],

})
export class AppModule { }
