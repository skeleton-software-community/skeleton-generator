import { NavLink } from "./nav-link";

export interface NavMenu {
  text: string;
  icon?: string;
  path?: string;
  links?:NavLink[];
}
