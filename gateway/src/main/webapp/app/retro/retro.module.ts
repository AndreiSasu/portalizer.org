import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RetroRoutingModule } from './retro-routing.module';
import { BoardSummaryComponent } from './board-summary/board-summary.component';

@NgModule({
  declarations: [BoardSummaryComponent],
  imports: [CommonModule, RetroRoutingModule]
})
export class RetroModule {}
