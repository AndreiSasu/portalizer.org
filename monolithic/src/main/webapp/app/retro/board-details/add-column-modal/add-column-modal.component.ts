import { Component, OnInit } from '@angular/core';
import { CommunicationService } from 'app/retro/communication.service';
import { ColumnAddRequest } from 'app/retro/model/boards';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-add-column-modal',
  templateUrl: './add-column-modal.component.html',
  styleUrls: ['./add-column-modal.component.scss']
})
export class AddColumnModalComponent implements OnInit {
  formModel = {
    columnName: ''
  };

  constructor(public modal: NgbActiveModal, private communicationService: CommunicationService<ColumnAddRequest>) {}

  ngOnInit() {}

  onSubmit() {
    this.communicationService.subject.next(new ColumnAddRequest('', this.formModel.columnName));
  }
}
