import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { SERVER_API_URL } from '../app.constants';
import { Counter } from './model';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
/* eslint-disable */
export class InsightsService {
  COUNTERS_URL = SERVER_API_URL + '/api/insights/counters';
  constructor(private httpClient: HttpClient) {}

  getCounters(): Promise<Array<Counter>> {
    return this.httpClient
      .get<Array<Counter>>(this.COUNTERS_URL)
      .pipe(tap(counter => console.log('Counters: ', counter)))
      .toPromise();
  }
}
