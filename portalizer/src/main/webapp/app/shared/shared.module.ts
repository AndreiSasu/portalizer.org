import { NgModule } from '@angular/core';
import { PortalizerSharedLibsModule } from './shared-libs.module';
import { JhiAlertComponent } from './alert/alert.component';
import { JhiAlertErrorComponent } from './alert/alert-error.component';
import { JhiLoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { LoaderComponent } from './loader/loader.component';

@NgModule({
  imports: [PortalizerSharedLibsModule],
  declarations: [JhiAlertComponent, LoaderComponent, JhiAlertErrorComponent, JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [
    PortalizerSharedLibsModule,
    JhiAlertComponent,
    LoaderComponent,
    JhiAlertErrorComponent,
    JhiLoginModalComponent,
    HasAnyAuthorityDirective
  ]
})
export class PortalizerSharedModule {}
