import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RfpEventAttendence } from 'app/shared/model/rfp-event-attendence.model';
import { RfpEventAttendenceService } from './rfp-event-attendence.service';
import { RfpEventAttendenceComponent } from './rfp-event-attendence.component';
import { RfpEventAttendenceDetailComponent } from './rfp-event-attendence-detail.component';
import { RfpEventAttendenceUpdateComponent } from './rfp-event-attendence-update.component';
import { RfpEventAttendenceDeletePopupComponent } from './rfp-event-attendence-delete-dialog.component';
import { IRfpEventAttendence } from 'app/shared/model/rfp-event-attendence.model';

@Injectable({ providedIn: 'root' })
export class RfpEventAttendenceResolve implements Resolve<IRfpEventAttendence> {
    constructor(private service: RfpEventAttendenceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RfpEventAttendence> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RfpEventAttendence>) => response.ok),
                map((rfpEventAttendence: HttpResponse<RfpEventAttendence>) => rfpEventAttendence.body)
            );
        }
        return of(new RfpEventAttendence());
    }
}

export const rfpEventAttendenceRoute: Routes = [
    {
        path: 'rfp-event-attendence',
        component: RfpEventAttendenceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpEventAttendence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-event-attendence/:id/view',
        component: RfpEventAttendenceDetailComponent,
        resolve: {
            rfpEventAttendence: RfpEventAttendenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpEventAttendence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-event-attendence/new',
        component: RfpEventAttendenceUpdateComponent,
        resolve: {
            rfpEventAttendence: RfpEventAttendenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpEventAttendence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-event-attendence/:id/edit',
        component: RfpEventAttendenceUpdateComponent,
        resolve: {
            rfpEventAttendence: RfpEventAttendenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpEventAttendence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rfpEventAttendencePopupRoute: Routes = [
    {
        path: 'rfp-event-attendence/:id/delete',
        component: RfpEventAttendenceDeletePopupComponent,
        resolve: {
            rfpEventAttendence: RfpEventAttendenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpEventAttendence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
