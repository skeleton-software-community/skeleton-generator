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
  selector: 'app-navigation-drawer',
  templateUrl: './navigation-drawer.component.html',
  styleUrls: ['./navigation-drawer.component.scss']
})
export class NavigationDrawerComponent {
  @ViewChild('sidenavRef', { static: false }) sideNavRef: MatSidenav;
  menus: NavMenu[] = navigationMenus;
  mode$: Observable<'over' | 'push' | 'side'>;
  large$: Observable<boolean>;

  constructor(breakpointObserver: BreakpointObserver) {
    this.large$ = breakpointObserver.observe([Breakpoints.Large, Breakpoints.XLarge]).pipe(map(result => result.matches));
    this.mode$ = this.large$.pipe(map(large => (large ? 'side' : 'over')));
  }

  toggle(): void {
    this.sideNavRef.toggle();
  }


}
