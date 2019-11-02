import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RetroRoutingModule } from './retro-routing.module';
import { BoardSummaryComponent } from './board-summary/board-summary.component';
import { MatGridListModule } from '@angular/material/grid-list';

@NgModule({
  declarations: [BoardSummaryComponent],
  imports: [MatGridListModule, CommonModule, RetroRoutingModule]
})
export class RetroModule {}
