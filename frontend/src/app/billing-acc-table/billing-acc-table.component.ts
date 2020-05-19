import {Component, OnInit, ViewChild} from "@angular/core";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatTableDataSource} from "@angular/material/table";
import {BillingAccount} from "../app-object-types/billingAccount";
import {BillingAccountService} from "../app-services/billing-account.service";
import {Payment} from "../app-object-types/payment";

@Component({
  selector: 'app-billing-acc-table',
  templateUrl: './billing-acc-table.component.html',
  styleUrls: ['./billing-acc-table.component.css'],
})
export class BillingAccountTableComponent implements OnInit{
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  basicParentId = 1000;
  private displayedColumns: string[] = ['name', 'balance', 'status', "actions"];

  dataLength = 0;
  dataSource: MatTableDataSource<BillingAccount>;

  constructor(private accountService: BillingAccountService) {
    this.dataSource = new MatTableDataSource<BillingAccount>();
  }

  ngOnInit(){
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.getAllAccounts(this.basicParentId); //REMEMBER TO CHANGE!
  }

  private getAllAccounts(customerId: number){
    this.accountService.getAllAccounts(customerId).subscribe((data:BillingAccount[]) => {
      this.dataSource.data = this.dataSource.data.concat(data);
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
