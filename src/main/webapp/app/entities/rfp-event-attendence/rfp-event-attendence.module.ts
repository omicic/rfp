import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfpSharedModule } from 'app/shared';
import {
    RfpEventAttendenceComponent,
    RfpEventAttendenceDetailComponent,
    RfpEventAttendenceUpdateComponent,
    RfpEventAttendenceDeletePopupComponent,
    RfpEventAttendenceDeleteDialogComponent,
    rfpEventAttendenceRoute,
    rfpEventAttendencePopupRoute
} from './';

const ENTITY_STATES = [...rfpEventAttendenceRoute, ...rfpEventAttendencePopupRoute];

@NgModule({
    imports: [RfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RfpEventAttendenceComponent,
        RfpEventAttendenceDetailComponent,
        RfpEventAttendenceUpdateComponent,
        RfpEventAttendenceDeleteDialogComponent,
        RfpEventAttendenceDeletePopupComponent
    ],
    entryComponents: [
        RfpEventAttendenceComponent,
        RfpEventAttendenceUpdateComponent,
        RfpEventAttendenceDeleteDialogComponent,
        RfpEventAttendenceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RfpRfpEventAttendenceModule {}
