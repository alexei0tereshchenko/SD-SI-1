import {Component, OnInit, ViewChild} from "@angular/core";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {DateFormatterService} from "../app-services/date-formatter.service";
import {ActivatedRoute} from "@angular/router";
import {OtsService} from "../app-services/ots.service";
import {Ots} from "../app-object-types/ots";
import {OtsUpdateComponent} from "../ots-update/ots-update.component";
import {OtsCreateComponent} from "../ots-create/ots-create.component";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-ots-table',
  templateUrl: './ots-table.component.html',
  styleUrls: ['./ots-table.component.css'],
})
export class OtsTableComponent implements OnInit{
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  basicParentId = 20;

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.getAllOts(this.basicParentId);
  }

  constructor(private otsService: OtsService,
              private dateFormatter:DateFormatterService,
              private activatedRoute: ActivatedRoute,
              private snackBar: MatSnackBar,
              private dialog: MatDialog) {
    this.basicParentId = activatedRoute.snapshot.params["accountId"];
    this.dataSource = new MatTableDataSource<Ots>();
  }

  private displayedColumns: string[] = ['name', 'createdWhen', 'amount', 'instalments', 'status', 'terminationReason', 'cancellationDate', "actions"];
  dataLength = 0;
  dataSource: MatTableDataSource<Ots>;

  getOts(otsId){
    this.otsService.getOts(otsId).subscribe((data:Ots) => {
      this.dataSource.data.push(data);
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    },
      error => {this.snackBar.open(error.error.message, "Ok", {duration: 8000});});
  }

  getAllOts(billingAccountId){
    this.otsService.getAllOts(billingAccountId).subscribe((data:Ots[]) => {
      this.dataSource.data = this.dataSource.data.concat(data);
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    },
      error => {this.snackBar.open(error.error.message, "Ok", {duration: 8000});});
  }

  reloadOts(billingAccountId){
    this.dataSource.data = [];
    this.getAllOts(billingAccountId);
  }

  cancelOts(otsId){
    this.otsService.terminateOts(otsId).subscribe(response => {
      console.log(response);
      let index = this.dataSource.data.findIndex((element:Ots) => (element.objectId == otsId));
      this.dataSource.data[index] = response;
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    },
      error => {this.snackBar.open(error.error.message, "Ok", {duration: 8000});});
  }

  deleteOts(otsId){
    this.otsService.deleteOts(otsId).subscribe(response => {
      this.dataSource.data = this.dataSource.data.filter((element:Ots) => (element.objectId != otsId));
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    },
      error => {this.snackBar.open(error.error.message, "Ok", {duration: 8000});});
  }

  updateOts(otsId){
    this.otsService.updateOts(otsId).subscribe(response => {
      if(response.parentId == this.basicParentId){
        let index = this.dataSource.data.findIndex((element:Ots) => (element.objectId == otsId));
        this.dataSource.data[index] = response;
      }
      else{
        this.dataSource.data = this.dataSource.data.filter((element:Ots) => (element.objectId != otsId));
      }
      this.dataLength = this.dataSource.data.length;
      this.dataSource._updateChangeSubscription();
    },
      error => {this.snackBar.open(error.error.message, "Ok", {duration: 8000});});
  }

  editOts(otsId, index){
    let i = 0;
    this.otsService.updateDto = JSON.parse(JSON.stringify(this.dataSource.data[index]));
    let dialogRef = this.dialog.open(OtsUpdateComponent);
    dialogRef.afterClosed().subscribe(response => {
      if(response != "cancelled") {
        if(i === 0){
          this.updateOts(otsId);
          i++;
        }
      }
    });
  }

  createOts(){
    let i = 0;
    this.otsService.createDto.parentId = this.basicParentId;
    this.otsService.createDto.amount = null;
    this.otsService.createDto.instalments = false;
    let dialogRef = this.dialog.open(OtsCreateComponent);
    dialogRef.afterClosed().subscribe(data => {
      if(data != "cancelled"){
        if(i === 0){
          this.otsService.createOts().subscribe(response => {
            this.reloadOts(this.basicParentId);
            i++;
          });
        }
      }
    },
      error => {this.snackBar.open(error.error.message, "Ok", {duration: 8000});});
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
