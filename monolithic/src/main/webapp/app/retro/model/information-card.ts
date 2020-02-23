import * as uuid from 'uuid';

export class InformationCard {
  id: string;
  boardId: string;
  columnKey: string;
  createdAt: Date;
  text: string;
  updatedAt: Date;
}

export class InformationCardVM extends InformationCard {
  /**
   * Only used internally for blank cards, as they do not have an id in the backend.
   */
  key: string;
  editMode: boolean;

  static of(informationCard: InformationCard): InformationCardVM {
    const informationCardVM = new InformationCardVM();
    informationCardVM.id = informationCard.id;
    informationCardVM.key = uuid.v4();
    informationCardVM.boardId = informationCard.boardId;
    informationCardVM.columnKey = informationCard.columnKey;
    informationCardVM.text = informationCard.text;
    informationCardVM.updatedAt = informationCard.updatedAt;
    informationCardVM.createdAt = informationCard.createdAt;
    return informationCardVM;
  }
}

export class CreateCardRequest {
  boardId: string;
  columnKey: string;
  text: string;
}

export class UpdateCardRequest {
  id: string;
  boardId: string;
  columnKey: string;
  text: string;
}

export class ReorderCardRequest {
  constructor(public id: string, public oldIndex: number, public newIndex: number, public oldColumn: string, public newColumn: string) {}
}
