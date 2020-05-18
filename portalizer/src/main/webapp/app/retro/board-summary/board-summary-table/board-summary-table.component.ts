import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { faEye, faTrash, faArchive, faPlusSquare, faClock } from '@fortawesome/free-solid-svg-icons';
import { ColorsService } from 'app/retro/colors.service';
import { BoardSummary } from 'app/retro/model/boards';

@Component({
  selector: 'jhi-board-summary-table',
  templateUrl: './board-summary-table.component.html',
  styleUrls: ['./board-summary-table.component.scss']
})
export class BoardSummaryTableComponent implements OnInit {
  @Input() boardSummaries: BoardSummary[];
  @Output() delete = new EventEmitter<BoardSummary>();

  faEye = faEye;
  faTrash = faTrash;
  faArchive = faArchive;
  faClock = faClock;
  faPlusSquare = faPlusSquare;
  colorService: ColorsService;
  constructor(colorService: ColorsService) {
    this.colorService = colorService;
  }

  ngOnInit() {}
}
