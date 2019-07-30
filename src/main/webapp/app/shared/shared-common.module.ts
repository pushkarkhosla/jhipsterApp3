import { NgModule } from '@angular/core';

import { JhipsterApp3SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [JhipsterApp3SharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [JhipsterApp3SharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipsterApp3SharedCommonModule {}
