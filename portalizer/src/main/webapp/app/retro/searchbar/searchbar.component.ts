import { EventEmitter, Output, Component, OnInit, Input } from '@angular/core';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { BoardService } from '../board.service';
import { Observable, of, Subject } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, tap, switchMap } from 'rxjs/operators';
import { BoardSummary, TextSearch, ClearSearch } from '../model/boards';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'jhi-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.scss']
})
/* eslint-disable */
export class SearchbarComponent implements OnInit {
  faSearch = faSearch;

  searching = false;
  searchFailed = false;
  searchControl = new FormControl();

  @Input() selection = 'name';
  @Input() searchValue: string;
  @Input() inputSubject = new Subject<TextSearch>();
  @Output() searchButtonClicked = new EventEmitter<TextSearch>();
  @Output() inputCleared = new EventEmitter<ClearSearch>();

  constructor(private boardService: BoardService, private router: Router) {}

  searchBoards = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap(term =>
        this.boardService.searchLight(this.selection.toLowerCase(), term).pipe(
          tap(() => (this.searchFailed = false)),
          catchError(() => {
            this.searchFailed = true;
            return of([]);
          })
        )
      ),
      tap(() => (this.searching = false))
    );

  ngOnInit() {
    this.inputSubject.subscribe(event => {
      this.searchValue = event.search;
      this.selection = event.fieldName;
      this.searchControl.setValue(this.searchValue);
    });

    this.searchControl.setValue(this.searchValue);
  }

  formatter = x => x;
  inputFormatter = (boardSummary: BoardSummary) => boardSummary.name;

  changeSelection(selection: string) {
    this.selection = selection;
  }

  onItemSelect(event: any) {
    this.router.navigateByUrl('retro/boards/' + event.item.id);
  }

  /*eslint-disable*/
  onEvent(event: any) {
    event.preventDefault();
    console.log(event);

    if ('Enter' === event.code || ('search' === event.type && this.searchValue)) {
      this.doEmit();
    } else if ('search' === event.type) {
      console.log('is clear search');
      this.searchValue = '';
      this.inputCleared.emit(new ClearSearch());
    }
  }

  doEmit() {
    if (this.searchValue) {
      let event = new TextSearch(this.selection.toLowerCase(), this.searchValue);
      this.searchButtonClicked.emit(event);
    }
  }
}
