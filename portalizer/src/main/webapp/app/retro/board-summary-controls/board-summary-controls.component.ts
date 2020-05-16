import { Component, OnInit } from '@angular/core';
import { faListUl, faSortAmountDown, faSortAmountUp, faTh, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { defaultBoardsFilter, ClearSearch, TextSearch } from '../model/boards';
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
  currentBoardsFilter = defaultBoardsFilter();
  clearInput = new Subject<any>();
  constructor() {}

  ngOnInit() {
    console.log(this.currentBoardsFilter);
  }

  onSearchInputChange(event: ClearSearch | TextSearch) {
    this.currentBoardsFilter.textBoxState = event;
    this.doEmit();
  }

  doEmit() {
    console.log(this.currentBoardsFilter);
  }

  clearAll() {
    this.currentBoardsFilter = defaultBoardsFilter();
    this.clearInput.next('');
    console.log(this.currentBoardsFilter);
  }
}
