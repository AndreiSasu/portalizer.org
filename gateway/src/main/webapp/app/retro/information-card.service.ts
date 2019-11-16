import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { SERVER_API_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class InformationCardService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {}

  addCard() {}
  removeCard() {}
  updateCard() {}
}
