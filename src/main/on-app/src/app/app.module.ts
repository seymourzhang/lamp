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
import {BrowserAnimationsModule, NoopAnimationsModule} from "@angular/platform-browser/animations";
import {NgZorroAntdModule} from "ng-zorro-antd";
import { MygirlComponent } from './routes/mygirl/mygirl.component';
import {DatePipe} from "@angular/common";
import {ClipboardModule} from "ngx-clipboard/dist";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    ShowComponent,
    IdentityComponent,
    AnalyseComponent,
    MygirlComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ShareModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxEchartsModule,
    EchartsNg2Module,
    NoopAnimationsModule,
    FormsModule,
    ClipboardModule,
    NgZorroAntdModule.forRoot()
  ],
  providers: [HttpService,DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }
