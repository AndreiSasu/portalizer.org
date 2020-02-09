import { InformationCard, InformationCardVM } from './information-card';

export class BoardSummary {
  id: string;
  name: string;
  description: string;
  columnDefinitions: Array<BoardColumn>;
  createdAt: Date;
}

export class BoardColumn {
  columnType: string;
  title: string;
  color?: string;
}

export class Board extends BoardSummary {
  informationCards: Array<InformationCard>;
}

export class BoardColumnVM extends BoardColumn {
  informationCards: Array<InformationCardVM>;

  static of(boardColumn: BoardColumn): BoardColumnVM {
    const boardColumnVM = new BoardColumnVM();
    boardColumnVM.color = boardColumn.color;
    boardColumnVM.columnType = boardColumn.columnType;
    boardColumnVM.title = boardColumn.title;
    boardColumnVM.informationCards = [];
    return boardColumnVM;
  }
}

export class BoardTemplate {
  constructor(public key: string, public boardColumns: Array<BoardColumn>, public description?: string) {}
}

export class CreateBoardRequest {
  constructor(public name: string, public description: string, public columnDefinitions: Array<BoardColumn>) {}
}

export class DeleteBoardRequest {
  constructor(public id: string) {}
}

export class TextSearch {
  constructor(public fieldName: string, public search: string) {}
}

export class ClearSearch {}
