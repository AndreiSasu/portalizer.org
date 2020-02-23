import { InformationCard, InformationCardVM } from './information-card';

export class BoardSummary {
  id: string;
  name: string;
  description: string;
  columnDefinitions: Array<BoardColumn>;
  createdAt: Date;
  totalCards: number;
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
  /**
   *
   * @param id board id
   */
  constructor(public id: string) {}
}

export class RefreshBoardRequest {
  /**
   *
   * @param id board id
   */
  constructor(public id: string) {}
}

export class SaveBoardRequest {
  /**
   *
   * @param id board id
   * @param name board name
   */
  constructor(public id: string, public name: string) {}
}

export class ColumnAddRequest {
  /**
   *
   * @param id board id
   * @param name  column name
   */
  constructor(public id: string, public name: string) {}
}

export class TextSearch {
  constructor(public fieldName: string, public search: string) {}
}

export class ClearSearch {}

export class ColumnReorderRequest {
  constructor(public id: string, public oldIndex: number, public newIndex: number) {}
}

export class InformationCardReorderRequest {
  constructor(public id: string, public oldIndex: number, public newIndex: number, public oldColumn: string, public newColumn: string) {}
}
