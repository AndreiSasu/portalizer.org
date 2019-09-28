import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  imports: [GatewaySharedModule, FontAwesomeModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class GatewayHomeModule {}
