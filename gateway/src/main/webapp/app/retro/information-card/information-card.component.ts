import { EventEmitter, Input, Output, Component, OnInit } from '@angular/core';
import { InformationCard, InformationCardVM } from '../model/information-card';
import { faTrash, faEdit } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-information-card',
  templateUrl: './information-card.component.html',
  styleUrls: ['./information-card.component.scss']
})
export class InformationCardComponent implements OnInit {
  @Input() color: string;
  @Input() informationCard: InformationCardVM;
  @Output() saved = new EventEmitter<InformationCardVM>();

  faTrash = faTrash;
  faEdit = faEdit;
  editMode: boolean;

  constructor() {}

  ngOnInit() {
    this.editMode = this.informationCard.editMode;
  }

  onSave() {
    this.saved.emit(this.informationCard);
    this.editMode = false;
  }
}
