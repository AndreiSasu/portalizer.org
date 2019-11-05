import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RetroRoutingModule } from './retro-routing.module';
import { BoardSummaryComponent } from './board-summary/board-summary.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { BoardDetailsComponent } from './board-details/board-details.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [BoardSummaryComponent, BoardDetailsComponent],
  imports: [FontAwesomeModule, MatGridListModule, CommonModule, RetroRoutingModule]
})
export class RetroModule {}
