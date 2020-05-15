import { Component, OnInit } from '@angular/core';
import { faTh, faListUl, faSortAmountUp, faSortAmountDown } from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'jhi-board-list-controls',
  templateUrl: './board-list-controls.component.html',
  styleUrls: ['./board-list-controls.component.scss']
})
// eslint-disable
export class BoardListControlsComponent implements OnInit {
  faTh = faTh;
  faListUl = faListUl;
  faSortUp = faSortAmountUp;
  faSortDown = faSortAmountDown;

  constructor() {}

  ngOnInit() {}

  onClear(event: any) {
    event.preventDefault();
  }

  onSearch(event: any) {
    event.preventDefault();
  }
}
