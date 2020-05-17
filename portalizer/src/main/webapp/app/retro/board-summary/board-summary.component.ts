import { Component, OnInit, Injector } from '@angular/core';
import { BoardService } from '../board.service';
import {
  BoardSummary,
  CreateBoardRequest,
  BoardTemplate,
  TextSearch,
  ClearSearch,
  BoardsView,
  BoardsFilterEvent as BoardsFilter
} from '../model/boards';
import { faEye, faListUl, faTh, faTrash, faArchive, faPlusSquare, faClock } from '@fortawesome/free-solid-svg-icons';
import { CommunicationService } from '../communication.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PaginationPage } from '../model/pagination';
import { CreateBoardModalComponent } from '../create-board-modal/create-board-modal.component';
import { Router, ActivatedRoute } from '@angular/router';
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

  currentSearch: TextSearch;

  constructor(
    private boardService: BoardService,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    this.route.queryParamMap.subscribe(params => {
      if (params.keys.length > 0) {
        this.restoreState(params);
      }
      this.updateLocation();
    });

    this.getBoardPages();
    this.boardService.getBoardTemplates().subscribe(boardTemplates => {
      this.boardTemplates = [...boardTemplates];
    });
  }

  onFilterChange(event: BoardsFilter) {
    this.savedFilter = event;
    this.updateLocation();
  }

  updateLocation() {
    this.location.go(filterToLocation('/retro/boards', this.currentPage - 1, this.view, this.savedFilter));
  }

  restoreState(params: any) {
    this.view = params.has('view') && params.get('view') == 'LIST' ? BoardsView.LIST : BoardsView.GRID;
    if (params.has('sort')) {
      this.savedFilter.sortByFieldName = params.get('sort').split(';')[0];
      this.savedFilter.sortDirection = params.get('sort').split(';')[1];
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
    this.boardService.getBoardsPage(this.currentPage - 1).subscribe(data => {
      this.pageObject = data;
      console.log(this.pageObject);
      this.boardSummaries = this.pageObject['content'];
    });
  }

  onPageChange(event: number) {
    console.log(event);
    this.currentPage = event;
    if (this.currentSearch === undefined) {
      this.getBoardPages();
    } else {
      this.onSearch(this.currentSearch, this.currentPage - 1);
    }
    this.updateLocation();
  }

  onSearch(event: TextSearch, currentPage?: number) {
    this.currentSearch = event;
    this.boardService.searchHeavy(event.fieldName, event.search, currentPage).subscribe(data => {
      this.pageObject = data;
      this.boardSummaries = this.pageObject['content'];
    });
  }

  onClear(event: ClearSearch) {
    this.currentSearch = undefined;
    this.getBoardPages();
  }

  openCreateBoardModal() {
    const submit = new CommunicationService<CreateBoardRequest>();
    this.modalService.open(CreateBoardModalComponent, {
      centered: true,

      injector: Injector.create([
        {
          provide: BoardService,
          useValue: this.boardService
        },
        {
          provide: CommunicationService,
          useValue: submit
        }
      ])
    });
    submit.subject.subscribe(createBoardRequest => {
      this.onSubmit(createBoardRequest);
    });
  }

  onSubmit(request: CreateBoardRequest) {
    console.log(request);
    this.boardService.createBoard(request).subscribe(
      board => {
        console.log(board);
        console.log(this.router.url);
        this.router.navigateByUrl('retro/boards/' + board.id);
      },
      error => {
        console.log(error);
      }
    );
  }
}
