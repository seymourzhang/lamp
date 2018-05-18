import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { LoginComponent } from './routes/user/login/login.component';
import {ShareModule} from './share/share.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpService} from "./net/service/http.service";
import {HttpClientModule} from '@angular/common/http';
import {HomeComponent} from "./routes/home/home.component";
import {ShowComponent} from "./routes/show/show.component";
import {IdentityComponent} from "./routes/user/identity/identity.component";
import {AnalyseComponent} from "./routes/analyse/analyse.component";
import {NgxEchartsModule} from 'ngx-echarts';
import {EchartsNg2Module} from "echarts-ng2";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {NzSelectModule} from "ng-zorro-antd";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ShowComponent,
    IdentityComponent,
    AnalyseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ShareModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxEchartsModule,
    EchartsNg2Module,
    NoopAnimationsModule,
    FormsModule
  ],
  providers: [HttpService],
  bootstrap: [AppComponent]
})
export class AppModule { }
