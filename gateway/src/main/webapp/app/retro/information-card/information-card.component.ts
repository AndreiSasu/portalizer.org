import { EventEmitter, Input, Output, Component, OnInit } from '@angular/core';
import { InformationCard } from '../model/information-card';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-information-card',
  templateUrl: './information-card.component.html',
  styleUrls: ['./information-card.component.scss']
})
export class InformationCardComponent implements OnInit {
  @Input() color: string;
  @Input() informationCard: InformationCard;
  @Output() saved = new EventEmitter<InformationCard>();

  faTrash = faTrash;
  faEdit = faEdit;
  editMode: boolean;

  constructor() {}

  ngOnInit() {}

  onSave() {
    this.saved.emit(this.informationCard);
    this.editMode = false;
  }
}
