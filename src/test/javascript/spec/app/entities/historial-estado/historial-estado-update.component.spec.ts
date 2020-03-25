import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiuconTestModule } from '../../../test.module';
import { HistorialEstadoUpdateComponent } from 'app/entities/historial-estado/historial-estado-update.component';
import { HistorialEstadoService } from 'app/entities/historial-estado/historial-estado.service';
import { HistorialEstado } from 'app/shared/model/historial-estado.model';

describe('Component Tests', () => {
  describe('HistorialEstado Management Update Component', () => {
    let comp: HistorialEstadoUpdateComponent;
    let fixture: ComponentFixture<HistorialEstadoUpdateComponent>;
    let service: HistorialEstadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [HistorialEstadoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HistorialEstadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HistorialEstadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HistorialEstadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HistorialEstado(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new HistorialEstado();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
