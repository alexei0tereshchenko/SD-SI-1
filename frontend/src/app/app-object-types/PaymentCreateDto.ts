export class PaymentCreateDto {
  parentId: number;
  amount: number;
  paymentMethod: string;
  createdBy: string;
};
