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
  }

  getColor(key: string): string {
    return this.colorMap.get(key);
  }
}
