import { Component, ViewChild } from '@angular/core';
import { map } from 'rxjs/operators';
import { MatSidenav } from '@angular/material/sidenav';
import { Observable } from 'rxjs';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { navigationMenus } from '../../data/navigation-menus';
import { NavMenu } from '../../models/nav-menu';
import { Router } from '@angular/router';
import { CdkAccordionItem } from '@angular/cdk/accordion';

@Component({
  selector: 'app-body',
  templateUrl: './body.component.html',
  styleUrls: ['./body.component.scss']
})
export class BodyComponent {
  @ViewChild('sidenavRef', { static: false }) sideNavRef: MatSidenav;
  menus: NavMenu[] = navigationMenus;

  toggle(): void {
    this.sideNavRef.toggle();
  }
}
