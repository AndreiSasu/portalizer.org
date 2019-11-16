import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Board, BoardColumn, BoardColumnVM } from '../model/boards';
import { InformationCard, CreateCardRequest, InformationCardVM } from '../model/information-card';

import { BoardService } from '../board.service';
import { ColorsService } from '../colors.service';
import { InformationCardService } from '../information-card.service';
import { faPlusCircle } from '@fortawesome/free-solid-svg-icons';
import * as uuid from 'uuid';

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

  editMode: boolean;

  columnAndCards: Map<String, BoardColumnVM> = new Map();
  boardColumnVMs: Array<BoardColumnVM> = [];
  colorService: ColorsService;

  constructor(
    private route: ActivatedRoute,
    private boardService: BoardService,
    private informationCardService: InformationCardService,
    colorService: ColorsService
  ) {
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
      boardColumnVM.informationCards.push(InformationCardVM.of(informationCard));
    });

    this.columnAndCards.forEach((value: BoardColumnVM, key: string) => {
      this.boardColumnVMs.push(value);
    });
  }

  addBlankCard(boardColumnVM: BoardColumnVM) {
    console.log('Adding blank card to ' + JSON.stringify(boardColumnVM));
    const blankCard = new InformationCardVM();
    blankCard.boardId = this.board.id;
    blankCard.columnType = boardColumnVM.columnType;
    blankCard.editMode = true;
    blankCard.key = uuid.v4();
    boardColumnVM.informationCards.unshift(blankCard);
  }

  removeCard(boardColumnVM: BoardColumnVM, index: number) {
    console.log('removing card with index ' + index + ' from ' + JSON.stringify(boardColumnVM));
    boardColumnVM.informationCards.splice(index, 1);
  }

  onSaveCard(informationCardVM: InformationCardVM) {
    console.log(informationCardVM);
    const keyToRemove = informationCardVM.key;
    //card was not saved before
    if (null == informationCardVM.id) {
      const createCardRequest = new CreateCardRequest();
      createCardRequest.boardId = this.board.id;
      createCardRequest.columnType = informationCardVM.columnType;
      createCardRequest.text = informationCardVM.text;

      this.informationCardService.addCard(createCardRequest).subscribe(
        informationCard => {
          console.log(informationCard);
          const boardColumnVM = this.columnAndCards.get(informationCard.columnType);
          boardColumnVM.informationCards.push(InformationCardVM.of(informationCard));
        },
        error => {
          console.log(error);
          this.error = error;
        }
      );
    } else {
      console.log('Card was saved before');
    }
  }

  createCard(boardColumnVM: BoardColumnVM, index: number) {
    const createCardRequest = new CreateCardRequest();
    createCardRequest.boardId = this.board.id;
    createCardRequest.columnType = boardColumnVM.columnType;
    createCardRequest.text = '';

    this.informationCardService.addCard(createCardRequest).subscribe(
      informationCard => {
        console.log(informationCard);
        boardColumnVM.informationCards.push(InformationCardVM.of(informationCard));
      },
      error => {
        console.log(error);
        this.error = error;
      }
    );
  }
}
