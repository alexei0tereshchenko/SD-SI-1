import { Component } from "@angular/core";

@Component({
  selector: 'app-customer-table',
  templateUrl: './customer-table.component.html',
  styleUrls: ['./customer-table.component.css'],
})
export class CustomerTableComponent {
  private customers = [
    {name: "Sahim", surname: "Janesh", status: "active"},
    {name: "Alba", surname: "Flores", status: "active"},
    {name: "James", surname: "Sallivan", status: "active"}
    ];
}
