import {Component, OnInit, ViewChild} from "@angular/core";
import {PaymentService} from "../app-services/payment.service";
import {Payment} from "../app-object-types/payment";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {PaymentUpdateComponent} from "../payment-update/payment-update.component";
import {PaymentCreateComponent} from "../payment-create/payment-create.component";
import {DateFormatterService} from "../app-services/date-formatter.service";

@Component({
  selector: 'app-payment-table',
  templateUrl: './payment-table.component.html',
  styleUrls: ['./payment-table.component.css'],
})
export class PaymentTableComponent implements OnInit{
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  basicParentId = 20;

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.getAllPayments(this.basicParentId); //REMEMBER TO CHANGE!
  }

  constructor(private paymentService: PaymentService,
              private dateFormatter:DateFormatterService,
              private dialog: MatDialog) {
    this.dataSource = new MatTableDataSource<Payment>();
  }

  private displayedColumns: string[] = ['name', 'createdWhen', 'amount', 'status', 'method', 'createdBy', 'cancellationDate', "actions"];
  dataLength = 0;
  dataSource: MatTableDataSource<Payment>;

  getPayment(paymentId){
    this.paymentService.getPayment(paymentId).subscribe((data:Payment) => {
      this.dataSource.data.push(data);
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    });
  }

  getAllPayments(billingAccountId){
    this.paymentService.getAllPayments(billingAccountId).subscribe((data:Payment[]) => {
      this.dataSource.data = this.dataSource.data.concat(data);
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    });
  }

  reloadPayments(billingAccountId){
    this.dataSource.data = [];
    this.getAllPayments(billingAccountId);
  }

  cancelPayment(paymentId){
    this.paymentService.cancelPayment(paymentId).subscribe(response => {
      console.log(response);
      let index = this.dataSource.data.findIndex((element:Payment) => (element.objectId == paymentId));
      this.dataSource.data[index] = response;
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    });
  }

  deletePayment(paymentId){
    this.paymentService.deletePayment(paymentId).subscribe(response => {
      console.log(response);
      this.dataSource.data = this.dataSource.data.filter((element:Payment) => (element.objectId != paymentId));
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    });
  }

  updatePayment(paymentId){
    this.paymentService.updatePayment(paymentId).subscribe(response => {
      //
      //REMEMBER TO CHANGE! |
      //
      if(response.parentId == this.basicParentId){
        let index = this.dataSource.data.findIndex((element:Payment) => (element.objectId == paymentId));
        this.dataSource.data[index] = response;
      }
      else{
        this.dataSource.data = this.dataSource.data.filter((element:Payment) => (element.objectId != paymentId));
      }
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    });
  }

  editPayment(paymentId, index){
    let i = 0;
    this.paymentService.updateDto = JSON.parse(JSON.stringify(this.dataSource.data[index]));
    this.dialog.open(PaymentUpdateComponent);
    this.dialog.afterAllClosed.subscribe(response => {
      if(i === 0){
        this.updatePayment(paymentId);
        i++;
      }
    });
  }

  createPayment(){
    let i = 0;
    this.paymentService.createDto.parentId = this.basicParentId;
    this.paymentService.createDto.paymentMethod = "CASH";
    this.dialog.open(PaymentCreateComponent);
    this.dialog.afterAllClosed.subscribe(data => {
      if(i === 0){
        this.paymentService.createPayment().subscribe(response => {
          this.reloadPayments(this.basicParentId);
          i++;
        });
      }
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
