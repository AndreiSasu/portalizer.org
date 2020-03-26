import { Component, OnInit } from '@angular/core';
import { CreateBoardRequest, BoardTemplate } from '../model/boards';
import { BoardColumn } from '../model/columns';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BoardService } from '../board.service';
import { CommunicationService } from '../communication.service';

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

  boardTemplates: Array<BoardTemplate>;
  currentDescription: string;

  constructor(public modal: NgbActiveModal, public boardService: BoardService, public submit: CommunicationService<CreateBoardRequest>) {
    this.boardService.getBoardTemplates().subscribe(data => (this.boardTemplates = data));
  }

  ngOnInit() {}

  onSubmit() {
    this.submit.getSubject().next(this.formModelToRequest(this.formModel));
  }

  formModelToRequest(formModel: any): CreateBoardRequest {
    const key = formModel.templateKey;
    const columnDefinitions: BoardColumn[] = this.boardTemplates.filter(boardTemplate => {
      return boardTemplate.key === key;
    })[0].boardColumns;
    return new CreateBoardRequest(formModel.boardName, formModel.description, columnDefinitions);
  }

  onSelect(boardName: string) {
    const boardTemplate = this.boardTemplates.filter(b => {
      return b.key === boardName;
    })[0];
    this.currentDescription = boardTemplate.description;
  }
}
