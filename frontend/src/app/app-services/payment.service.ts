import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Payment} from "../app-object-types/payment";
import {PaymentUpdateDto} from "../app-object-types/PaymentUpdateDto";
import {PaymentCreateDto} from "../app-object-types/PaymentCreateDto";

@Injectable({providedIn: "root"})
export class PaymentService {
  constructor(private http: HttpClient) {}

  public updateDto: PaymentUpdateDto;
  public createDto: PaymentCreateDto = {
    "parentId": null,
    "amount": null,
    "paymentMethod": null,
    "createdBy": null,
  };

  getAccountPayments(accountId){
    return this.http.get('http://localhost:8080/payment/49');
  }

  getPayment(paymentId):Observable<Payment>{
    return this.http.get<Payment>('http://localhost:8080/payment/' + paymentId.toString());
  }

  getAllPayments(billingAccountId):Observable<Payment[]>{
    return this.http.get<Payment[]>('http://localhost:8080/payment/account/' + billingAccountId.toString());
  }

  cancelPayment(paymentId):Observable<any>{
    return this.http.put('http://localhost:8080/payment/cancel/' + paymentId.toString(), null);
  }

  deletePayment(paymentId):Observable<any>{
    return this.http.delete('http://localhost:8080/payment/' + paymentId.toString());
  }

  updatePayment(paymentId):Observable<any>{
    return this.http.put('http://localhost:8080/payment/' + paymentId.toString(), this.updateDto);
  }

  createPayment():Observable<any>{
    return this.http.post('http://localhost:8080/payment/', this.createDto);
  }
}
