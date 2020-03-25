import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DiuconTestModule } from '../../../test.module';
import { IncidenteDetailComponent } from 'app/entities/incidente/incidente-detail.component';
import { Incidente } from 'app/shared/model/incidente.model';

describe('Component Tests', () => {
  describe('Incidente Management Detail Component', () => {
    let comp: IncidenteDetailComponent;
    let fixture: ComponentFixture<IncidenteDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ incidente: new Incidente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [IncidenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IncidenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncidenteDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load incidente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incidente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
