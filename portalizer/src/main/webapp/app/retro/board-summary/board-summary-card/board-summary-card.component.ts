import { Component, OnInit, Input, Injector, Output, EventEmitter } from '@angular/core';
import { BoardSummary, DeleteBoardRequest } from 'app/retro/model/boards';
import { BoardService } from 'app/retro/board.service';
import { CommunicationService } from 'app/retro/communication.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DeleteConfirmationModalComponent } from 'app/retro/delete-confirmation-modal/delete-confirmation-modal.component';
import { ColorsService } from 'app/retro/colors.service';
import { faEye, faTrash, faArchive, faPlusSquare, faClock } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-board-summary-card',
  templateUrl: './board-summary-card.component.html',
  styleUrls: ['./board-summary-card.component.scss']
})
export class BoardSummaryCardComponent implements OnInit {
  @Input() boardsummary: BoardSummary;
  @Output() delete = new EventEmitter<string>();

  faEye = faEye;
  faTrash = faTrash;
  faArchive = faArchive;
  faClock = faClock;
  faPlusSquare = faPlusSquare;
  colorService: ColorsService;
  constructor(private boardService: BoardService, private modalService: NgbModal, colorService: ColorsService) {
    this.colorService = colorService;
  }

  ngOnInit() {}

  openDeleteConfirmationModal(boardSummary: BoardSummary) {
    const ok = new CommunicationService<DeleteBoardRequest>();
    this.modalService.open(DeleteConfirmationModalComponent, {
      centered: true,

      injector: Injector.create([
        {
          provide: BoardSummary,
          useValue: boardSummary
        },
        {
          provide: CommunicationService,
          useValue: ok
        }
      ])
    });
    ok.subject.subscribe(deleteBoardRequest => {
      this.boardService.deleteBoardById(deleteBoardRequest.id).subscribe(
        () => {
          this.delete.emit(deleteBoardRequest.id);
        },
        error => {}
      );
    });
  }
}
