import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  inco: any = "./assets/image/inco.ico";

  constructor(private router: Router) { }

  ngOnInit() {
  }

  isCollapsed = true;
  toShowPage() {
    // this.router.navigate(['show']).then();
  }

}
