import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiuconTestModule } from '../../../test.module';
import { ResponsableDetailComponent } from 'app/entities/responsable/responsable-detail.component';
import { Responsable } from 'app/shared/model/responsable.model';

describe('Component Tests', () => {
  describe('Responsable Management Detail Component', () => {
    let comp: ResponsableDetailComponent;
    let fixture: ComponentFixture<ResponsableDetailComponent>;
    const route = ({ data: of({ responsable: new Responsable(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [ResponsableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ResponsableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ResponsableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load responsable on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.responsable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
