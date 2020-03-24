import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IncidenteService } from 'app/entities/incidente/incidente.service';
import { IIncidente, Incidente } from 'app/shared/model/incidente.model';
import { Estado } from 'app/shared/model/enumerations/estado.model';

describe('Service Tests', () => {
  describe('Incidente Service', () => {
    let injector: TestBed;
    let service: IncidenteService;
    let httpMock: HttpTestingController;
    let elemDefault: IIncidente;
    let expectedResult: IIncidente | IIncidente[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IncidenteService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Incidente(0, currentDate, 'AAAAAAA', Estado.CREADA, 'AAAAAAA', 0, 0, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            fechaResolucion: currentDate.format(DATE_FORMAT),
            fechaCierre: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Incidente', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_TIME_FORMAT),
            fechaResolucion: currentDate.format(DATE_FORMAT),
            fechaCierre: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaResolucion: currentDate,
            fechaCierre: currentDate
          },
          returnedFromService
        );

        service.create(new Incidente()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Incidente', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            cuerpo: 'BBBBBB',
            estado: 'BBBBBB',
            localizacion: 'BBBBBB',
            latitud: 1,
            longitud: 1,
            fechaResolucion: currentDate.format(DATE_FORMAT),
            fechaCierre: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaResolucion: currentDate,
            fechaCierre: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Incidente', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_TIME_FORMAT),
            cuerpo: 'BBBBBB',
            estado: 'BBBBBB',
            localizacion: 'BBBBBB',
            latitud: 1,
            longitud: 1,
            fechaResolucion: currentDate.format(DATE_FORMAT),
            fechaCierre: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
            fechaResolucion: currentDate,
            fechaCierre: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Incidente', () => {
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
