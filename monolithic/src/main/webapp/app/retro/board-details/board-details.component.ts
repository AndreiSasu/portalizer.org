import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Board, BoardColumn, BoardColumnVM, RefreshBoardRequest } from '../model/boards';
import { InformationCard, CreateCardRequest, InformationCardVM, UpdateCardRequest } from '../model/information-card';

import { BoardService } from '../board.service';
import { ColorsService } from '../colors.service';
import { InformationCardService } from '../information-card.service';
import { faPlusCircle, faArrowsAlt, faSync, faPencilAlt, faSearch, faChevronLeft, faChevronRight } from '@fortawesome/free-solid-svg-icons';
import * as uuid from 'uuid';
import { CardStorageService } from '../card-storage.service';
import { Subscription } from 'rxjs';
import { DragulaService } from 'ng2-dragula';

@Component({
  selector: 'jhi-board-details',
  templateUrl: './board-details.component.html',
  styleUrls: ['./board-details.component.scss']
})
export class BoardDetailsComponent implements OnInit, OnDestroy {
  boardId: string;
  board: Board;
  error: string;
  faPlusCircle = faPlusCircle;
  faSync = faSync;
  faChevronLeft = faChevronLeft;
  faChevronRight = faChevronRight;
  faSearch = faSearch;
  faPencilAlt = faPencilAlt;
  faArrowsAlt = faArrowsAlt;

  editMode: boolean;
  search: string;

  columnAndCards: Map<String, BoardColumnVM> = new Map();
  boardColumnVMs: Array<BoardColumnVM> = [];
  colorService: ColorsService;
  subs = new Subscription();

  constructor(
    private route: ActivatedRoute,
    private boardService: BoardService,
    private informationCardService: InformationCardService,
    private cardStorageService: CardStorageService,
    colorService: ColorsService,
    private dragulaService: DragulaService
  ) {
    this.colorService = colorService;
  }

  /* eslint-disable */
  ngOnInit() {
    this.boardId = this.route.snapshot.paramMap.get('id');
    this.cardStorageService.initBoardStorageIfNecessary(this.boardId);
    this.refreshBoard();

    this.dragulaService.createGroup('COLUMNS', {
      direction: 'horizontal',
      moves: (el, source, handle: any) => {
        // it makes sense not to drag and drop from icons as they might be edit icons
        if (handle.className instanceof SVGAnimatedString) return false;
        return handle.className.includes('column-drag');
      }
    });

    this.subs.add(
      this.dragulaService
        .dropModel('COLUMNS')
        .subscribe(({ name, el, target, source, sibling, sourceModel, targetModel, item, sourceIndex, targetIndex }) => {
          const sourceBoardColumnVM = this.boardColumnVMs[sourceIndex];
          this.boardColumnVMs.splice(sourceIndex, 1);
          this.boardColumnVMs.splice(targetIndex, 0, sourceBoardColumnVM);
          console.log(this.boardColumnVMs);
        })
    );

    // These will get events limited to the VAMPIRES group.

    // this.subs.add(
    //   this.dragulaService.drag('CARDS').subscribe(({ name, el, source }) => {
    //     // ...
    //     console.log('DRAG: ');
    //     console.log('NAME:' + JSON.stringify(name), 'EL: ' + JSON.stringify(el), 'SOURCE: ' + JSON.stringify(source));
    //   })
    // );
    // this.subs.add(
    //   this.dragulaService.drop('CARDS').subscribe(({ name, el, target, source, sibling }) => {
    //     // ...
    //     console.log('DROP: ');
    //     console.log(name,el,target,source,sibling);
    //   })
    // );
    // some events have lots of properties, just pick the ones you need
    this.subs.add(
      this.dragulaService
        .dropModel('CARDS')
        // WHOA
        // .subscribe(({ name, el, target, source, sibling, sourceModel, targetModel, item }) => {
        .subscribe(({ name, el, target, source, sibling, sourceModel, targetModel, item, sourceIndex, targetIndex }) => {
          console.log('------- DROPMODEL: ');
          // console.log(
          //   'SOURCEMODEL: \n' + JSON.stringify(sourceModel),
          //   'TARGETMODEL: \n' + JSON.stringify(targetModel),
          //   'ITEM: \n' + JSON.stringify(item),
          //   ' SOURCEINDEX: ' + sourceIndex,
          //   ' TARGETINDEX: ' + targetIndex
          // );
          console.log(name, el, target, source, sibling, sourceIndex, targetIndex);
          console.log(target.getAttribute('columnKey'));
          const cardId = el.getAttribute('id');
          const targetBoardColumnVM = this.columnAndCards.get(target.getAttribute('columnKey'));
          const sourceBoardColumnVM = this.columnAndCards.get(source.getAttribute('columnKey'));
          const card = sourceBoardColumnVM.informationCards.filter(informationCard => informationCard.id === cardId)[0];
          console.log(card);
          sourceBoardColumnVM.informationCards = [
            ...sourceBoardColumnVM.informationCards.filter(informationCard => informationCard.id != cardId)
          ];
          if (targetBoardColumnVM.key === sourceBoardColumnVM.key) {
            sourceBoardColumnVM.informationCards.splice(targetIndex, 0, card);
            return;
          }

          targetBoardColumnVM.informationCards.splice(targetIndex, 0, card);
          targetBoardColumnVM.informationCards = [...targetBoardColumnVM.informationCards];

          console.log(targetBoardColumnVM);
          // this.updateCardColors();
        })
    );
  }

