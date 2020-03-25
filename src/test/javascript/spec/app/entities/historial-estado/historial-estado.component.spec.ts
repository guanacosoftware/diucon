import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DiuconTestModule } from '../../../test.module';
import { HistorialEstadoComponent } from 'app/entities/historial-estado/historial-estado.component';
import { HistorialEstadoService } from 'app/entities/historial-estado/historial-estado.service';
import { HistorialEstado } from 'app/shared/model/historial-estado.model';

describe('Component Tests', () => {
  describe('HistorialEstado Management Component', () => {
    let comp: HistorialEstadoComponent;
    let fixture: ComponentFixture<HistorialEstadoComponent>;
    let service: HistorialEstadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [HistorialEstadoComponent]
      })
        .overrideTemplate(HistorialEstadoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistorialEstadoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistorialEstadoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HistorialEstado(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.historialEstados && comp.historialEstados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
