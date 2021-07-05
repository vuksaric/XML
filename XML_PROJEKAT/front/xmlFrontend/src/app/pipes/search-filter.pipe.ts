import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilter'
})
export class SearchFilterPipe implements PipeTransform {

  transform(items: any[], searchValue: string): any[] {
    if (!items) {
      return [];
    }
    else if (!searchValue) {
      return [];
    }
    searchValue = searchValue.toLocaleLowerCase();

    return items.filter(it => {
      return it.toLocaleLowerCase().includes(searchValue);
    });
  }

}
