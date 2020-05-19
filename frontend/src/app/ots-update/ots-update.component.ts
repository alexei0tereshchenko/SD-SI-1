import {Component} from "@angular/core";
import {MatDialogRef} from "@angular/material/dialog";
import {OtsService} from "../app-services/ots.service";
import {OtsUpdateDto} from "../app-object-types/otsUpdateDto";

@Component({
  selector: 'app-ots-update',
  templateUrl: './ots-update.component.html',
  styleUrls: ['./ots-update.component.css'],
})
export class OtsUpdateComponent{
  data: OtsUpdateDto;
  instalments: string;

  constructor(public dialogRef: MatDialogRef<OtsUpdateComponent>,
              public otsService: OtsService) {
    dialogRef.disableClose = true;
    this.data = JSON.parse(JSON.stringify(this.otsService.updateDto));
    if(this.data.instalments == true){
      this.instalments = "YES";
    }
    else{
      this.instalments = "NO";
    }
    //this.data.createdWhen = this.data.createdWhen.slice(0, -9);
    //this.data.cancellationDate = this.data.cancellationDate.slice(0, -9);
  }

  submit() {
    if(this.instalments == "YES"){
      this.data.instalments = true;
    }
    else{
      this.data.instalments = false;
    }
    this.otsService.updateDto = JSON.parse(JSON.stringify(this.data));
    //this.otsService.updateDto.createdWhen = this.otsService.updateDto.createdWhen.concat(".000+0000");
    //this.otsService.updateDto.cancellationDate = this.otsService.updateDto.cancellationDate.concat(".000+0000");
  }

  cancel(): void {
    this.dialogRef.close("cancelled");
  }
}
