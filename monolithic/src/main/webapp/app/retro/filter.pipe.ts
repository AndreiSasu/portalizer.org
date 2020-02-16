import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {
  /*eslint-disable*/
  transform(value: any, ...args: any[]): any {
    console.log(args);
    console.log(value);
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
