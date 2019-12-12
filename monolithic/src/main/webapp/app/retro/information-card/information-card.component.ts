import { EventEmitter, Input, Output, Component, OnInit } from '@angular/core';
import { InformationCardVM } from '../model/information-card';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';
import { CardStorageService } from '../card-storage.service';

@Component({
  selector: 'jhi-information-card',
  templateUrl: './information-card.component.html',
  styleUrls: ['./information-card.component.scss']
})
export class InformationCardComponent implements OnInit {
  @Input() color: string;
  @Input() informationCard: InformationCardVM;
  @Output() saved = new EventEmitter<InformationCardVM>();
  @Output() removed = new EventEmitter<InformationCardVM>();

  faTrash = faTrash;
  faEdit = faEdit;
  editMode: boolean;

  constructor(private cardStorageService: CardStorageService) {}

  ngOnInit() {
    this.editMode = this.informationCard.editMode;
  }

  onSave() {
    this.saved.emit(this.informationCard);
    this.editMode = false;
  }

  onRemove() {
    this.removed.emit(this.informationCard);
  }
  /*eslint-disable*/
  updateLocalStorage() {
    this.cardStorageService.addCard(this.informationCard.boardId, this.informationCard);
  }
}
