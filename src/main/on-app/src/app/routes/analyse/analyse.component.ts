import { Component, OnInit } from '@angular/core';
import * as echarts from 'echarts';
import {HttpService} from "../../net/service/http.service";
import {NzMessageService} from "ng-zorro-antd";

@Component({
  selector: 'app-analyse',
  templateUrl: './analyse.component.html',
  styleUrls: ['./analyse.component.css']
})
export class AnalyseComponent implements OnInit {
  tempData = [];
  date = [];
  imeis: any[];
  options = [];
  loading = false;
  imei;

  temp = Math.random() * 300;
  // data = [Math.random() * 300];
  T1 = [];
  T2 = [];
  T3 = [];
  T4 = [];
  T5 = [];
  T6 = [];
  chartOption = {};
  _isSpinning = false;
  valData;

  ngOnInit() {
    this.initCharts();
    this.queryImeis();
  }

  constructor(private http: HttpService,
              private _message: NzMessageService) {
    this.valData = {
      code: 'login'
      , conn: http.conn
    }
  }

  initDatas(imei: any) {
    this._isSpinning = true;
    this.date = [];
    this.T1 = [];
    this.T2 = [];
    this.T3 = [];
    this.T4 = [];
    this.T5 = [];
    this.T6 = [];
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
              this.tempData = val.data;
              let i = 0;
              for (let j = this.tempData.length - 1; j > 0 ; --j) {
                // let n = +new Date(this.tempData[this.tempData.length - 1].collectionDatetime) + i * 300 * 1000;
                // let now = new Date(n);
                console.log("datetime:", this.tempData[j].collectionDatetime);
                let now = new Date(this.tempData[j].collectionDatetime);
                this.date.push([now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/') + ' ' + [now.getHours(), now.getMinutes()].join(':'));
                this.T1.push(this.tempData[j].t1 == null ? "0" : this.tempData[j].t1);
                this.T2.push(this.tempData[j].t2 == null ? "0" : this.tempData[j].t2);
                this.T3.push(this.tempData[j].t3 == null ? "0" : this.tempData[j].t3);
                this.T4.push(this.tempData[j].t4 == null ? "0" : this.tempData[j].t4);
                this.T5.push(this.tempData[j].t5 == null ? "0" : this.tempData[j].t5);
                this.T6.push(this.tempData[j].t6 == null ? "0" : this.tempData[j].t6);
                i++;
              }
              console.log("Date:", this.date);
              console.log("T1:", this.T1);
              this.initCharts();
              console.info("init here ~!");
            } else if (val.Result === 'Fail') {
              this._message.create('error', val.Error);
            }
            this._isSpinning = false;
          }, '5000');
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
              // this.imei = this.imeis[0].imei;
              this.initDatas("");
            } else if (val.Result === 'Fail') {
              this._message.create('error', val.Error);
            }
            this._isSpinning = false;
          }, '5000');
        },
        response => {
          console.log('Request Error: ', response);
        },
        () => {
          // console.log('The POST observable is now completed.');
        });
  }

  scrollToBottom() {
    if (!this.loading) {
      this.loading = true;
      setTimeout(() => {
        // this.generateFakeData();
        this.loading = false;
      }, 3000);
    }
  }

  initCharts() {
    this.chartOption = {
      tooltip: {
        trigger: 'axis',
        position: function (pt) {
          return [pt[0], '10%'];
        }
      },
      title: {
        left: 'center',
        text: '大数据量面积图',
      },
      toolbox: {
        feature: {
          dataZoom: {
            yAxisIndex: 'none'
          },
          restore: {},
          saveAsImage: {}
        }
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: this.date
      },
      yAxis: {
        type: 'value',
        boundaryGap: [0, '100%']
      },
      dataZoom: [{
        type: 'inside',
        start: 0,
        end: 10
      }, {
        start: 0,
        end: 10,
        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
        handleSize: '80%',
        handleStyle: {
          color: '#fff',
          shadowBlur: 3,
          shadowColor: 'rgba(0, 0, 0, 0.6)',
          shadowOffsetX: 2,
          shadowOffsetY: 2
        }
      }],
      series: [
        {
          name:'T1',
          type:'line',
          smooth:true,
          symbol: 'none',
          sampling: 'average',
          itemStyle: {
            normal: {
              color: 'rgb(255, 70, 131)'
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgb(255, 158, 68)'
              }, {
                offset: 1,
                color: 'rgb(255, 70, 131)'
              }])
            }
          },
          data: this.T1
        },{
          name:'T2',
          type:'line',
          smooth:true,
          symbol: 'none',
          sampling: 'average',
          itemStyle: {
            normal: {
              color: 'rgb(245, 70, 131)'
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgb(245, 158, 68)'
              }, {
                offset: 1,
                color: 'rgb(245, 70, 131)'
              }])
            }
          },
          data: this.T2
        },{
          name:'T3',
          type:'line',
          smooth:true,
          symbol: 'none',
          sampling: 'average',
          itemStyle: {
            normal: {
              color: 'rgb(165, 70, 131)'
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgb(165, 158, 68)'
              }, {
                offset: 1,
                color: 'rgb(165, 70, 131)'
              }])
            }
          },
          data: this.T3
        },{
          name:'T4',
          type:'line',
          smooth:true,
          symbol: 'none',
          sampling: 'average',
          itemStyle: {
            normal: {
              color: 'rgb(125, 70, 131)'
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgb(125, 158, 68)'
              }, {
                offset: 1,
                color: 'rgb(125, 70, 131)'
              }])
            }
          },
          data: this.T4
        },{
          name:'T5',
          type:'line',
          smooth:true,
          symbol: 'none',
          sampling: 'average',
          itemStyle: {
            normal: {
              color: 'rgb(85, 70, 131)'
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgb(85, 158, 68)'
              }, {
                offset: 1,
                color: 'rgb(85, 70, 131)'
              }])
            }
          },
          data: this.T5
        },{
          name:'T6',
          type:'line',
          smooth:true,
          symbol: 'none',
          sampling: 'average',
          itemStyle: {
            normal: {
              color: 'rgb(45, 70, 131)'
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgb(45, 158, 68)'
              }, {
                offset: 1,
                color: 'rgb(45, 70, 131)'
              }])
            }
          },
          data: this.T6
        }
      ]
    };
  }

}
