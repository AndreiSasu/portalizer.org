import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { BoardSummary, Board, CreateBoardRequest } from './model/boards';

import { SERVER_API_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class BoardService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  BOARDS_URL = SERVER_API_URL + '/services/retro/api/boards/';

  constructor(private http: HttpClient) {}

  /** GET heroes from the server */
  /* eslint-disable */
  getBoardSummaries(): Observable<BoardSummary[]> {
    return this.http.get<BoardSummary[]>(this.BOARDS_URL).pipe(
      tap(_ => console.log(`fetched boardsummaries ${this.BOARDS_URL}`)),
      catchError(this.handleError<BoardSummary[]>('getBoardSummaries', []))
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
