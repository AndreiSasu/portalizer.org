import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

import { SERVER_API_URL } from '../app.constants';

import { CreateCardRequest, InformationCard, UpdateCardRequest } from './model/information-card';

@Injectable({
  providedIn: 'root'
})
/* eslint-disable */
export class InformationCardService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  CARDS_URL = SERVER_API_URL + '/api/retro/information-card/';

  constructor(private http: HttpClient) {}

  addCard(createCardRequest: CreateCardRequest): Observable<InformationCard> {
    return this.http
      .post<InformationCard>(this.CARDS_URL, createCardRequest, this.httpOptions)
      .pipe(tap((newCard: InformationCard) => console.log(`added card w/ id=${newCard.id}`)));
  }
  removeCard(id: string): Observable<any> {
    return this.http.delete(this.CARDS_URL + id).pipe(tap(response => console.log(response)));
  }
  updateCard(updateCardRequest: UpdateCardRequest): Observable<InformationCard> {
    return this.http
      .put<InformationCard>(this.CARDS_URL, updateCardRequest, this.httpOptions)
      .pipe(tap((updateCard: InformationCard) => console.log(`updated card w/ id=${updateCard.id}`)));
  }
}
