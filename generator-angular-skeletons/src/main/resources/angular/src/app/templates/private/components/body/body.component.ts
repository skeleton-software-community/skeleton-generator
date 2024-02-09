import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { navigationMenus } from '../../data/navigation-menus';
import { NavMenu } from '../../models/nav-menu';

@Component({
  selector: 'app-private-body',
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
