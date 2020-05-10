import {Component} from "@angular/core";
import {MatDialogRef} from "@angular/material/dialog";
import {PaymentService} from "../app-services/payment.service";
import {PaymentCreateDto} from "../app-object-types/PaymentCreateDto";

@Component({
  selector: 'app-payment-create',
  templateUrl: './payment-create.component.html',
  styleUrls: ['./payment-create.component.css'],
})
export class PaymentCreateComponent{
  data: PaymentCreateDto;

  constructor(public dialogRef: MatDialogRef<PaymentCreateComponent>,
              public paymentService: PaymentService) {
    this.data = JSON.parse(JSON.stringify(this.paymentService.createDto));
  }

  submit() {
    this.paymentService.createDto = JSON.parse(JSON.stringify(this.data));
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
