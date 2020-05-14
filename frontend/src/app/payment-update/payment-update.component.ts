import {Component} from "@angular/core";
import {MatDialogRef} from "@angular/material/dialog";
import {PaymentService} from "../app-services/payment.service";
import {PaymentUpdateDto} from "../app-object-types/PaymentUpdateDto";

@Component({
  selector: 'app-payment-update',
  templateUrl: './payment-update.component.html',
  styleUrls: ['./payment-update.component.css'],
})
export class PaymentUpdateComponent{
  data: PaymentUpdateDto;

  constructor(public dialogRef: MatDialogRef<PaymentUpdateComponent>,
              public paymentService: PaymentService) {
      this.data = JSON.parse(JSON.stringify(this.paymentService.updateDto));
      this.data.createdWhen = this.data.createdWhen.slice(0, -9);
      this.data.cancellationDate = this.data.cancellationDate.slice(0, -9);
  }

  submit() {
    this.paymentService.updateDto = JSON.parse(JSON.stringify(this.data));
    this.paymentService.updateDto.createdWhen = this.paymentService.updateDto.createdWhen.concat(".000+0000");
    this.paymentService.updateDto.cancellationDate = this.paymentService.updateDto.cancellationDate.concat(".000+0000");
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
