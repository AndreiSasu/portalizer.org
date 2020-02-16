import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {
  transform(value: any, ...args: any[]): any {
    if (!value) return null;
    if (!args) return value;
    if (!args[0]) return value;

    return value.filter(function(item) {
      return JSON.stringify(item)
        .toLowerCase()
        .includes(args[0]);
    });
  }
}
