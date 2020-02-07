import { EventEmitter, Output, Component, OnInit } from '@angular/core';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { BoardService } from '../board.service';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, tap, switchMap } from 'rxjs/operators';
import { BoardSummary } from '../model/boards';
import { Router } from '@angular/router';
import { ColorsService } from '../colors.service';

@Component({
  selector: 'jhi-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.scss']
})
export class SearchbarComponent implements OnInit {
  faSearch = faSearch;
  selection = 'Name';
  searchValue: string;
  searching = false;
  searchFailed = false;

  @Output() searchEvent = new EventEmitter<any>();

  constructor(private boardService: BoardService, private colorService: ColorsService, private router: Router) {}

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

  ngOnInit() {}

  formatter = x => x;
  inputFormatter = (boardSummary: BoardSummary) => boardSummary.name;

  changeSelection(selection: string) {
    this.selection = selection;
  }

  onItemSelect(event: any) {
    this.router.navigateByUrl('retro/boards/' + event.item.id);
  }

  onKeyUp(event: any) {
    if ('Enter' === event.code) {
      this.doEmit();
    }
  }

  doEmit() {
    if (this.searchValue) {
      this.searchEvent.emit({ field: this.selection.toLowerCase(), search: this.searchValue });
    }
  }
}
