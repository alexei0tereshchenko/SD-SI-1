export class PaymentUpdateDto {
  parentId: number;
  description: string;
  createdWhen: string;
  amount: number;
  status: string;
  paymentMethod: string;
  createdBy: string;
  cancellationDate: string;
};
