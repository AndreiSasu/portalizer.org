import { InformationCard } from './information-card';
import { BoardColumn } from './columns';

export class BoardSummary {
  id: string;
  name: string;
  description: string;
  columnDefinitions: Array<BoardColumn>;
  createdAt: Date;
  totalCards: number;
}

export class Board extends BoardSummary {
  informationCards: Array<InformationCard>;
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

export class TextSearch {
  constructor(public fieldName: string, public search: string) {}
}

export class ClearSearch {}
