import { InformationCard } from './information-card';
import { BoardColumn } from './columns';
import { Account } from '../../core/user/account.model';
export class BoardSummary {
  id: string;
  name: string;
  description: string;
  columnDefinitions: Array<BoardColumn>;
  createdAt: Date;
  totalCards: number;
  owner: Account;
}

export class Board extends BoardSummary {
  informationCards: Array<InformationCard>;
}

export class BoardTemplate {
  constructor(public title: string, public boardColumns: Array<BoardColumn>, public description?: string) {}
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
  static default() {
    return new TextSearch('name', '');
  }
}

export class ClearSearch {}

export class ColumnReorderRequest {
  constructor(public id: string, public oldIndex: number, public newIndex: number) {}
}

export class BoardsClearAllFiltersEvent {}

export enum SortDirection {
  DESC = 'DESC',
  ASC = 'ASC'
}

export enum BoardsView {
  LIST = 'LIST',
  GRID = 'GRID'
}

export class BoardsFilterEvent {
  constructor(
    public view: BoardsView,
    public sortByFieldName: string,
    public sortDirection: SortDirection,
    public itemsPerPage: number,
    public textBoxState: TextSearch | ClearSearch
  ) {}

  static default() {
    return new BoardsFilterEvent(BoardsView.GRID, 'createdAt', SortDirection.DESC, 25, new ClearSearch());
  }
}

export class InformationCardReorderRequest {
  constructor(public id: string, public oldIndex: number, public newIndex: number, public oldColumn: string, public newColumn: string) {}
}

export function filterToQueryString(page: number, view: BoardsView, filter: BoardsFilterEvent): string {
  let url = `?view=${view}&sort=${filter.sortByFieldName},${filter.sortDirection}&size=${filter.itemsPerPage}&page=${page}`;
  if (filter.textBoxState instanceof TextSearch) {
    url += `&searchField=${filter.textBoxState.fieldName}&searchPhrase=${filter.textBoxState.search}`;
  }
  return url;
}

export function filterToLocation(basePath: string, page: number, view: BoardsView, filter: BoardsFilterEvent): string {
  return `${basePath}${filterToQueryString(page, view, filter)}`;
}
