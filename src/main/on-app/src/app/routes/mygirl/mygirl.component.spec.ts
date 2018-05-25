import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MygirlComponent } from './mygirl.component';

describe('MygirlComponent', () => {
  let component: MygirlComponent;
  let fixture: ComponentFixture<MygirlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MygirlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MygirlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
