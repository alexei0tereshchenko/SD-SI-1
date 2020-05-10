export class PaymentUpdateDto {
  name: string;
  parentId: number;
  description: string;
  createdWhen: string;
  amount: number;
  status: string;
  paymentMethod: string;
  createdBy: string;
  cancellationDate: string;
};
