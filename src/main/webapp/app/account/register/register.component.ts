import { MapsAPILoader } from '@agm/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { AfterViewInit, Component, ElementRef, NgZone, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { SubCategoriaService } from 'app/entities/sub-categoria/sub-categoria.service';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared/constants/error.constants';
import { ISubCategoria } from 'app/shared/model/sub-categoria.model';
import { JhiLanguageService } from 'ng-jhipster';
import { RegisterService } from './register.service';

@Component({
  selector: 'jhi-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent implements AfterViewInit {
  @ViewChild('login', { static: false })
  login?: ElementRef;

  doNotMatch = false;
  error = false;
  errorEmailExists = false;
  errorUserExists = false;
  success = false;

  registerForm = this.fb.group({
    login: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern('^[_.@A-Za-z0-9-]*$')]],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    nombreCompleto: [null, [Validators.required]],
    telefono: [null, [Validators.required]],
    fechaNacimiento: [],
    dni: [null, [Validators.min(0), Validators.max(99999999)]],
    domicilio: [null, [Validators.required]],
    latitud: [],
    longitud: [],
    profesional: [],
    estudiante: [],
    trabajador: [],
    usuarioId: [],
    subcategorias: []
  });

  @ViewChild('search', { static: false }) public searchElementRef: ElementRef;
  latitud: number | undefined;
  longitud: number | undefined;

  subcategorias: ISubCategoria[] = [];
  fechaNacimientoDp: any;

  constructor(
    private languageService: JhiLanguageService,
    private loginModalService: LoginModalService,
    private registerService: RegisterService,
    protected subCategoriaService: SubCategoriaService,
    private fb: FormBuilder,
    private mapsAPILoader: MapsAPILoader,
    private ngZone: NgZone
  ) {}

  ngAfterViewInit(): void {
    if (this.login) {
      this.login.nativeElement.focus();
    }

    this.subCategoriaService.query().subscribe((res: HttpResponse<ISubCategoria[]>) => (this.subcategorias = res.body || []));

    // load Places Autocomplete
    this.mapsAPILoader.load().then(() => {
      const autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
        types: ['address']
      });
      autocomplete.addListener('place_changed', () => {
        this.ngZone.run(() => {
          // get the place result
          const place: google.maps.places.PlaceResult = autocomplete.getPlace();

          // verify result
          if (place.geometry === undefined || place.geometry === null) {
            this.latitud = undefined;
            this.longitud = undefined;
            this.registerForm.patchValue({
              latitud: this.latitud,
              longitud: this.longitud
            });
            return;
          }

          // set latitude, longitude and zoom
          this.latitud = place.geometry.location.lat();
          this.longitud = place.geometry.location.lng();
          this.registerForm.patchValue({
            domicilio: place.formatted_address,
            latitud: this.latitud,
            longitud: this.longitud
          });
        });
      });
    });
  }

  register(): void {
    this.doNotMatch = false;
    this.error = false;
    this.errorEmailExists = false;
    this.errorUserExists = false;

    const password = this.registerForm.get(['password'])!.value;
    if (password !== this.registerForm.get(['confirmPassword'])!.value) {
      this.doNotMatch = true;
    } else {
      const login = this.registerForm.get(['login'])!.value;
      const email = this.registerForm.get(['email'])!.value;
      this.registerService.save({ login, email, password, langKey: this.languageService.getCurrentLanguage() }).subscribe(
        () => (this.success = true),
        response => this.processError(response)
      );
    }
  }

  openLogin(): void {
    this.loginModalService.open();
  }

  private processError(response: HttpErrorResponse): void {
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = true;
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = true;
    } else {
      this.error = true;
    }
  }

  trackById(index: number, item: ISubCategoria): any {
    return item.id;
  }

  getSelected(selectedVals: ISubCategoria[], option: ISubCategoria): ISubCategoria {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
