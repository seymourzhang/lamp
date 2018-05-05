import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {HttpService} from "../../../net/service/http.service";
import {NzMessageService} from "ng-zorro-antd";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  validateForm: FormGroup;
  valLogin;
  valImageBackgroundUrl = '';
  _isSpinning = false;

  constructor(private fb: FormBuilder,
              private router: Router,
              public http: HttpService,
              private _message: NzMessageService) {

    // console.log(trans);

    this.valLogin = {
      code: 'login'
      , username : 'userName'
      , password : 'password'
      , conn: http.conn
    };
    this.validateForm = fb.group({
      name: [null, Validators.compose([Validators.required])],
      password: [null, Validators.required],
      remember_me: [null]
    });
    this.valImageBackgroundUrl = './assets/image/Cosmos01.jpg';
  }

  ngOnInit() {
    this.validateForm =  this.fb.group({
      userName: [ null, [ Validators.required ] ],
      password: [ null, [ Validators.required ] ],
      remember: [ true ],
    });
  }


  _submitForm() {
    this._isSpinning = true;
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
    }

    if (this.validateForm.valid) {
      console.log('Valid!');
      // this.valLogin.conn.url = '/auth/toLogin';
      this.valLogin.conn.url = '/user/userLogin';
      this.valLogin.conn.isLocal = true;
      this.valLogin.conn.param = {
        user_name: this.validateForm.controls['userName'].value,
        user_password: this.validateForm.controls['password'].value
      };
      this.http.request(this.valLogin.conn)
        .subscribe(
          (val) => {
            console.log('POST call successful value returned in body', val);
            setTimeout(() => {
              this._isSpinning = false;
              if (val.Result === 'Success') {
                this.router.navigate(['home']).then();
              } else if (val.Result === 'Fail') {
                this._message.create('error', val.Error);
              }
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
}
