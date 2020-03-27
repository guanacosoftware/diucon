import { AgmCoreModule, GoogleMapsAPIWrapper } from '@agm/core';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { DiuconCoreModule } from 'app/core/core.module';
import { DiuconSharedModule } from 'app/shared/shared.module';
import { environment } from '../environment/environment';
import { DiuconAppRoutingModule } from './app-routing.module';
import { DiuconEntityModule } from './entities/entity.module';
import { DiuconHomeModule } from './home/home.module';
import { ErrorComponent } from './layouts/error/error.component';
import { FooterComponent } from './layouts/footer/footer.component';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import './vendor';

@NgModule({
  imports: [
    BrowserModule,
    DiuconSharedModule,
    DiuconCoreModule,
    DiuconHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    DiuconEntityModule,
    DiuconAppRoutingModule,
    AgmCoreModule.forRoot({
      apiKey: environment.apiKey,
      libraries: ['places']
    })
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
  providers: [GoogleMapsAPIWrapper]
})
export class DiuconAppModule {}
