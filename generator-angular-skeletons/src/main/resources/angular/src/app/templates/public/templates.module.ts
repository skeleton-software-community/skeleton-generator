import { NgModule } from '@angular/core';

import { HeaderComponent } from './components/header/header.component';
import { BodyComponent } from './components/body/body.component';
import { FooterComponent } from './components/footer/footer.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { TemplateComponent } from './components/template/template.component';
import { CommonModule } from '@angular/common';


@NgModule({
  imports: [CommonModule, SharedModule],
  declarations: [HeaderComponent, BodyComponent, FooterComponent, TemplateComponent],
  exports: [TemplateComponent]
})
export class PublicTemplatesModule {
}
