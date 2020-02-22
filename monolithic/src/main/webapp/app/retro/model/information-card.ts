import * as uuid from 'uuid';

export class InformationCard {
  id: string;
  boardId: string;
  columnType: string;
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
    informationCardVM.columnType = informationCard.columnType;
    informationCardVM.text = informationCard.text;
    informationCardVM.updatedAt = informationCard.updatedAt;
    informationCardVM.createdAt = informationCard.createdAt;
    return informationCardVM;
  }
}

export class CreateCardRequest {
  boardId: string;
  columnType: string;
  text: string;
}

export class UpdateCardRequest {
  id: string;
  boardId: string;
  columnType: string;
  text: string;
}
