import { Component, OnInit } from '@angular/core';
import { BoardService } from '../board.service';
import { BoardSummary, CreateBoardRequest, BoardTemplate, TextSearch, ClearSearch } from '../model/boards';
import { faEye, faTrash, faArchive, faPlusSquare } from '@fortawesome/free-solid-svg-icons';
import { ColorsService } from '../colors.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { PaginationPage } from '../model/pagination';

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
  faPlusSquare = faPlusSquare;
  colorService: ColorsService;
  closeResult: string;
  boardTemplates: Array<BoardTemplate>;

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

  constructor(private boardService: BoardService, private router: Router, private modalService: NgbModal, colorService: ColorsService) {
    this.colorService = colorService;
  }

  ngOnInit() {
    this.getBoardPages();
    this.boardService.getBoardTemplates().subscribe(boardTemplates => {
      this.boardTemplates = [...boardTemplates];
    });
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

  openVerticallyCentered(content) {
    console.log(content);
    this.modalService.open(content, { centered: true });
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
