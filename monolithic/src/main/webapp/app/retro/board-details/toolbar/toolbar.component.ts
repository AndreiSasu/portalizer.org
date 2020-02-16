import { EventEmitter, Component, OnInit, Input, Output } from '@angular/core';
import { faPlusCircle, faSync, faSave, faPencilAlt, faClock, faSearch } from '@fortawesome/free-solid-svg-icons';
import { Board, RefreshBoardRequest, ColumnAddRequest } from 'app/retro/model/boards';

@Component({
  selector: 'jhi-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {
  faPlusCircle = faPlusCircle;
  faSync = faSync;
  faSearch = faSearch;
  faPencilAlt = faPencilAlt;
  faSave = faSave;
  faClock = faClock;

  @Input() board: Board;
  @Output() searching = new EventEmitter<string>();
  @Output() refreshed = new EventEmitter<RefreshBoardRequest>();
  @Output() addColumn = new EventEmitter<ColumnAddRequest>();

  editMode = false;
  edited = false;
  searchModel: string;

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

  onRefresh(event: any) {}

  onAdd(event: any) {}

  onSearch(event: string) {
    console.log(event);
    if (event.length == 0) {
      this.searchModel = '';
    }
    this.searching.emit(this.searchModel);
  }
}
