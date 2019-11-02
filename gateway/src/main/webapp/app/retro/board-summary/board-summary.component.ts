import { Component, OnInit } from '@angular/core';
import { BoardService } from '../board.service';
import { BoardSummary } from '../model/boards';
@Component({
  selector: 'jhi-board-summary',
  templateUrl: './board-summary.component.html',
  styleUrls: ['./board-summary.component.scss']
})
export class BoardSummaryComponent implements OnInit {
  boardSummaries: BoardSummary[];
  constructor(private boardService: BoardService) {}

  ngOnInit() {
    this.boardService.getBoardSummaries().subscribe(boardSummaries => (this.boardSummaries = boardSummaries));
  }
}
