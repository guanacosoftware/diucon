import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiuconTestModule } from '../../../test.module';
import { PlantillaUpdateComponent } from 'app/entities/plantilla/plantilla-update.component';
import { PlantillaService } from 'app/entities/plantilla/plantilla.service';
import { Plantilla } from 'app/shared/model/plantilla.model';

describe('Component Tests', () => {
  describe('Plantilla Management Update Component', () => {
    let comp: PlantillaUpdateComponent;
    let fixture: ComponentFixture<PlantillaUpdateComponent>;
    let service: PlantillaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [PlantillaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlantillaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlantillaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlantillaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Plantilla(123);
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
        const entity = new Plantilla();
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
