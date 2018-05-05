import {Inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {isNullOrUndefined} from 'util';
import * as CryptoJS from 'crypto-js';
import {Constants} from "../../share/constants";

export type method = 'POST' | 'GET' ;
export interface Conn {
  url?: string;
  method?: method;
  isAsync?: boolean;
  param?: { } ;
  isLocal?: boolean;
  service: string;
  [key: string]: any;
}

@Injectable()
export class HttpService {

  constructor( public http: HttpClient
  ) { }

  get conn(): Conn {
    return {
      url: '',
      method: 'POST',
      isAsync: false,
      param: null ,
      isLocal: false ,
      service: null
    };
  }

  public begin(endpoint: string, conn: Conn) {
    if(endpoint.startsWith('http')){
      conn.url = endpoint;
    }else{
      if(Constants.APP_SERVER_MODE === 'DEBUG') {
        conn.url = Constants.APP_SERVER_DEBUG_ADDR + endpoint;
      }else if (Constants.APP_SERVER_MODE === 'PRODUCTION') {
        conn.url = Constants.APP_SERVER_PRODUCTION_ADDR + endpoint;
      }
      console.log('ApiService URL',conn.url);
      console.time('ApiService time');
    }
  }

  // 请求
  request = function ( conn: Conn ) {
    this.begin(conn.url, conn);
    const isLocal = ( null == conn.isLocal ? false : conn.isLocal );
    const isPost = ( isLocal ? false : (  'POST' === ( null == conn.method ? 'POST' : conn.method ) ? true : false  ) );
    const address = conn.url;
    // const param = ( null == conn.param ) ? { } : conn.param;
    // console.log( JSON.stringify(param) );
    const paramStr = CryptoJS.SHA256((isNullOrUndefined(conn.param)) ? '' : (JSON.stringify(conn.param))) ;
    //const signStr = ( isNullOrUndefined( this.tokenSrv.get().token ) ? '' : this.tokenSrv.get().token );
    const options = {
      // params: { param:  JSON.stringify(conn.param) }
      withCredentials: true
      , responseType: ( isNullOrUndefined(conn['responseType']) ) ? 'json' : conn['responseType']
      , headers: { __sign__: paramStr + '#&!:!&#'  }
    };
    // console.log(address);
    if ( !isNullOrUndefined(conn.param) ) {
      options['params'] = conn.param;
    }

    // console.log('Http options: ');
    // console.log(options);
    if ( isPost ) {
      return this.http.post( address, null, options);
    } else {
      return this.http.get( address, options );
    }
  };




}
