import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DiuconTestModule } from '../../../test.module';
import { SubCategoriaDetailComponent } from 'app/entities/sub-categoria/sub-categoria-detail.component';
import { SubCategoria } from 'app/shared/model/sub-categoria.model';

describe('Component Tests', () => {
  describe('SubCategoria Management Detail Component', () => {
    let comp: SubCategoriaDetailComponent;
    let fixture: ComponentFixture<SubCategoriaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ subCategoria: new SubCategoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [SubCategoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SubCategoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SubCategoriaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load subCategoria on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.subCategoria).toEqual(jasmine.objectContaining({ id: 123 }));
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
