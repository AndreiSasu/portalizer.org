import { Component, OnInit, Input, Injector, Output, EventEmitter } from '@angular/core';
import { faEye, faTrash, faArchive, faPlusSquare, faClock } from '@fortawesome/free-solid-svg-icons';
import { BoardService } from 'app/retro/board.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CommunicationService } from 'app/retro/communication.service';
import { ColorsService } from 'app/retro/colors.service';
import { BoardSummary, DeleteBoardRequest, BoardsView } from 'app/retro/model/boards';
import { DeleteConfirmationModalComponent } from 'app/retro/delete-confirmation-modal/delete-confirmation-modal.component';
import { CreateBoardRequest } from '../../model/boards';
import { CreateBoardModalComponent } from 'app/retro/create-board-modal/create-board-modal.component';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-board-display-container',
  templateUrl: './board-display-container.component.html',
  styleUrls: ['./board-display-container.component.scss']
})
export class BoardDisplayContainerComponent implements OnInit {
  @Input() view: BoardsView = BoardsView.GRID;
  @Input() boardSummaries: BoardSummary[];
  @Output() delete = new EventEmitter<string>();

  faEye = faEye;
  faTrash = faTrash;
  faArchive = faArchive;
  faClock = faClock;
  faPlusSquare = faPlusSquare;
  colorService: ColorsService;
  constructor(private boardService: BoardService, private router: Router, private modalService: NgbModal, colorService: ColorsService) {
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

  openCreateBoardModal() {
    const submit = new CommunicationService<CreateBoardRequest>();
    this.modalService.open(CreateBoardModalComponent, {
      centered: true,

      injector: Injector.create([
        {
          provide: BoardService,
          useValue: this.boardService
        },
        {
          provide: CommunicationService,
          useValue: submit
        }
      ])
    });
    submit.subject.subscribe(createBoardRequest => {
      this.onSubmit(createBoardRequest);
    });
  }

  onSubmit(request: CreateBoardRequest) {
    this.boardService.createBoard(request).subscribe(
      board => {
        this.router.navigateByUrl('retro/boards/' + board.id);
      },
      error => {}
    );
  }
}
