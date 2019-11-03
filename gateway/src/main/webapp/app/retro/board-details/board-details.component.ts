import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Board, BoardColumn, InformationCard, BoardColumnVM } from '../model/boards';
import { BoardService } from '../board.service';
import { ColorsService } from '../colors.service';

import { faPlusCircle } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-board-details',
  templateUrl: './board-details.component.html',
  styleUrls: ['./board-details.component.scss']
})
export class BoardDetailsComponent implements OnInit {
  boardId: string;
  board: Board;
  error: string;
  faPlusCircle = faPlusCircle;

  columnAndCards: Map<String, BoardColumnVM> = new Map();
  boardColumnVMs: Array<BoardColumnVM> = [];
  colorService: ColorsService;

  constructor(private route: ActivatedRoute, private boardService: BoardService, colorService: ColorsService) {
    this.colorService = colorService;
  }

  /* eslint-disable */
  ngOnInit() {
    this.boardId = this.route.snapshot.paramMap.get('id');
    this.boardService.getBoardById(this.boardId).subscribe(
      board => {
        this.board = board;
        this.buildBoardColumnVMs(board.columnDefinitions, board.informationCards);
        console.log(board);
        console.log(this.columnAndCards);
      },
      error => {
        console.log(error);
        this.error = error;
      }
    );
  }

  /* eslint-disable */
  buildBoardColumnVMs(boardColumns: Array<BoardColumn>, informationCards: Array<InformationCard>) {
    boardColumns.forEach(element => {
      this.columnAndCards.set(element.columnType, BoardColumnVM.of(element));
    });

    informationCards.forEach(informationCard => {
      const boardColumnVM: BoardColumnVM = this.columnAndCards.get(informationCard.columnType);
      boardColumnVM.informationCards.push(informationCard);
    });

    this.columnAndCards.forEach((value: BoardColumnVM, key: string) => {
      this.boardColumnVMs.push(value);
    });
  }
}
