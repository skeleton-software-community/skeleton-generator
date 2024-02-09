import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { AuthRoutingModule } from './auth-routing.module';
import { CallbackComponent } from './callback/callback.component';
import { LogoutComponent } from './logout/logout.component';
import { PublicTemplatesModule } from 'src/app/templates/public/templates.module';

@NgModule({
declarations: [CallbackComponent, LogoutComponent],
imports: [CommonModule, SharedModule, AuthRoutingModule, PublicTemplatesModule]
})
export class AuthModule { }
