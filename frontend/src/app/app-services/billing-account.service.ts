import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {BillingAccount} from "../app-object-types/billingAccount";

@Injectable({providedIn: "root"})
export class BillingAccountService {
  constructor(private http: HttpClient) {}

  public getAllAccounts(customerId:number){
    return this.http.get<BillingAccount[]>("/assets/accounts.json");
  }
}