  refreshBoard() {
    this.boardService.getBoardById(this.boardId).subscribe(
      board => {
        this.board = board;
        this.buildBoardColumnVMs(board.columnDefinitions, board.informationCards);

        this.cardStorageService.getCards(this.boardId).forEach(informationCardVM => {
          const boardColumnVM: BoardColumnVM = this.columnAndCards.get(informationCardVM.columnKey);
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
      this.columnAndCards.set(element.key, BoardColumnVM.of(element));
    });

    informationCards.forEach(informationCard => {
      const boardColumnVM: BoardColumnVM = this.columnAndCards.get(informationCard.columnKey);
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
    blankCard.columnKey = boardColumnVM.key;
    blankCard.editMode = true;
    blankCard.key = uuid.v4();
    boardColumnVM.informationCards = [...boardColumnVM.informationCards, blankCard];
  }

  removeCard(informationCardVM: InformationCardVM) {
    const keyToRemove = informationCardVM.key;
    const idToRemove = informationCardVM.id;
    const boardColumnVM = this.columnAndCards.get(informationCardVM.columnKey);

    this.cardStorageService.removeCard(this.boardId, informationCardVM.key);

    if (undefined === idToRemove) {
      // remove previous unsaved card
      boardColumnVM.informationCards = boardColumnVM.informationCards.filter(function(informationCardVM) {
        return informationCardVM.key !== keyToRemove;
      });
      return;
    }
    console.log('removing card with key ' + informationCardVM.key + ' from ' + informationCardVM.columnKey);

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
    createCardRequest.columnKey = informationCardVM.columnKey;
    createCardRequest.text = informationCardVM.text;

    this.informationCardService.addCard(createCardRequest).subscribe(
      informationCard => {
        console.log(informationCard);
        const boardColumnVM = this.columnAndCards.get(informationCard.columnKey);

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
    updateCardRequest.columnKey = informationCardVM.columnKey;

    this.informationCardService.updateCard(updateCardRequest).subscribe(
      updatedCard => {
        console.log(updatedCard);
        const boardColumnVM = this.columnAndCards.get(updatedCard.columnKey);

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

  onSearch(event: string) {
    this.search = event;
  }

  onRefresh(event: RefreshBoardRequest) {
    this.refreshBoard();
  }

  ngOnDestroy() {
    // destroy all the subscriptions at once
    this.subs.unsubscribe();
  }
}
