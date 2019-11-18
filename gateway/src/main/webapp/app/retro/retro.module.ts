import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RetroRoutingModule } from './retro-routing.module';
import { BoardSummaryComponent } from './board-summary/board-summary.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { BoardDetailsComponent } from './board-details/board-details.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';
import { SortByPipe } from './sort-by.pipe';
import { InformationCardComponent } from './information-card/information-card.component';
import { GatewaySharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [SortByPipe, BoardSummaryComponent, BoardDetailsComponent, InformationCardComponent],
  imports: [FontAwesomeModule, GatewaySharedModule, MatGridListModule, CommonModule, FormsModule, RetroRoutingModule]
})
export class RetroModule {}
