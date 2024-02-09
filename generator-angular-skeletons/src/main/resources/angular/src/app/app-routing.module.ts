import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/services/AuthGuard';
/**
 * auto generated app routing module ts file
 * <br/>write modifications between specific code marks
 * <br/>processed by skeleton-generator
 */

const routes: Routes = [

/* Specific Code Start */
,{path: '', redirectTo: '/index', pathMatch:'full'}
,{path:'', loadChildren:()=>import('src/app/features/components/index/index.module').then(m=>m.IndexModule), canActivate: [AuthGuard]}
,{path:'', loadChildren:()=>import('src/app/features/components/auth/auth.module').then(m=>m.AuthModule)}
,{path:'**', redirectTo: '/index'}
/* Specific Code End */
];

@NgModule({
imports: [RouterModule.forRoot(routes)],
exports: [RouterModule]
})
export class AppRoutingModule { }
