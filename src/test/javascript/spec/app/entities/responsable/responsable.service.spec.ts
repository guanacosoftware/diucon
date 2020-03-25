import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ResponsableService } from 'app/entities/responsable/responsable.service';
import { IResponsable, Responsable } from 'app/shared/model/responsable.model';

describe('Service Tests', () => {
  describe('Responsable Service', () => {
    let injector: TestBed;
    let service: ResponsableService;
    let httpMock: HttpTestingController;
    let elemDefault: IResponsable;
    let expectedResult: IResponsable | IResponsable[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ResponsableService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Responsable(0, 'AAAAAAA', 'AAAAAAA', currentDate, 0, 'AAAAAAA', 0, 0, false, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaNacimiento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Responsable', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaNacimiento: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaNacimiento: currentDate
          },
          returnedFromService
        );

        service.create(new Responsable()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Responsable', () => {
        const returnedFromService = Object.assign(
          {
            nombreCompleto: 'BBBBBB',
            telefono: 'BBBBBB',
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            dni: 1,
            domicilio: 'BBBBBB',
            latitud: 1,
            longitud: 1,
            profesional: true,
            estudiante: true,
            trabajador: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaNacimiento: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Responsable', () => {
        const returnedFromService = Object.assign(
          {
            nombreCompleto: 'BBBBBB',
            telefono: 'BBBBBB',
            fechaNacimiento: currentDate.format(DATE_FORMAT),
            dni: 1,
            domicilio: 'BBBBBB',
            latitud: 1,
            longitud: 1,
            profesional: true,
            estudiante: true,
            trabajador: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaNacimiento: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Responsable', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
