import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbPaginationModule } from '@ng-bootstrap/ng-bootstrap';

import { RetroRoutingModule } from './retro-routing.module';
import { BoardSummaryComponent } from './board-summary/board-summary.component';
import { BoardDetailsComponent } from './board-details/board-details.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';
import { SortByPipe } from './sort-by.pipe';
import { InformationCardComponent } from './information-card/information-card.component';
import { PortalizerSharedModule } from '../shared/shared.module';
import { SearchbarComponent } from './searchbar/searchbar.component';
import { CreateBoardModalComponent } from './create-board-modal/create-board-modal.component';
import { DeleteConfirmationModalComponent } from './delete-confirmation-modal/delete-confirmation-modal.component';
import { ToolbarComponent } from './board-details/toolbar/toolbar.component';
import { FilterPipe } from './filter.pipe';
import { AddColumnModalComponent } from './board-details/add-column-modal/add-column-modal.component';
import { DragulaModule } from 'ng2-dragula';
import { DeleteColumnModalComponent } from './board-details/delete-column-modal/delete-column-modal.component';
import { BoardSummaryCardComponent } from './board-summary/board-summary-card/board-summary-card.component';
import { BoardSummmaryControlsComponent } from './board-summary-controls/board-summary-controls.component';
import { HumanizePipe } from './humanize.pipe';
import { BoardSummaryTableComponent } from './board-summary/board-summary-table/board-summary-table.component';
import { BoardDisplayContainerComponent } from './board-summary/board-display-container/board-display-container.component';

@NgModule({
  declarations: [
    SortByPipe,
    BoardSummaryComponent,
    BoardDetailsComponent,
    InformationCardComponent,
    SearchbarComponent,
    CreateBoardModalComponent,
    DeleteConfirmationModalComponent,
    ToolbarComponent,
    FilterPipe,
    AddColumnModalComponent,
    DeleteColumnModalComponent,
    BoardSummaryCardComponent,
    BoardSummmaryControlsComponent,
    HumanizePipe,
    BoardSummaryTableComponent,
    BoardDisplayContainerComponent
  ],
  imports: [
    FontAwesomeModule,
    NgbPaginationModule,
    CommonModule,
    FormsModule,
    RetroRoutingModule,
    PortalizerSharedModule,
    DragulaModule.forRoot()
  ],
  entryComponents: [CreateBoardModalComponent, DeleteConfirmationModalComponent, AddColumnModalComponent, DeleteColumnModalComponent]
})
export class RetroModule {}
