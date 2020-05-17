import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { faListUl, faSortAmountDown, faSortAmountUp, faTh, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { ClearSearch, TextSearch, BoardsFilterEvent } from '../model/boards';
import { Subject } from 'rxjs';

@Component({
  selector: 'jhi-board-summary-controls',
  templateUrl: './board-summary-controls.component.html',
  styleUrls: ['./board-summary-controls.component.scss']
})
/* eslint-disable */
export class BoardSummmaryControlsComponent implements OnInit {
  faTh = faTh;
  faListUl = faListUl;
  faSortUp = faSortAmountUp;
  faSortDown = faSortAmountDown;
  faTimesCircle = faTimesCircle;

  @Input() savedFilter: BoardsFilterEvent;
  @Output() filterChanged = new EventEmitter<BoardsFilterEvent>();

  currentBoardsFilter: BoardsFilterEvent;

  inputSubject = new Subject<any>();

  defaultSearchValue: string;
  defaultSearchField: string = 'name';

  enableRelevance: boolean;

  constructor() {}

  ngOnInit() {
    this.currentBoardsFilter = undefined === this.savedFilter ? BoardsFilterEvent.default() : this.savedFilter;
    if (this.currentBoardsFilter.textBoxState instanceof TextSearch) {
      this.defaultSearchValue = this.currentBoardsFilter.textBoxState.search;
      this.defaultSearchField = this.currentBoardsFilter.textBoxState.fieldName;
    }
    console.log(this.currentBoardsFilter);
  }

  onSearchInputChange(event: ClearSearch | TextSearch) {
    const previousState = this.currentBoardsFilter.textBoxState;
    this.currentBoardsFilter.textBoxState = event;

    if (previousState instanceof ClearSearch && event instanceof TextSearch) {
      this.enableRelevance = true;
      this.currentBoardsFilter.sortByFieldName = 'relevance';
    } else if (event instanceof ClearSearch) {
      this.enableRelevance = false;
      this.currentBoardsFilter = BoardsFilterEvent.default();
    }

    this.doEmit();
  }

  doEmit() {
    console.log(this.currentBoardsFilter);
    this.filterChanged.emit(this.currentBoardsFilter);
  }

  clearAll() {
    this.enableRelevance = false;
    this.currentBoardsFilter = BoardsFilterEvent.default();
    this.inputSubject.next(TextSearch.default());
    this.doEmit();
  }
}
