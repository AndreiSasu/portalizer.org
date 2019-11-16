import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { SERVER_API_URL } from '../app.constants';

import { CreateCardRequest, InformationCard } from './model/information-card';

@Injectable({
  providedIn: 'root'
})
/* eslint-disable */
export class InformationCardService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  CARDS_URL = SERVER_API_URL + '/services/retro/api/information-card/';

  constructor(private http: HttpClient) {}

  addCard(createCardRequest: CreateCardRequest): Observable<InformationCard> {
    return this.http
      .post<InformationCard>(this.CARDS_URL, createCardRequest, this.httpOptions)
      .pipe(tap((newCard: InformationCard) => console.log(`added card w/ id=${newCard.id}`)));
  }
  removeCard() {}
  updateCard() {}
}
