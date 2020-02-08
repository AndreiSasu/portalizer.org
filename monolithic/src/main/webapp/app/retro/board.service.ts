import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { BoardSummary, Board, CreateBoardRequest, BoardTemplate } from './model/boards';
import { PaginationPage } from './model/pagination';
import { SERVER_API_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
/* eslint-disable */
export class BoardService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  BOARDS_URL = SERVER_API_URL + '/api/retro/boards/';
  BOARDS_PAGING_URL = SERVER_API_URL + '/api/retro/boards?page=';
  BOARD_TEMPLATES_URL = SERVER_API_URL + '/api/retro/boardtemplates/';
  BOARDS_SEARCH_LIGHT_URL = SERVER_API_URL + '/api/retro/boards/search/light';
  BOARDS_SEARCH_HEAVY_URL = SERVER_API_URL + '/api/retro/boards/search/heavy';

  constructor(private http: HttpClient) {}

  searchLight(field: string, search: string): Observable<Array<BoardSummary>> {
    return this.http.get<Array<BoardSummary>>(this.BOARDS_SEARCH_LIGHT_URL + '?fieldName=' + field + '&search=' + search);
  }

  searchHeavy(field: string, search: string, page?: number): Observable<PaginationPage<BoardSummary>> {
    let url = this.BOARDS_SEARCH_HEAVY_URL + '?fieldName=' + field + '&search=' + search;
    if (page !== undefined) {
      url += '&page=' + page;
    }
    console.log(url);
    return this.http.get<PaginationPage<BoardSummary>>(url);
  }

  getBoardsPage(page: number): Observable<PaginationPage<BoardSummary>> {
    return this.http.get<PaginationPage<BoardSummary>>(this.BOARDS_PAGING_URL + page);
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
