import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfpSharedModule } from 'app/shared';
import {
    RfpEventComponent,
    RfpEventDetailComponent,
    RfpEventUpdateComponent,
    RfpEventDeletePopupComponent,
    RfpEventDeleteDialogComponent,
    rfpEventRoute,
    rfpEventPopupRoute
} from './';

const ENTITY_STATES = [...rfpEventRoute, ...rfpEventPopupRoute];

@NgModule({
    imports: [RfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RfpEventComponent,
        RfpEventDetailComponent,
        RfpEventUpdateComponent,
        RfpEventDeleteDialogComponent,
        RfpEventDeletePopupComponent
    ],
    entryComponents: [RfpEventComponent, RfpEventUpdateComponent, RfpEventDeleteDialogComponent, RfpEventDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RfpRfpEventModule {}
