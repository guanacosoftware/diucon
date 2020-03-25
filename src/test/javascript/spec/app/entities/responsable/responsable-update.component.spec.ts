import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DiuconTestModule } from '../../../test.module';
import { ResponsableUpdateComponent } from 'app/entities/responsable/responsable-update.component';
import { ResponsableService } from 'app/entities/responsable/responsable.service';
import { Responsable } from 'app/shared/model/responsable.model';

describe('Component Tests', () => {
  describe('Responsable Management Update Component', () => {
    let comp: ResponsableUpdateComponent;
    let fixture: ComponentFixture<ResponsableUpdateComponent>;
    let service: ResponsableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [ResponsableUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ResponsableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ResponsableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ResponsableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Responsable(123);
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
        const entity = new Responsable();
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
