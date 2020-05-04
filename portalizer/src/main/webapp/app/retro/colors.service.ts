import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ColorsService {
  colorMap: Map<string, string> = new Map();

  // TODO: store these in the backend
  constructor() {
    this.colorMap.set('Mad', 'danger');
    this.colorMap.set('Sad', 'info');
    this.colorMap.set('Glad', 'success');

    this.colorMap.set('Went Well', 'danger');
    this.colorMap.set('To Improve', 'info');
    this.colorMap.set('Action Items', 'success');

    this.colorMap.set('More', 'warning');
    this.colorMap.set('Less', 'danger');
    this.colorMap.set('Add', 'info');
    this.colorMap.set('Keep', 'success');

    this.colorMap.set('Stop', 'danger');
    this.colorMap.set('Start', 'info');
    this.colorMap.set('Continue', 'success');

    this.colorMap.set('Lacked', 'warning');
    this.colorMap.set('Longed For', 'danger');
    this.colorMap.set('Liked', 'info');
    this.colorMap.set('Learned', 'success');

    this.colorMap.set('To Discuss', 'danger');
    this.colorMap.set('Discussing', 'info');
    this.colorMap.set('Discussed', 'success');

    this.colorMap.set('Improve', 'warning');
    this.colorMap.set('Drop', 'danger');
    this.colorMap.set('Add', 'info');
    this.colorMap.set('Keep', 'success');

    this.colorMap.set('Keep Doing', 'success');
    this.colorMap.set('Less Of', 'warning');
    this.colorMap.set('More Of', 'info');
    this.colorMap.set('Stop Doing', 'danger');
    this.colorMap.set('Start Doing', 'primary');
  }

  getColor(key: string): string {
    const color = this.colorMap.get(key);
    return undefined !== color ? color : 'secondary';
  }
}
