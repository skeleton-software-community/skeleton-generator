import { NavLink } from "./nav-link";

export interface NavMenu {
  text: string;
  path?: string;
  links?:NavLink[];
}
