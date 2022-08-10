import { CdkAccordionItem } from '@angular/cdk/accordion';
import { Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { NavMenu } from '../../models/nav-menu';

@Component({
  selector: 'app-navigation-list',
  templateUrl: './navigation-list.component.html',
  styleUrls: ['./navigation-list.component.scss']
})
export class NavigationListComponent {
  @ViewChild('sidenavRef', { static: false }) sideNavRef: MatSidenav;
  @Output() listItemClick = new EventEmitter();
  @Input() menu: NavMenu;

  constructor(public router: Router) {

  }

  public togglePanel(panel: CdkAccordionItem) {
    if (this.menu.links) {
      panel.toggle();
    } else {
      this.router.navigateByUrl(this.menu.path);
      this.listItemClick.emit();
    }
  }
}
