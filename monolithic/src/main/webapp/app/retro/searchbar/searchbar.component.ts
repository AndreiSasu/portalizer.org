import { Component, OnInit } from '@angular/core';
import { faSearch } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.scss']
})
export class SearchbarComponent implements OnInit {
  faSearch = faSearch;

  selection = 'Name';

  constructor() {}

  ngOnInit() {}

  changeSelection(selection: string) {
    this.selection = selection;
  }
}
