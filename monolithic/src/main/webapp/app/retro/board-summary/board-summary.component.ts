import { Component, OnInit } from '@angular/core';
import { BoardService } from '../board.service';
import { BoardSummary, Boards, CreateBoardRequest, BoardColumn, BoardTemplate } from '../model/boards';
import { faEye, faTrash, faArchive, faPlusSquare } from '@fortawesome/free-solid-svg-icons';
import { ColorsService } from '../colors.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-board-summary',
  templateUrl: './board-summary.component.html',
  styleUrls: ['./board-summary.component.scss']
})
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
    this.boardService.getBoardSummaries().subscribe(boardSummaries => {
      this.boardSummaries = boardSummaries;
    });

    this.boardService.getBoardTemplates().subscribe(boardTemplates => {
      this.boardTemplates = [...boardTemplates];
    });
  }

  openVerticallyCentered(content) {
    this.modalService.open(content, { centered: true });
  }

  /* eslint-disable */
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
