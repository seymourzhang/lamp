import { Component, OnInit } from '@angular/core';
import {NzMessageService} from "ng-zorro-antd";
import {HttpService} from "../../net/service/http.service";
import {DatePipe} from "@angular/common";
import {WindowSrv} from "ngx-clipboard/dist/src/window.service";

@Component({
  selector: 'app-mygirl',
  templateUrl: './mygirl.component.html',
  styleUrls: ['./mygirl.component.css']
})
export class MygirlComponent implements OnInit {
  i = 1;
  valData;
  editCache = {};
  dataSet = [];
  varieties = [];
  size = 'small';
  isVisible = false;
  isOkLoading = false;
  inputValue: string;
  si : string[];
  tempDisabled = false;

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    this.isOkLoading = true;
    window.setTimeout(() => {
      this.isVisible = false;
      this.isOkLoading = false;
    }, 3000);
  }

  handleCancel(): void {
    this.isVisible = false;
  }

  addRow(): void {
    this.si = this.inputValue.split(",");

    this.i++;
    this.dataSet = [...this.dataSet, {
      id: "",
      name: this.si[0] === undefined ? "" : this.si[0],
      address: this.si[1] === undefined ? "" : this.si[1],
      phoneNo: this.si[2] === undefined ? "" : this.si[2],
      varietyId: "",
      varietyName: "",
      price: "0.0",
      num: "0",
      transCast: "0.0",
      packageCast: "0.0",
      incubatorCast: "0.0",
      totalCast: "0.0",
      subPrice: "0.0",
      actualIn: "0.0",
      actualOut: "0.0",
      benefits: "0.0",
      deliverDate: this.datePipe.transform(new Date(),"yyyy-MM-dd HH:mm:ss"),
      transNo: this.si[3] === undefined ? "" : this.si[3],
      acceptDate: this.datePipe.transform(new Date(),"yyyy-MM-dd HH:mm:ss"),
      originPlace: this.si[4] === undefined ? "" : this.si[4],
      tips: this.si[5] === undefined ? "" : this.si[5],
      disabled: this.i === 2
    }];
    this.initEdit();
    this.isVisible = false;
    this.isOkLoading = false;
    this.editCache[""].edit = true;
  }

  startEdit(key: string): void {
    this.editCache[key].edit = true;
  }

  cancelEdit(key: string): void {
    this.editCache[key].edit = false;
  }

  saveEdit(key: string): void {
    const index = this.dataSet.findIndex(item => item.id === key);
    this.dataSet[index] = this.editCache[key].data;
    this.editCache[key].edit = false;

    this.valData.conn.url = '/my/saveMyGirl';
    this.valData.conn.isLocal = true;
    this.varieties.forEach((code) => {
      if (code.codeId == this.dataSet[index].varietyId) {
        this.dataSet[index].varietyName = code.codeName;
      }
    })
    this.valData.conn.param = {
      id              : this.dataSet[index].id,
      name            : this.dataSet[index].name,
      address         : this.dataSet[index].address,
      phoneNo         : this.dataSet[index].phoneNo,
      varietyId       : this.dataSet[index].varietyId,
      varietyName     : this.dataSet[index].varietyName,
      price           : this.dataSet[index].price,
      num             : this.dataSet[index].num,
      transCast       : this.dataSet[index].transCast,
      packageCast     : this.dataSet[index].packageCast,
      incubatorCast   : this.dataSet[index].incubatorCast,
      totalCast       : this.dataSet[index].totalCast,
      subPrice        : this.dataSet[index].subPrice,
      actualIn        : this.dataSet[index].actualIn,
      actualOut       : this.dataSet[index].actualOut,
      benefits        : this.dataSet[index].benefits,
      deliverDate     : this.datePipe.transform(this.dataSet[index].deliverDate,"yyyy-MM-dd HH:mm:ss"),
      transNo         : this.dataSet[index].transNo,
      acceptDate      : this.datePipe.transform(this.dataSet[index].acceptDate,"yyyy-MM-dd HH:mm:ss"),
      originPlace     : this.dataSet[index].originPlace,
      tips            : this.dataSet[index].tips
    };
    this.http.request(this.valData.conn)
      .subscribe(
        (val) => {
          console.log('POST call successful value returned in body', val);
          setTimeout(() => {
            if (val.Result === 'Success') {
              console.log("response detail data:", val);
              this.updateEditCache();
            } else if (val.Result === 'Fail') {
              this._message.create('error', val.Error);
            }
          }, '100');
        },
        response => {
          console.log('Request Error: ', response);
        },
        () => {
          // console.log('The POST observable is now completed.');
        });
  }

  deleteEdit(key: string) {
    this.editCache[key].edit = false;
    this.valData.conn.url = '/my/deleteMyGirl';
    this.valData.conn.isLocal = true;
    this.valData.conn.param = {
      id              : key
    };
    this.http.request(this.valData.conn)
      .subscribe(
        (val) => {
          console.log('POST call successful value returned in body', val);
          setTimeout(() => {
            if (val.Result === 'Success') {
              console.log("response detail data:", val);
              this.updateEditCache();
            } else if (val.Result === 'Fail') {
              this._message.create('error', val.Error);
            }
          }, '100');
        },
        response => {
          console.log('Request Error: ', response);
        },
        () => {
          // console.log('The POST observable is now completed.');
        });
  }

  updateEditCache(): void {
    this.valData.conn.url = '/my/queryMyGirl';
    this.valData.conn.isLocal = true;
    this.valData.conn.param = {
      user_name: '',
      user_id: '',
    };
    this.http.request(this.valData.conn)
      .subscribe(
        (val) => {
          console.log('POST call successful value returned in body', val);
          setTimeout(() => {
            if (val.Result === 'Success') {
              console.log("response detail data:", val);
              this.dataSet = val.data;
              console.log("origin data set: ", this.dataSet);
            } else if (val.Result === 'Fail') {
              this._message.create('error', val.Error);
            }
            this.initEdit();
          }, '100');
        },
        response => {
          console.log('Request Error: ', response);
        },
        () => {
          // console.log('The POST observable is now completed.');
        });
  }

  initEdit() {
    console.log("data set: ", this.dataSet);
    this.dataSet.forEach(item => {
      if (!this.editCache[item.id]) {
        this.editCache[item.id] = {
          edit: false,
          data: item
        };
      }
    });
  }

  queryCherryCode() {
    this.valData.conn.url = '/my/queryCherryCode';
    this.valData.conn.isLocal = true;
    this.valData.conn.param = {};
    this.http.request(this.valData.conn)
      .subscribe(
        (val) => {
          console.log('POST call successful value returned in body', val);
          setTimeout(() => {
            if (val.Result === 'Success') {
              console.log("response detail data:", val);
              this.varieties = val.data;
              console.log("origin data set: ", this.varieties);
            } else if (val.Result === 'Fail') {
              this._message.create('error', val.Error);
            }
            this.initEdit();
          }, '100');
        },
        response => {
          console.log('Request Error: ', response);
        },
        () => {
          // console.log('The POST observable is now completed.');
        });
  }

  constructor(public http: HttpService,
              private _message: NzMessageService,
              private datePipe: DatePipe,
              private window: WindowSrv) {
    this.valData = {
      code: 'login'
      , conn: http.conn
    }
  }



  ngOnInit(): void {
    console.log("clipboard data:", );
    this.queryCherryCode();
    this.updateEditCache();
  }

}
