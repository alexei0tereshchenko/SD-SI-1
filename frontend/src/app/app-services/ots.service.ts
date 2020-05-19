import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {OtsUpdateDto} from "../app-object-types/otsUpdateDto";
import {OtsCreateDto} from "../app-object-types/otsCreateDto";
import {Ots} from "../app-object-types/ots";

@Injectable({providedIn: "root"})
export class OtsService {
  constructor(private http: HttpClient) {}

  public updateDto: OtsUpdateDto;
  public createDto: OtsCreateDto = {
    "parentId": null,
    "amount": null,
    "instalments": null,
  };

  getOts(otsId):Observable<Ots>{
    return this.http.get<Ots>('http://localhost:8080/ots/' + otsId.toString());
  }

  getAllOts(billingAccountId):Observable<Ots[]>{
    return this.http.get<Ots[]>('http://localhost:8080/ots/account/' + billingAccountId.toString());
  }

  terminateOts(otsId):Observable<any>{
    return this.http.put('http://localhost:8080/ots/terminate/' + otsId.toString(), null);
  }

  deleteOts(otsId):Observable<any>{
    return this.http.delete('http://localhost:8080/ots/' + otsId.toString());
  }

  updateOts(otsId):Observable<any>{
    return this.http.put('http://localhost:8080/ots/' + otsId.toString(), this.updateDto);
  }

  createOts():Observable<any>{
    return this.http.post('http://localhost:8080/ots/', this.createDto);
  }
}
