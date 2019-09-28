import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

import { MatSliderModule } from '@angular/material/slider';

@NgModule({
  imports: [GatewaySharedModule, MatSliderModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class GatewayHomeModule {}
