import { EventEmitter, Output, Input, Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-confirmation-modal',
  templateUrl: './confirmation-modal.component.html',
  styleUrls: ['./confirmation-modal.component.scss']
})
export class ConfirmationModalComponent implements OnInit {
  @Input() modal: any;

  constructor() {}

  ngOnInit() {}
}
