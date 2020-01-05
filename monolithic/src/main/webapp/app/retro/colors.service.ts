import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ColorsService {
  colorMap: Map<string, string> = new Map();

  constructor() {
    this.colorMap.set('MAD', 'danger');
    this.colorMap.set('SAD', 'info');
    this.colorMap.set('GLAD', 'success');

    this.colorMap.set('WENT_WELL', 'danger');
    this.colorMap.set('TO_IMPROVE', 'info');
    this.colorMap.set('ACTION_ITEMS', 'success');

    this.colorMap.set('MORE', 'warning');
    this.colorMap.set('LESS', 'danger');
    this.colorMap.set('ADD', 'info');
    this.colorMap.set('KEEP', 'success');

    this.colorMap.set('STOP', 'danger');
    this.colorMap.set('START', 'info');
    this.colorMap.set('CONTINUE', 'success');

    this.colorMap.set('LACKED', 'warning');
    this.colorMap.set('LONGED_FOR', 'danger');
    this.colorMap.set('LIKED', 'info');
    this.colorMap.set('LEARNED', 'success');

    this.colorMap.set('TO_DISCUSS', 'danger');
    this.colorMap.set('DISCUSSING', 'info');
    this.colorMap.set('DISCUSSED', 'success');

    this.colorMap.set('IMPROVE', 'warning');
    this.colorMap.set('DROP', 'danger');
    this.colorMap.set('ADD', 'info');
    this.colorMap.set('KEEP', 'success');
  }

  getColor(key: string): string {
    return this.colorMap.get(key);
  }
}
