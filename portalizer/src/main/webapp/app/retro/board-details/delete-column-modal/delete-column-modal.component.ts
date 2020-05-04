import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CommunicationService } from 'app/retro/communication.service';
import { ColumnDeleteRequest, BoardColumnVM } from 'app/retro/model/columns';

@Component({
  selector: 'jhi-delete-column-modal',
  templateUrl: './delete-column-modal.component.html',
  styleUrls: ['./delete-column-modal.component.scss']
})
export class DeleteColumnModalComponent implements OnInit {
  constructor(
    public modal: NgbActiveModal,
    public communicationService: CommunicationService<ColumnDeleteRequest>,
    public boardColumnVM: BoardColumnVM
  ) {}

  ngOnInit() {}

  onSubmit() {
    this.communicationService.subject.next(new ColumnDeleteRequest('', this.boardColumnVM.key));
  }
}
