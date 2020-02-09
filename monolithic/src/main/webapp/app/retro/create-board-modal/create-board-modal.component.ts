import { EventEmitter, Output, Input, Component, OnInit } from '@angular/core';
import { CreateBoardRequest, BoardColumn, BoardTemplate } from '../model/boards';

@Component({
  selector: 'jhi-create-board-modal',
  templateUrl: './create-board-modal.component.html',
  styleUrls: ['./create-board-modal.component.scss']
})
export class CreateBoardModalComponent implements OnInit {
  formModel = {
    boardName: '',
    templateKey: '',
    description: ''
  };

  @Input() modal: any;
  @Input() boardTemplates: Array<BoardTemplate>;
  @Output() submit = new EventEmitter<CreateBoardRequest>();

  constructor() {}

  ngOnInit() {}

  onSubmit() {
    this.submit.emit(this.formModelToRequest(this.formModel));
  }

  formModelToRequest(formModel: any): CreateBoardRequest {
    const key = formModel.templateKey;
    const columnDefinitions: BoardColumn[] = this.boardTemplates.filter(boardTemplate => {
      return boardTemplate.key === key;
    })[0].boardColumns;
    return new CreateBoardRequest(formModel.boardName, formModel.description, columnDefinitions);
  }
}
