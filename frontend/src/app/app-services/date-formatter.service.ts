import {Injectable} from "@angular/core";

@Injectable({providedIn: "root"})
export class DateFormatterService{
  formatDate(date:string){
    date = date.replace("T", " ");
    date = date.replace(".000+0000", "");
    return date;
  }
}
