import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule, Routes} from "@angular/router";
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";
import { PacijentHomepageComponent } from './pacijent-homepage/pacijent-homepage.component';
import { LoadingSpinnerComponent} from "../../shared/loading-spinner/loading-spinner.component";
import { AdminKlinikeHomepageComponent } from './admin-klinike-homepage/admin-klinike-homepage.component';
import { LekarComponent } from './lekar/lekar.component';
import { MedicinskaSestraComponent } from './medicinska-sestra/medicinska-sestra.component';
import { KlinikaIzmenaComponent } from './klinika/klinika-izmena/klinika-izmena.component';
import { AdminKcComponent } from './admin-kc/admin-kc.component';

const appRouts : Routes = [
  { path: 'login', component : LoginComponent },
  { path: 'registration', component : RegistrationComponent },
  { path: 'home', component : PacijentHomepageComponent },
  { path: 'adminklinike', component: AdminKlinikeHomepageComponent },
  { path: 'izmenaklinike', component: KlinikaIzmenaComponent },
  { path: 'lekar', component: LekarComponent },
  { path: 'medsestra', component: MedicinskaSestraComponent },
  { path: 'adminkc', component : AdminKcComponent},
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
    KlinikaIzmenaComponent,
    AdminKcComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(appRouts)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
