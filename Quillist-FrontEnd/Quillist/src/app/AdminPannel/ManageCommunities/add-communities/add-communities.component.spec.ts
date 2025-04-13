import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCommunitiesComponent } from './add-communities.component';

describe('AddCommunitiesComponent', () => {
  let component: AddCommunitiesComponent;
  let fixture: ComponentFixture<AddCommunitiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddCommunitiesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCommunitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
