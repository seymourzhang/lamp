import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgZorroAntdModule} from "ng-zorro-antd";

@NgModule({
  imports: [
    CommonModule,
    NgZorroAntdModule.forRoot()
  ],
  exports: [
    NgZorroAntdModule
  ],
  declarations: []
})
export class ShareModule { }
