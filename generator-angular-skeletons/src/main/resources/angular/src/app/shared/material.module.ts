import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { CdkAccordionModule } from '@angular/cdk/accordion';

import { MatListModule } from '@angular/material/list';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTabsModule } from '@angular/material/tabs';
import { MatChipsModule } from '@angular/material/chips';

@NgModule({
  exports: [MatButtonModule, MatToolbarModule, MatIconModule,
    MatMenuModule, MatSidenavModule, MatListModule, MatTableModule,
    MatPaginatorModule, MatSortModule, MatInputModule, MatDatepickerModule,
    MatNativeDateModule, MatCheckboxModule, CdkAccordionModule,
    MatSnackBarModule, MatAutocompleteModule, MatSelectModule,
    MatDialogModule, MatSlideToggleModule, MatTabsModule, MatChipsModule]
})
export class MaterialModule {
}
