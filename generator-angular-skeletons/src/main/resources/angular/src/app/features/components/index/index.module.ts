import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { IndexComponent } from './index.component';
import { IndexRoutingModule } from './index-routing.module';
import { PrivateTemplatesModule } from 'src/app/templates/private/templates.module';

@NgModule({
declarations: [IndexComponent],
imports: [CommonModule, SharedModule, IndexRoutingModule, PrivateTemplatesModule]
})
export class IndexModule { }
