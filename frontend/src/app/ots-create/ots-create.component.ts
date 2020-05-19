import {Component} from "@angular/core";
import {MatDialogRef} from "@angular/material/dialog";
import {OtsService} from "../app-services/ots.service";
import {OtsCreateDto} from "../app-object-types/otsCreateDto";

@Component({
  selector: 'app-ots-create',
  templateUrl: './ots-create.component.html',
  styleUrls: ['./ots-create.component.css'],
})
export class OtsCreateComponent{
  data: OtsCreateDto;
  instalments: string;

  constructor(public dialogRef: MatDialogRef<OtsCreateComponent>,
              public otsService: OtsService) {
    dialogRef.disableClose = true;
    this.data = JSON.parse(JSON.stringify(this.otsService.createDto));
    this.instalments = "NO";
    this.data.amount = null;
  }

  submit() {
    if(this.instalments == "NO"){
      this.data.instalments = false;
    }
    else{
      this.data.instalments = true;
    }
    this.otsService.createDto = JSON.parse(JSON.stringify(this.data));
  }

  cancel(): void {
    this.dialogRef.close("cancelled");
  }
}
