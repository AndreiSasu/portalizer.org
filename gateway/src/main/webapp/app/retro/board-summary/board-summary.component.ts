import { Component, OnInit } from '@angular/core';
import { BoardService } from '../board.service';
import { BoardSummary } from '../model/boards';

export class Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
  boardSummary: BoardSummary;
}

@Component({
  selector: 'jhi-board-summary',
  templateUrl: './board-summary.component.html',
  styleUrls: ['./board-summary.component.scss']
})
export class BoardSummaryComponent implements OnInit {
  boardSummaries: BoardSummary[];
  tiles: Tile[] = [
    // {text: 'One', cols: 1, rows: 1, color: 'lightblue'},
    // {text: 'Two', cols: 1, rows: 1, color: 'lightgreen'},
    // {text: 'Three', cols: 1, rows: 1, color: 'lightpink'},
    // {text: 'Four', cols: 1, rows: 1, color: '#DDBDF1'},
    // {text: 'One', cols: 1, rows: 1, color: 'lightblue'},
    // {text: 'Two', cols: 1, rows: 1, color: 'lightgreen'},
    // {text: 'Three', cols: 1, rows: 1, color: 'lightpink'},
    // {text: 'Four', cols: 1, rows: 1, color: '#DDBDF1'},
  ];

  constructor(private boardService: BoardService) {}

  ngOnInit() {
    this.boardService.getBoardSummaries().subscribe(boardSummaries => {
      this.boardSummaries = boardSummaries;
      this.boardSummaries.forEach(boardSummarry => {
        const tile = new Tile();
        tile.boardSummary = boardSummarry;
        tile.color = 'lightblue';
        tile.cols = 1;
        tile.rows = 1;
        this.tiles.push(tile);
      });
    });
  }
}
