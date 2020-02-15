import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Board, BoardColumn, BoardColumnVM } from '../model/boards';
import { InformationCard, CreateCardRequest, InformationCardVM, UpdateCardRequest } from '../model/information-card';

import { BoardService } from '../board.service';
import { ColorsService } from '../colors.service';
import { InformationCardService } from '../information-card.service';
import { faPlusCircle, faSync, faPencilAlt, faSearch, faChevronLeft, faChevronRight } from '@fortawesome/free-solid-svg-icons';
import * as uuid from 'uuid';
import { CardStorageService } from '../card-storage.service';

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
  faSync = faSync;
  faChevronLeft = faChevronLeft;
  faChevronRight = faChevronRight;
  faSearch = faSearch;
  faPencilAlt = faPencilAlt;

  editMode: boolean;

  columnAndCards: Map<String, BoardColumnVM> = new Map();
  boardColumnVMs: Array<BoardColumnVM> = [];
  colorService: ColorsService;

  constructor(
    private route: ActivatedRoute,
    private boardService: BoardService,
    private informationCardService: InformationCardService,
    private cardStorageService: CardStorageService,
    colorService: ColorsService
  ) {
    this.colorService = colorService;
  }

  /* eslint-disable */
  ngOnInit() {
    this.boardId = this.route.snapshot.paramMap.get('id');
    this.cardStorageService.initBoardStorageIfNecessary(this.boardId);
    this.refreshBoard();
  }

  refreshBoard() {
    this.boardService.getBoardById(this.boardId).subscribe(
      board => {
        this.board = board;
        this.buildBoardColumnVMs(board.columnDefinitions, board.informationCards);

        this.cardStorageService.getCards(this.boardId).forEach(informationCardVM => {
          const boardColumnVM: BoardColumnVM = this.columnAndCards.get(informationCardVM.columnType);
          boardColumnVM.informationCards.push(informationCardVM);
        });

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
    this.boardColumnVMs = [...[]];
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
    boardColumnVM.informationCards = [...boardColumnVM.informationCards, blankCard];
  }

  removeCard(informationCardVM: InformationCardVM) {
    const keyToRemove = informationCardVM.key;
    const idToRemove = informationCardVM.id;
    const boardColumnVM = this.columnAndCards.get(informationCardVM.columnType);

    this.cardStorageService.removeCard(this.boardId, informationCardVM.key);

    if (undefined === idToRemove) {
      // remove previous unsaved card
      boardColumnVM.informationCards = boardColumnVM.informationCards.filter(function(informationCardVM) {
        return informationCardVM.key !== keyToRemove;
      });
      return;
    }
    console.log('removing card with key ' + informationCardVM.key + ' from ' + informationCardVM.columnType);

    this.informationCardService.removeCard(idToRemove).subscribe(
      response => {
        // remove previous unsaved card
        boardColumnVM.informationCards = boardColumnVM.informationCards.filter(function(informationCardVM) {
          return informationCardVM.key !== keyToRemove;
        });
      },
      error => {
        this.error = error;
      }
    );
  }

  onSaveCard(informationCardVM: InformationCardVM) {
    console.log(informationCardVM);
    //card was not saved before
    if (undefined === informationCardVM.id) {
      this.createCard(informationCardVM);
    } else {
      console.log('Card was saved before');
      this.updateCard(informationCardVM);
    }
  }

  createCard(informationCardVM: InformationCardVM) {
    const keyToRemove = informationCardVM.key;
    const createCardRequest = new CreateCardRequest();
    createCardRequest.boardId = this.board.id;
    createCardRequest.columnType = informationCardVM.columnType;
    createCardRequest.text = informationCardVM.text;

    this.informationCardService.addCard(createCardRequest).subscribe(
      informationCard => {
        console.log(informationCard);
        const boardColumnVM = this.columnAndCards.get(informationCard.columnType);

        // remove previous unsaved card
        boardColumnVM.informationCards = boardColumnVM.informationCards.filter(function(informationCardVM) {
          return informationCardVM.key !== keyToRemove;
        });

        boardColumnVM.informationCards.push(InformationCardVM.of(informationCard));

        this.cardStorageService.removeCard(this.boardId, keyToRemove);
      },
      error => {
        console.log(error);
        this.error = error;
      }
    );
  }

  updateCard(informationCardVM: InformationCardVM) {
    const keyToRemove = informationCardVM.key;
    const updateCardRequest = new UpdateCardRequest();
    updateCardRequest.id = informationCardVM.id;
    updateCardRequest.boardId = informationCardVM.boardId;
    updateCardRequest.text = informationCardVM.text;
    updateCardRequest.columnType = informationCardVM.columnType;

    this.informationCardService.updateCard(updateCardRequest).subscribe(
      updatedCard => {
        console.log(updatedCard);
        const boardColumnVM = this.columnAndCards.get(updatedCard.columnType);

        // remove previous unsaved card
        boardColumnVM.informationCards = boardColumnVM.informationCards.filter(function(informationCardVM) {
          return informationCardVM.key !== keyToRemove;
        });

        boardColumnVM.informationCards.push(InformationCardVM.of(updatedCard));

        this.cardStorageService.removeCard(this.boardId, keyToRemove);
      },
      error => {
        console.log(error);
        this.error = error;
      }
    );
  }
}
