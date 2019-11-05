export class BoardSummary {
  id: string;
  name: string;
  columnDefinitions: Array<BoardColumn>;
  createdAt: Date;
}

export class BoardColumn {
  columnType: string;
  title: string;
  color: string;
}

export class InformationCard {
  id: string;
  boardId: string;
  columnType: string;
  createdAt: Date;
  text: string;
  updatedAt: Date;
}

export class Board extends BoardSummary {
  informationCards: Array<InformationCard>;
}

export class BoardColumnVM extends BoardColumn {
  informationCards: Array<InformationCard>;

  static of(boardColumn: BoardColumn): BoardColumnVM {
    const boardColumnVM = new BoardColumnVM();
    boardColumnVM.color = boardColumn.color;
    boardColumnVM.columnType = boardColumn.columnType;
    boardColumnVM.title = boardColumn.title;
    boardColumnVM.informationCards = [];
    return boardColumnVM;
  }
}
