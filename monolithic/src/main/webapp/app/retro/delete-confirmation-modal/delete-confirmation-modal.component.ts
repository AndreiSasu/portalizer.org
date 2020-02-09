import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BoardSummary, DeleteBoardRequest } from '../model/boards';
import { Subject } from 'rxjs';

@Component({
  selector: 'jhi-confirmation-modal',
  templateUrl: './delete-confirmation-modal.component.html',
  styleUrls: ['./delete-confirmation-modal.component.scss']
})
export class DeleteConfirmationModalComponent implements OnInit {
  constructor(public modal: NgbActiveModal, public boardSummary: BoardSummary, public ok: Subject<DeleteBoardRequest>) {}

  ngOnInit() {}

  onOk() {
    this.ok.next(new DeleteBoardRequest(this.boardSummary.id));
  }
}
