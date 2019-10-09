import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RoutesRoutingModule } from './routes-routing.module';
import {HttpService} from "../net/service/http.service";
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
  declarations: [],
  providers: [
    HttpService
  ]
})
export class RoutesModule { }
