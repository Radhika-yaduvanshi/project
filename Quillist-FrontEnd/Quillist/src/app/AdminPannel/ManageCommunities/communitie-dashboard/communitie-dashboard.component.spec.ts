import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommunitieDashboardComponent } from './communitie-dashboard.component';

describe('CommunitieDashboardComponent', () => {
  let component: CommunitieDashboardComponent;
  let fixture: ComponentFixture<CommunitieDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CommunitieDashboardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CommunitieDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
