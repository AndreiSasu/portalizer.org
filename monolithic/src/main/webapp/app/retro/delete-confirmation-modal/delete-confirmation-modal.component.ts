import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BoardSummary, DeleteBoardRequest } from '../model/boards';
import { CommunicationService } from '../communication.service';

@Component({
  selector: 'jhi-confirmation-modal',
  templateUrl: './delete-confirmation-modal.component.html',
  styleUrls: ['./delete-confirmation-modal.component.scss']
})
/*eslint-disable*/
export class DeleteConfirmationModalComponent implements OnInit {
  constructor(public modal: NgbActiveModal, public boardSummary: BoardSummary, public ok: CommunicationService<DeleteBoardRequest>) {}

  ngOnInit() {}

  onOk() {
    console.log(this.ok);
    this.ok.subject.next(new DeleteBoardRequest(this.boardSummary.id));
  }
}
