import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RfpEvent } from 'app/shared/model/rfp-event.model';
import { RfpEventService } from './rfp-event.service';
import { RfpEventComponent } from './rfp-event.component';
import { RfpEventDetailComponent } from './rfp-event-detail.component';
import { RfpEventUpdateComponent } from './rfp-event-update.component';
import { RfpEventDeletePopupComponent } from './rfp-event-delete-dialog.component';
import { IRfpEvent } from 'app/shared/model/rfp-event.model';

@Injectable({ providedIn: 'root' })
export class RfpEventResolve implements Resolve<IRfpEvent> {
    constructor(private service: RfpEventService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RfpEvent> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RfpEvent>) => response.ok),
                map((rfpEvent: HttpResponse<RfpEvent>) => rfpEvent.body)
            );
        }
        return of(new RfpEvent());
    }
}

export const rfpEventRoute: Routes = [
    {
        path: 'rfp-event',
        component: RfpEventComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'rfpApp.rfpEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-event/:id/view',
        component: RfpEventDetailComponent,
        resolve: {
            rfpEvent: RfpEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-event/new',
        component: RfpEventUpdateComponent,
        resolve: {
            rfpEvent: RfpEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-event/:id/edit',
        component: RfpEventUpdateComponent,
        resolve: {
            rfpEvent: RfpEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rfpEventPopupRoute: Routes = [
    {
        path: 'rfp-event/:id/delete',
        component: RfpEventDeletePopupComponent,
        resolve: {
            rfpEvent: RfpEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpEvent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
