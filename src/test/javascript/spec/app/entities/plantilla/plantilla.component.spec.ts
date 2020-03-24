import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DiuconTestModule } from '../../../test.module';
import { PlantillaComponent } from 'app/entities/plantilla/plantilla.component';
import { PlantillaService } from 'app/entities/plantilla/plantilla.service';
import { Plantilla } from 'app/shared/model/plantilla.model';

describe('Component Tests', () => {
  describe('Plantilla Management Component', () => {
    let comp: PlantillaComponent;
    let fixture: ComponentFixture<PlantillaComponent>;
    let service: PlantillaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DiuconTestModule],
        declarations: [PlantillaComponent]
      })
        .overrideTemplate(PlantillaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlantillaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlantillaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Plantilla(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.plantillas && comp.plantillas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
