import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './routes/user/login/login.component';
import {HomeComponent} from './routes/home/home.component';
import {ShowComponent} from "./routes/show/show.component";
import {Identifiers} from "@angular/compiler";
import {IdentityComponent} from "./routes/user/identity/identity.component";
import {AnalyseComponent} from "./routes/analyse/analyse.component";
import {MygirlComponent} from "./routes/mygirl/mygirl.component";
import {IndexComponent} from "./routes/index/index.component";

const routes: Routes = [
  { path: '', redirectTo: 'index', pathMatch: 'full' },
  { path: 'index', component: IndexComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'home',
    component: HomeComponent,
    children: [
      { path: '', redirectTo: 'show', pathMatch: 'full' },
      { path: 'show', component: ShowComponent },
      { path: 'identity', component: IdentityComponent },
      { path: 'analyse', component: AnalyseComponent },
      { path: 'cherry', component: MygirlComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
