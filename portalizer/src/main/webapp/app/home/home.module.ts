import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PortalizerSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [PortalizerSharedModule, RouterModule.forChild([HOME_ROUTE]), NgxChartsModule, BrowserAnimationsModule],
  declarations: [HomeComponent]
})
export class PortalizerHomeModule {}
