import { Component, OnInit, Input } from '@angular/core';
import { faPlusCircle, faSync, faPencilAlt, faSearch, faChevronLeft, faChevronRight } from '@fortawesome/free-solid-svg-icons';
import { Board } from 'app/retro/model/boards';

@Component({
  selector: 'jhi-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {
  faPlusCircle = faPlusCircle;
  faSync = faSync;
  faChevronLeft = faChevronLeft;
  faChevronRight = faChevronRight;
  faSearch = faSearch;
  faPencilAlt = faPencilAlt;

  @Input() board: Board;

  editMode = false;

  constructor() {}

  ngOnInit() {}
}
