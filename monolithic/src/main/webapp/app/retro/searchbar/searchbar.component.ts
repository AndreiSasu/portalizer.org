import { Component, OnInit } from '@angular/core';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { WikipediaService } from './wikipediaservice';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, tap, switchMap } from 'rxjs/operators';

@Component({
  selector: 'jhi-searchbar',
  templateUrl: './searchbar.component.html',
  providers: [WikipediaService],
  styleUrls: ['./searchbar.component.scss']
})
export class SearchbarComponent implements OnInit {
  faSearch = faSearch;
  selection = 'Name';
  searchValue: string;
  searching = false;
  searchFailed = false;

  constructor(private _service: WikipediaService) {}

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap(term =>
        this._service.search(term).pipe(
          tap(() => (this.searchFailed = false)),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          })
        )
      ),
      tap(() => (this.searching = false))
    );

  ngOnInit() {}

  changeSelection(selection: string) {
    this.selection = selection;
  }

  /*eslint-disable*/
  onItemSelect(event: any) {
    console.log(event);
  }
}
