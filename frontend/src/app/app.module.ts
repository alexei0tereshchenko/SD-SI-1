import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {RouterModule} from "@angular/router";

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {PaymentTableComponent} from "./payment-table/payment-table.component";
import {MatTableModule} from "@angular/material/table";
import {HttpClientModule} from "@angular/common/http";
import {CustomerTableComponent} from "./customer-table/customer-table.component";
import {PaymentService} from "./app-services/payment.service";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatTooltipModule} from "@angular/material/tooltip";
import {PaymentUpdateComponent} from "./payment-update/payment-update.component";
import {MatDialogModule} from "@angular/material/dialog";
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {PaymentCreateComponent} from "./payment-create/payment-create.component";
import {DateFormatterService} from "./app-services/date-formatter.service";

@NgModule({
  declarations: [
    AppComponent,
    CustomerTableComponent,
    PaymentTableComponent,
    PaymentUpdateComponent,
    PaymentCreateComponent,
  ],
  entryComponents: [
    PaymentUpdateComponent,
    PaymentCreateComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    MatTableModule,
    MatToolbarModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    MatInputModule,
    MatButtonModule,
    MatTooltipModule,
    MatDialogModule,
    MatFormFieldModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: '', component: PaymentTableComponent},
    ]),
    FormsModule
  ],
  providers: [PaymentService, DateFormatterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
