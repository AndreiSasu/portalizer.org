import { Injectable } from '@angular/core';

export class BoardSummary {
  id: string;
  name: string;
  columnDefinitions: Array<BoardColumn>;
  createdAt: Date;
}

export class BoardColumn {
  columnType: string;
  title: string;
  color?: string;
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

export class BoardTemplate {
  constructor(public key: string, public boardColumns: Array<BoardColumn>, public description?: string) {}
}

export class CreateBoardRequest {
  constructor(public name: string, public columnDefinitions: Array<BoardColumn>) {}
}

// todo: get this from backend
@Injectable({
  providedIn: 'root'
})
export class Boards {
  boardTemplates: Array<BoardTemplate> = [];

  constructor() {
    this.boardTemplates.push(
      new BoardTemplate(
        'Mad - Sad - Glad',
        [
          {
            columnType: 'MAD',
            title: 'What makes me mad'
          },
          {
            columnType: 'SAD',
            title: 'What makes me sad'
          },
          {
            columnType: 'GLAD',
            title: 'What makes me glad'
          }
        ],
        '(Most simple 3 column board)'
      ),
      new BoardTemplate(
        'Went Well - To Improve - Action Items',
        [
          {
            columnType: 'Went Well',
            title: 'What went well'
          },
          {
            columnType: 'To Improve',
            title: 'What to improve'
          },
          {
            columnType: 'Action Items',
            title: 'What actions to take'
          }
        ],
        ''
      )
    );
  }
  getTemplates(): Array<BoardTemplate> {
    return this.boardTemplates;
  }
}
