import { Component, OnInit, Input } from '@angular/core';
import {
  faPlusCircle,
  faSync,
  faSave,
  faPencilAlt,
  faClock,
  faSearch,
  faChevronLeft,
  faChevronRight
} from '@fortawesome/free-solid-svg-icons';
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
  faSave = faSave;
  faClock = faClock;

  @Input() board: Board;

  editMode = false;
  edited = false;

  constructor() {}

  /*eslint-disable*/
  ngOnInit() {
    console.log(this.board);
  }

  onSave(event: any) {
    this.edited = true;
    this.editMode = false;
    setTimeout(
      function() {
        this.edited = false;
      }.bind(this),
      3000
    );
  }
}
