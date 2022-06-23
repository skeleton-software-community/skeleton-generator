import { NgModule, Optional, SkipSelf } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

import { SharedModule } from '../shared/shared.module';
import { HeaderComponent } from './components/header/header.component';
import { NavigationDrawerComponent } from './components/navigation-drawer/navigation-drawer.component';
import { FooterComponent } from './components/footer/footer.component';
import { NavigationListComponent } from './components/navigation-list/navigation-list.component';
import { ConfirmationModalComponent } from './components/confirmation-modal/confirmation-modal.component';


@NgModule({
  imports: [BrowserAnimationsModule, HttpClientModule, SharedModule],
  declarations: [HeaderComponent, NavigationDrawerComponent, FooterComponent, NavigationListComponent, ConfirmationModalComponent],
  exports: [HeaderComponent, NavigationDrawerComponent, FooterComponent, NavigationListComponent, ConfirmationModalComponent]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error(`CoreModule has already been loaded. Import Core modules in the AppModule only.`);
    }
  }
}
