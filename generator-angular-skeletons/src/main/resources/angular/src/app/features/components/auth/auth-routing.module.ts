import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CallbackComponent } from './callback/callback.component';
import { LogoutComponent } from './logout/logout.component';

const routes: Routes = [
{path: 'callback', component: CallbackComponent},
{path: 'logout', component: LogoutComponent}
];

@NgModule({
imports: [RouterModule.forChild(routes)],
exports: [RouterModule]
})
export class AuthRoutingModule { }
