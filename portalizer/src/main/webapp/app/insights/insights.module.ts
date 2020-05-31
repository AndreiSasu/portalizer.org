import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InsightsRoutingModule } from './insights-routing.module';
import { InsightsComponent } from './insights/insights.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@NgModule({
  declarations: [InsightsComponent],
  imports: [CommonModule, NgxChartsModule, InsightsRoutingModule]
})
export class InsightsModule {}
