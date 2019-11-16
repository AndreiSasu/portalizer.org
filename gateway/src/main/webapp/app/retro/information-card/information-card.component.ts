import { EventEmitter, Input, Output, Component, OnInit } from '@angular/core';
import { InformationCardVM } from '../model/information-card';
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
  @Output() removed = new EventEmitter<InformationCardVM>();

  faTrash = faTrash;
  faEdit = faEdit;
  editMode: boolean;
  text: string;

  constructor() {}

  ngOnInit() {
    this.text = this.informationCard.text;
    this.editMode = this.informationCard.editMode;
  }

  onSave() {
    this.informationCard.text = this.text;
    this.saved.emit(this.informationCard);
    this.editMode = false;
  }

  onRemove() {
    this.removed.emit(this.informationCard);
  }
}