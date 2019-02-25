import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfpSharedModule } from 'app/shared';
import {
    RfpLocationComponent,
    RfpLocationDetailComponent,
    RfpLocationUpdateComponent,
    RfpLocationDeletePopupComponent,
    RfpLocationDeleteDialogComponent,
    rfpLocationRoute,
    rfpLocationPopupRoute
} from './';

const ENTITY_STATES = [...rfpLocationRoute, ...rfpLocationPopupRoute];

@NgModule({
    imports: [RfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RfpLocationComponent,
        RfpLocationDetailComponent,
        RfpLocationUpdateComponent,
        RfpLocationDeleteDialogComponent,
        RfpLocationDeletePopupComponent
    ],
    entryComponents: [RfpLocationComponent, RfpLocationUpdateComponent, RfpLocationDeleteDialogComponent, RfpLocationDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RfpRfpLocationModule {}
