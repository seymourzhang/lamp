import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RoutesRoutingModule } from './routes-routing.module';
import { LoginComponent } from './user/login/login.component';
import {HttpService} from "../net/service/http.service";
import {BrowserModule} from "@angular/platform-browser";
import {HttpClient, HttpClientModule, HttpHandler} from "@angular/common/http";
import { HomeComponent } from './home/home.component';
import { ShowComponent } from './show/show.component';
import {ShareModule} from "../share/share.module";
import { IdentityComponent } from './user/identity/identity.component';
import { AnalyseComponent } from './analyse/analyse.component';
import {NgZorroAntdModule} from "ng-zorro-antd";
import { MygirlComponent } from './mygirl/mygirl.component';
import { IndexComponent } from './index/index.component';

@NgModule({
  imports: [
    CommonModule,
    RoutesRoutingModule,
    ShareModule,
    NgZorroAntdModule
  ],
  declarations: [
    ShowComponent,
    IdentityComponent,
    AnalyseComponent,
    MygirlComponent,
    IndexComponent
  ],
  providers: [
    HttpService
  ]
})
export class RoutesModule { }
