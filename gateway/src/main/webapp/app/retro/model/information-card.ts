export class InformationCard {
  id: string;
  boardId: string;
  columnType: string;
  createdAt: Date;
  text: string;
  updatedAt: Date;
}

export class CreateCardRequest {
  boardId: string;
  columnType: string;
  text: string;
}
