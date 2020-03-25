import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DiuconTestModule } from '../../../test.module';
import { CategoriaDetailComponent } from 'app/entities/categoria/categoria-detail.component';
import { Categoria } from 'app/shared/model/categoria.model';

describe('Component Tests', () => {
  describe('Categoria Management Detail Component', () => {
    let comp: CategoriaDetailComponent;
    let fixture: ComponentFixture<CategoriaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ categoria: new Categoria(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [CategoriaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategoriaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategoriaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load categoria on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categoria).toEqual(jasmine.objectContaining({ id: 123 }));
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
