import { Component, Input, Output, EventEmitter } from '@angular/core';
import { BoardSummary } from 'app/retro/model/boards';
import { ColorsService } from 'app/retro/colors.service';
import { faEye, faTrash, faArchive, faPlusSquare, faClock } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-board-summary-card',
  templateUrl: './board-summary-card.component.html',
  styleUrls: ['./board-summary-card.component.scss']
})
export class BoardSummaryCardComponent {
  @Input() boardsummary: BoardSummary;
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
}
