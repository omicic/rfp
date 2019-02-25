import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RfpSharedModule } from 'app/shared';
import {
    RfpUserComponent,
    RfpUserDetailComponent,
    RfpUserUpdateComponent,
    RfpUserDeletePopupComponent,
    RfpUserDeleteDialogComponent,
    rfpUserRoute,
    rfpUserPopupRoute
} from './';

const ENTITY_STATES = [...rfpUserRoute, ...rfpUserPopupRoute];

@NgModule({
    imports: [RfpSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RfpUserComponent,
        RfpUserDetailComponent,
        RfpUserUpdateComponent,
        RfpUserDeleteDialogComponent,
        RfpUserDeletePopupComponent
    ],
    entryComponents: [RfpUserComponent, RfpUserUpdateComponent, RfpUserDeleteDialogComponent, RfpUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RfpRfpUserModule {}
