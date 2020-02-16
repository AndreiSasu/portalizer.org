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
    AddColumnModalComponent
  ],
  imports: [FontAwesomeModule, NgbPaginationModule, CommonModule, FormsModule, RetroRoutingModule, PortalizerSharedModule],
  entryComponents: [CreateBoardModalComponent, DeleteConfirmationModalComponent, AddColumnModalComponent]
})
export class RetroModule {}
