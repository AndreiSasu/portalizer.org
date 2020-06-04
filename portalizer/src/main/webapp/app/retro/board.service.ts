import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { BoardSummary, Board, CreateBoardRequest, BoardTemplate, SaveBoardRequest, BoardsFilterEvent, TextSearch } from './model/boards';
import { PaginationPage } from './model/pagination';
import { SERVER_API_URL } from '../app.constants';
import { ColumnsUpdateRequest, ColumnAddRequest, BoardColumn, ColumnDeleteRequest } from './model/columns';

@Injectable({
  providedIn: 'root'
})
/* eslint-disable */
export class BoardService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  BOARDS_URL = SERVER_API_URL + '/api/retro/boards/';
  BOARDS_PAGING_URL = SERVER_API_URL + '/api/retro/boards';
  BOARD_TEMPLATES_URL = SERVER_API_URL + '/api/retro/boardtemplates/';
  BOARDS_SEARCH_LIGHT_URL = SERVER_API_URL + '/api/retro/boards/search/light';

  constructor(private http: HttpClient) {}

  searchLight(field: string, search: string): Observable<Array<BoardSummary>> {
    return this.http.get<Array<BoardSummary>>(this.BOARDS_SEARCH_LIGHT_URL + '?fieldName=' + field + '&search=' + search);
  }

  deleteColumn(columnDeleteRequest: ColumnDeleteRequest): Observable<any> {
    return this.http.delete<any>(`${this.BOARDS_URL}${columnDeleteRequest.boardId}/columns/${columnDeleteRequest.columnId}`).pipe(
      tap(_ => console.log(`deleted column  ${columnDeleteRequest.boardId}/columns/${columnDeleteRequest.columnId}`)),
      catchError(this.handleError<any>(`deleteColumn ${columnDeleteRequest.columnId}`))
    );
  }

  addColumn(boardId: string, columnAddRequest: ColumnAddRequest): Observable<BoardColumn> {
    return this.http.post<BoardColumn>(this.BOARDS_URL + boardId + '/columns', columnAddRequest, this.httpOptions).pipe(
      tap((newColumn: BoardColumn) => console.log(`added column w/ key=${newColumn.key}`)),
      catchError(this.handleError<BoardColumn>('addColumn'))
    );
  }

  reorderColumns(boardId: string, columnsUpdateRequest: ColumnsUpdateRequest): Observable<any> {
    return this.http.put<any>(this.BOARDS_URL + boardId + '/columns', columnsUpdateRequest, this.httpOptions).pipe(
      tap(_ => console.log(`updated columns ${this.BOARDS_URL}${boardId}`)),
      catchError(this.handleError<any>(`updateColumnOrdering ${boardId}`))
    );
  }

  getBoardsPage(page: number, savedFilter: BoardsFilterEvent): Observable<PaginationPage<BoardSummary>> {
    let url = `${this.BOARDS_PAGING_URL}?sort=${savedFilter.sortByFieldName},${savedFilter.sortDirection}&page=${page}&size=${savedFilter.itemsPerPage}`;
    if (savedFilter.textBoxState instanceof TextSearch) {
      let asTextSearch = savedFilter.textBoxState as TextSearch;
      if (asTextSearch.fieldName.length > 0 && asTextSearch.search.length > 0) {
        url += `&searchField=${asTextSearch.fieldName}&searchPhrase=${asTextSearch.search}`;
      }
    }
    return this.http.get<PaginationPage<BoardSummary>>(url);
  }

  getBoardTemplates(): Observable<Array<BoardTemplate>> {
    return this.http.get<Array<BoardTemplate>>(this.BOARD_TEMPLATES_URL).pipe(
      tap(_ => console.log(`fetched boardtemplates ${this.BOARD_TEMPLATES_URL}`)),
      catchError(this.handleError<Array<BoardTemplate>>('getBoardTemplates', []))
    );
  }

  getBoardById(id: string): Observable<Board> {
    return this.http.get<Board>(this.BOARDS_URL + id).pipe(
      tap(_ => console.log(`fetched full board ${this.BOARDS_URL}${id}`)),
      catchError(this.handleError<Board>(`getBoardById ${id}`))
    );
  }

  deleteBoardById(id: string): Observable<any> {
    return this.http.delete<any>(this.BOARDS_URL + id).pipe(
      tap(_ => console.log(`deleted full board ${this.BOARDS_URL}${id}`)),
      catchError(this.handleError<any>(`deleteBoardById ${id}`))
    );
  }

  saveBoard(id: string, saveBoardRequest: SaveBoardRequest): Observable<any> {
    return this.http.put<any>(this.BOARDS_URL + id, saveBoardRequest, this.httpOptions).pipe(
      tap(_ => console.log(`saved board ${this.BOARDS_URL}${id}`)),
      catchError(this.handleError<any>(`saveBoard ${id}`))
    );
  }

  /** POST: add a new hero to the server */
  createBoard(createBoardRequest: CreateBoardRequest): Observable<Board> {
    return this.http.post<Board>(this.BOARDS_URL, createBoardRequest, this.httpOptions).pipe(
      tap((newBoard: Board) => console.log(`added board w/ id=${newBoard.id}`)),
      catchError(this.handleError<Board>('createBoard'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      if (result === undefined) {
        return of(error);
      }
      return of(result as T);
    };
  }
}
