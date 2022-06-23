import { NgModule } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { BrowserModule, DomSanitizer } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    SharedModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(private matIconRegistry: MatIconRegistry, private sanitizer: DomSanitizer) {
    this.matIconRegistry.addSvgIconSet(this.sanitizer.bypassSecurityTrustResourceUrl('../assets/icons/mdi.svg'));
  }
}
