export class BoardSummary {
  id: boolean;
  name: string;
  columnDefinitions: Array<BoardColumn>;
  createdAt: Date;
}

export class BoardColumn {
  columnType: string;
  title: string;
}
