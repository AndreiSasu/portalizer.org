import { Component, OnInit } from '@angular/core';
import { BoardService } from '../board.service';
import { BoardSummary, BoardTemplate, TextSearch, BoardsView, BoardsFilterEvent as BoardsFilter } from '../model/boards';
import { faEye, faListUl, faTh, faTrash, faArchive, faPlusSquare, faClock } from '@fortawesome/free-solid-svg-icons';
import { PaginationPage } from '../model/pagination';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { filterToLocation } from '../model/boards';

@Component({
  selector: 'jhi-board-summary',
  templateUrl: './board-summary.component.html',
  styleUrls: ['./board-summary.component.scss']
})
/* eslint-disable */
export class BoardSummaryComponent implements OnInit {
  boardSummaries: BoardSummary[];
  page = 1;
  faEye = faEye;
  faTrash = faTrash;
  faArchive = faArchive;
  faClock = faClock;
  faListUl = faListUl;
  faTh = faTh;
  faPlusSquare = faPlusSquare;
  closeResult: string;
  boardTemplates: Array<BoardTemplate>;

  GRID = BoardsView.GRID;
  LIST = BoardsView.LIST;

  view = BoardsView.GRID;

  savedFilter = BoardsFilter.default();

  formModel = {
    boardName: '',
    templateKey: '',
    description: ''
  };

  pageObject: PaginationPage<BoardSummary>;
  currentPage = 1;
  paginationSize = 'lg';
  pageSize = 25;

  constructor(private boardService: BoardService, private route: ActivatedRoute, private location: Location) {}

  ngOnInit() {
    this.route.queryParamMap.subscribe(params => {
      if (params.keys.length > 0) {
        this.restoreState(params);
      }
      this.updateLocation();
      this.getBoardPages();
    });

    this.boardService.getBoardTemplates().subscribe(boardTemplates => {
      this.boardTemplates = [...boardTemplates];
    });
  }

  onFilterChange(event: BoardsFilter) {
    this.savedFilter = event;
    this.updateLocation();
    this.getBoardPages();
  }

  updateLocation() {
    this.location.go(filterToLocation('/retro/boards', this.currentPage - 1, this.view, this.savedFilter));
  }

  restoreState(params: any) {
    this.view = params.has('view') && params.get('view') == 'LIST' ? BoardsView.LIST : BoardsView.GRID;
    if (params.has('sort')) {
      this.savedFilter.sortByFieldName = params.get('sort').split(',')[0];
      this.savedFilter.sortDirection = params.get('sort').split(',')[1];
    }
    if (params.has('size')) {
      this.savedFilter.itemsPerPage = params.get('size');
    }
    if (params.has('searchField') && params.has('searchPhrase')) {
      let textSearch = new TextSearch(params.get('searchField'), params.get('searchPhrase'));
      this.savedFilter.textBoxState = textSearch;
    }
  }

  getBoardPages() {
    this.boardService.getBoardsPage(this.currentPage - 1, this.savedFilter).subscribe(data => {
      this.pageObject = data;
      this.pageSize = this.pageObject.size;
      this.boardSummaries = this.pageObject['content'];
    });
  }

  onPageChange(event: number) {
    console.log(event);
    this.currentPage = event;
    this.getBoardPages();
    this.updateLocation();
  }
}
