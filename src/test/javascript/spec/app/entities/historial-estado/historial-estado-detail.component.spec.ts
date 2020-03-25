import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiuconTestModule } from '../../../test.module';
import { HistorialEstadoDetailComponent } from 'app/entities/historial-estado/historial-estado-detail.component';
import { HistorialEstado } from 'app/shared/model/historial-estado.model';

describe('Component Tests', () => {
  describe('HistorialEstado Management Detail Component', () => {
    let comp: HistorialEstadoDetailComponent;
    let fixture: ComponentFixture<HistorialEstadoDetailComponent>;
    const route = ({ data: of({ historialEstado: new HistorialEstado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [HistorialEstadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(HistorialEstadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HistorialEstadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load historialEstado on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.historialEstado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
