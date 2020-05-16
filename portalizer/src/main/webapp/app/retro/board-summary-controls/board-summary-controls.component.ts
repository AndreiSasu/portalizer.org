import { Component, OnInit } from '@angular/core';
import { faListUl, faSortAmountDown, faSortAmountUp, faTh, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { defaultBoardsFilter } from '../model/boards';

@Component({
  selector: 'jhi-board-summary-controls',
  templateUrl: './board-summary-controls.component.html',
  styleUrls: ['./board-summary-controls.component.scss']
})
// eslint-disable
export class BoardSummmaryControlsComponent implements OnInit {
  faTh = faTh;
  faListUl = faListUl;
  faSortUp = faSortAmountUp;
  faSortDown = faSortAmountDown;
  faTimesCircle = faTimesCircle;
  defaultBoardsFilter = defaultBoardsFilter;

  currentBoardsFilter = defaultBoardsFilter;

  constructor() {}

  ngOnInit() {}

  onClear(event: any) {
    event.preventDefault();
  }

  onSearch(event: any) {
    event.preventDefault();
  }
}
