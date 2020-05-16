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

export class ColumnReorderRequest {
  constructor(public id: string, public oldIndex: number, public newIndex: number) {}
}

export class BoardsCardView {}

export class BoardsListView {}

export class BoardsClearAllFiltersEvent {}

export enum SortDirection {
  DESC = 'DESC',
  ASC = 'ASC'
}

export class BoardsFilterEvent {
  constructor(
    public sortBy: string,
    public sortDirection: SortDirection,
    public itemsPerPage: number,
    public textBoxState: TextSearch | ClearSearch
  ) {}
}

export class InformationCardReorderRequest {
  constructor(public id: string, public oldIndex: number, public newIndex: number, public oldColumn: string, public newColumn: string) {}
}

export const defaultBoardsFilter = new BoardsFilterEvent('createdAt', SortDirection.DESC, 25, new ClearSearch());
