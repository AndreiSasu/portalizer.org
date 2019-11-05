import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { BoardSummary, Board } from './model/boards';

import { SERVER_API_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class BoardService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private boardSummaryUrl = SERVER_API_URL + '/services/retro/api/boards';
  private boardByIdUrl = SERVER_API_URL + '/services/retro/api/boards/';

  constructor(private http: HttpClient) {}

  /** GET heroes from the server */
  /* eslint-disable */
  getBoardSummaries(): Observable<BoardSummary[]> {
    return this.http.get<BoardSummary[]>(this.boardSummaryUrl).pipe(
      tap(_ => console.log(`fetched boardsummaries ${this.boardSummaryUrl}`)),
      catchError(this.handleError<BoardSummary[]>('getBoardSummaries', []))
    );
  }

  getBoardById(id: string): Observable<Board> {
    return this.http.get<Board>(this.boardByIdUrl + id).pipe(
      tap(_ => console.log(`fetched full board ${this.boardByIdUrl}${id}`)),
      catchError(this.handleError<Board>(`getBoardById ${id}`))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  /* eslint-disable */
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
