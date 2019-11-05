import { Component, OnInit } from '@angular/core';
import { BoardService } from '../board.service';
import { BoardSummary, Boards, CreateBoardRequest, BoardColumn } from '../model/boards';
import { faEye, faTrash, faArchive } from '@fortawesome/free-solid-svg-icons';
import { ColorsService } from '../colors.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

export class Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
  boardSummary: BoardSummary;
}

@Component({
  selector: 'jhi-board-summary',
  templateUrl: './board-summary.component.html',
  styleUrls: ['./board-summary.component.scss']
})
export class BoardSummaryComponent implements OnInit {
  boardSummaries: BoardSummary[];
  tiles: Tile[] = [];

  faEye = faEye;
  faTrash = faTrash;
  faArchive = faArchive;
  colorService: ColorsService;
  closeResult: string;
  boards: Boards;

  formModel = {
    boardName: '',
    templateKey: '',
    description: ''
  };

  constructor(private boardService: BoardService, colorService: ColorsService, private modalService: NgbModal, boards: Boards) {
    this.colorService = colorService;
    this.boards = boards;
  }

  ngOnInit() {
    this.boardService.getBoardSummaries().subscribe(boardSummaries => {
      this.boardSummaries = boardSummaries;
      this.boardSummaries.forEach(boardSummarry => {
        const tile = new Tile();
        tile.boardSummary = boardSummarry;
        tile.color = 'lightblue';
        tile.cols = 1;
        tile.rows = 1;
        this.tiles.push(tile);
      });
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
      },
      error => {
        console.log(error);
      }
    );
  }

  formModelToRequest(formModel): CreateBoardRequest {
    // MAD - SAD - GLAD: (Most simple 3 column board)
    const key = formModel.templateKey.split(':')[0];
    const columnDefinitions: BoardColumn[] = this.boards.getTemplates().filter(boardTemplate => {
      return boardTemplate.key === key;
    })[0].boardColumns;
    return new CreateBoardRequest(formModel.boardName, columnDefinitions);
  }

  // TODO: Remove this when we're done
  get diagnostic() {
    return JSON.stringify(this.formModel);
  }
}
