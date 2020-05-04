import { Injectable } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { CARDS_PREFIX_LS } from './constants';
import { InformationCardVM } from './model/information-card';
@Injectable({
  providedIn: 'root'
})
/*eslint-disable*/
export class CardStorageService {
  private storageName: string;

  constructor(private localStorageService: LocalStorageService) {
    this.storageName = CARDS_PREFIX_LS;
  }

  initBoardStorageIfNecessary(boardId: string) {
    const boardStorage = this.localStorageService.retrieve(CARDS_PREFIX_LS + boardId);

    if (null === boardStorage) {
      this.localStorageService.store(CARDS_PREFIX_LS + boardId, new Array<InformationCardVM>());
    }
  }

  addCard(boardId: string, informationCard: InformationCardVM) {
    let cardStorage: Array<InformationCardVM> = this.localStorageService.retrieve(CARDS_PREFIX_LS + boardId);
    console.log(cardStorage);
    const currentKey = informationCard.key;
    cardStorage = cardStorage.filter(function(card) {
      return card.key !== currentKey;
    });
    cardStorage.push(informationCard);
    this.localStorageService.store(CARDS_PREFIX_LS + boardId, cardStorage);
  }

  getCards(boardId: string): Array<InformationCardVM> {
    return this.localStorageService.retrieve(CARDS_PREFIX_LS + boardId);
  }

  removeCard(boardId: string, cardKey: string) {
    let cardStorage: Array<InformationCardVM> = this.localStorageService.retrieve(CARDS_PREFIX_LS + boardId);
    cardStorage = cardStorage.filter(function(informationCard) {
      return informationCard.key !== cardKey;
    });
    this.localStorageService.store(CARDS_PREFIX_LS + boardId, cardStorage);
  }

  clearCards(boardId: string) {
    localStorage.removeItem(this.storageName + boardId);
  }
}
