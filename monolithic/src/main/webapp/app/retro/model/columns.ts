import { InformationCardVM } from './information-card';

export class BoardColumn {
  key: string;
  title: string;
  color?: string;
  priority: number;
  static of(boardColumnVM: BoardColumnVM) {
    const boardColumn = new BoardColumn();
    boardColumn.key = boardColumnVM.key;
    boardColumn.title = boardColumnVM.title;
    boardColumn.priority = boardColumnVM.priority;
    boardColumn.color = boardColumnVM.color;
    return boardColumn;
  }
}

export class BoardColumnVM extends BoardColumn {
  informationCards: Array<InformationCardVM>;

  static of(boardColumn: BoardColumn): BoardColumnVM {
    const boardColumnVM = new BoardColumnVM();
    boardColumnVM.color = boardColumn.color;
    boardColumnVM.key = boardColumn.key;
    boardColumnVM.title = boardColumn.title;
    boardColumnVM.priority = boardColumn.priority;
    boardColumnVM.informationCards = [];
    return boardColumnVM;
  }
}

export class ColumnAddRequest {
  /**
   *
   * @param id board id
   * @param name  column name
   */
  constructor(public id: string, public name: string) {}
}

export class ColumnsUpdateRequest {
  constructor(public id: string, public oldIndex: number, public newIndex: number) {}
}
