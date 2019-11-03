import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Board } from '../model/boards';
import { BoardService } from '../board.service';

@Component({
  selector: 'jhi-board-details',
  templateUrl: './board-details.component.html',
  styleUrls: ['./board-details.component.scss']
})
export class BoardDetailsComponent implements OnInit {
  boardId: string;
  board: Board;
  error: string;
  constructor(private route: ActivatedRoute, private boardService: BoardService) {}

  /* eslint-disable */
  ngOnInit() {
    this.boardId = this.route.snapshot.paramMap.get('id');
    this.boardService.getBoardById(this.boardId).subscribe(
      board => {
        this.board = board;
        console.log(board);
      },
      error => {
        console.log(error);
        this.error = error;
      }
    );
  }
}
