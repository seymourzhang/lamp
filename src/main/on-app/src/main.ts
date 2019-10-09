import {enableProdMode, ViewEncapsulation} from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import { registerLocaleData } from '@angular/common';
import zh from '@angular/common/locales/zh';
import {debug} from "util";

registerLocaleData(zh);
(function preloaderFinished() {
  const body = document.querySelector('body')!;
  const preloader = document.querySelector('.preloader')!;

  body.style.overflow = 'hidden';

  function remove() {
    // preloader value null when running --hmr
    if (!preloader) return;
    preloader.addEventListener('transitionend', () => {
      preloader.className = 'preloader-hidden';
    });

    preloader.className += ' preloader-hidden-add preloader-hidden-add-active';
  }
  console.log(window);
  debugger;
  (window as any).appBootstrap = () => {
    setTimeout(() => {
      remove();
      body.style.overflow = '';
    }, 100);
  };
})();

const bootstrap = () => {
  return platformBrowserDynamic().bootstrapModule(AppModule, {
    defaultEncapsulation: ViewEncapsulation.Emulated,
    preserveWhitespaces: false,
  }).then((res) => {
    if ((<any>window).appBootstrap) {
      (<any>window).appBootstrap();
    }
    return res;
  });
};

if (environment.production) {
  enableProdMode();
}

/*
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.log(err));
*/
bootstrap();


