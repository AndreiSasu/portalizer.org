import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RetroRoutingModule } from './retro-routing.module';
import { BoardSummaryComponent } from './board-summary/board-summary.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { BoardDetailsComponent } from './board-details/board-details.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [BoardSummaryComponent, BoardDetailsComponent],
  imports: [FontAwesomeModule, MatGridListModule, CommonModule, FormsModule, RetroRoutingModule]
})
export class RetroModule {}
