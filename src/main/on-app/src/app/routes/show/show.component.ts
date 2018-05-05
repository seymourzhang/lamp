import { Component, OnInit } from '@angular/core';
import {HttpService} from "../../net/service/http.service";
import {NzMessageService} from "ng-zorro-antd";

@Component({
  selector: 'app-show',
  templateUrl: './show.component.html',
  styleUrls: ['./show.component.css']
})
export class ShowComponent implements OnInit {

  valData;
  _isSpinning = false;
  data: any[];
  imeis: any[];
  constructor(
    public http: HttpService,
    private _message: NzMessageService
  ) {
    this.valData = {
      code: 'login'
      , conn: http.conn
    }
    this.imeis = [
      {"imei" : "123"}
    ]
  }

  ngOnInit() {
    this.queryImeis();
  }

  initTableData(imei :any) {
    this._isSpinning = true;
    this.valData.conn.url = '/show/lampData';
    this.valData.conn.isLocal = true;
    this.valData.conn.param = {
      user_name: '',
      user_id: '',
      imei_no: imei
    };
    this.http.request(this.valData.conn)
      .subscribe(
        (val) => {
          console.log('POST call successful value returned in body', val);
          setTimeout(() => {
            if (val.Result === 'Success') {
              console.log("response detail data:", val);
              this.data = val.data;
            } else if (val.Result === 'Fail') {
              this._message.create('error', val.Error);
            }
            this._isSpinning = false;
          }, '1000');
        },
        response => {
          console.log('Request Error: ', response);
        },
        () => {
          // console.log('The POST observable is now completed.');
        });
  }

  queryImeis() {
    this._isSpinning = true;
    this.valData.conn.url = '/show/queryImeis';
    this.valData.conn.isLocal = true;
    this.valData.conn.param = {
      user_id: ''
    };
    this.http.request(this.valData.conn)
      .subscribe(
        (val) => {
          console.log('POST call successful value returned in body', val);
          setTimeout(() => {
            if (val.Result === 'Success') {
              console.log("response detail imei data:", val);
              this.imeis = val.imeis;
              this.initTableData(this.imeis[0].imei);
            } else if (val.Result === 'Fail') {
              this._message.create('error', val.Error);
            }
            this._isSpinning = false;
          }, '1000');
        },
        response => {
          console.log('Request Error: ', response);
        },
        () => {
          // console.log('The POST observable is now completed.');
        });
  }

}
