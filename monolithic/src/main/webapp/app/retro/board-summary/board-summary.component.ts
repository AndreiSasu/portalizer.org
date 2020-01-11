import { Component, OnInit } from '@angular/core';
import { BoardService } from '../board.service';
import { BoardSummary, Boards, CreateBoardRequest, BoardColumn, BoardTemplate } from '../model/boards';
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
  boards: Boards;
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

  constructor(
    private boardService: BoardService,
    private router: Router,
    private modalService: NgbModal,
    colorService: ColorsService,
    boards: Boards
  ) {
    this.colorService = colorService;
    this.boards = boards;
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
    this.getBoardPages();
  }

  openVerticallyCentered(content) {
    this.modalService.open(content, { centered: true });
  }

  onSubmit() {
    const request: CreateBoardRequest = this.formModelToRequest(this.formModel);
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

  formModelToRequest(formModel): CreateBoardRequest {
    const key = formModel.templateKey;
    const columnDefinitions: BoardColumn[] = this.boardTemplates.filter(boardTemplate => {
      return boardTemplate.key === key;
    })[0].boardColumns;
    return new CreateBoardRequest(formModel.boardName, formModel.description, columnDefinitions);
  }

  // TODO: Remove this when we're done
  get diagnostic() {
    return JSON.stringify(this.formModel);
  }
}
