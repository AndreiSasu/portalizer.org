export interface PaginationPropertySort {
  direction: string;
  property: string;
}

export interface PaginationPage<T> {
  content?: Array<T>;
  last?: boolean;
  first?: boolean;
  number: number;
  size: number;
  totalPages?: number;
  itemsPerPage?: number;
  totalElements?: number;
  sort?: Array<PaginationPropertySort>;
}
