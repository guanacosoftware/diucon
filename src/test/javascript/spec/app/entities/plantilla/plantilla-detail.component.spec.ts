import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DiuconTestModule } from '../../../test.module';
import { PlantillaDetailComponent } from 'app/entities/plantilla/plantilla-detail.component';
import { Plantilla } from 'app/shared/model/plantilla.model';

describe('Component Tests', () => {
  describe('Plantilla Management Detail Component', () => {
    let comp: PlantillaDetailComponent;
    let fixture: ComponentFixture<PlantillaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ plantilla: new Plantilla(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [PlantillaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlantillaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlantillaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load plantilla on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plantilla).toEqual(jasmine.objectContaining({ id: 123 }));
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
