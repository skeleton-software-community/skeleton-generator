import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms'
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material.module';
import { RouterModule } from '@angular/router';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS, MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { ISO_LOCAL_DATE_FORMAT } from '../core/adapters/IsoLocalDateAdapter';


@NgModule({
  imports: [CommonModule, MaterialModule, RouterModule, ReactiveFormsModule, FormsModule],
  exports: [CommonModule, MaterialModule, RouterModule, ReactiveFormsModule, FormsModule],
  declarations: [],
  providers: [
    {provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: {useUtc:true, strict: true}},
    {provide: MAT_DATE_LOCALE, useValue: 'GMT'},
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]},
    {provide: MAT_DATE_FORMATS, useValue: ISO_LOCAL_DATE_FORMAT}

  ]
})
export class SharedModule {
}

