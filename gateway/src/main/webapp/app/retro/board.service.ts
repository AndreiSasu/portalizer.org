import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of, from } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { BoardSummary } from './model/boards';

import { SERVER_API_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class BoardService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  private boardSummaryUrl = SERVER_API_URL + '/services/retro/api/boards';

  constructor(private http: HttpClient) {}

  /** GET heroes from the server */
  getBoardSummaries(): Observable<BoardSummary[]> {
    return this.http.get<BoardSummary[]>(this.boardSummaryUrl).pipe(
      tap(_ => this.log(`fetched heroes ${this.boardSummaryUrl}`)),
      catchError(this.handleError<BoardSummary[]>('getBoardSummaries', []))
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
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    console.log(`PROD BoardService: ${message}`);
  }
}
