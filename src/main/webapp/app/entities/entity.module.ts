import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RfpRfpUserModule } from './rfp-user/rfp-user.module';
import { RfpRfpEventAttendenceModule } from './rfp-event-attendence/rfp-event-attendence.module';
import { RfpRfpEventModule } from './rfp-event/rfp-event.module';
import { RfpRfpLocationModule } from './rfp-location/rfp-location.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RfpRfpUserModule,
        RfpRfpEventAttendenceModule,
        RfpRfpEventModule,
        RfpRfpLocationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RfpEntityModule {}
