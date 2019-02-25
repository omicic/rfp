import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RfpLocation } from 'app/shared/model/rfp-location.model';
import { RfpLocationService } from './rfp-location.service';
import { RfpLocationComponent } from './rfp-location.component';
import { RfpLocationDetailComponent } from './rfp-location-detail.component';
import { RfpLocationUpdateComponent } from './rfp-location-update.component';
import { RfpLocationDeletePopupComponent } from './rfp-location-delete-dialog.component';
import { IRfpLocation } from 'app/shared/model/rfp-location.model';

@Injectable({ providedIn: 'root' })
export class RfpLocationResolve implements Resolve<IRfpLocation> {
    constructor(private service: RfpLocationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RfpLocation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RfpLocation>) => response.ok),
                map((rfpLocation: HttpResponse<RfpLocation>) => rfpLocation.body)
            );
        }
        return of(new RfpLocation());
    }
}

export const rfpLocationRoute: Routes = [
    {
        path: 'rfp-location',
        component: RfpLocationComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'rfpApp.rfpLocation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-location/:id/view',
        component: RfpLocationDetailComponent,
        resolve: {
            rfpLocation: RfpLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpLocation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-location/new',
        component: RfpLocationUpdateComponent,
        resolve: {
            rfpLocation: RfpLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpLocation.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-location/:id/edit',
        component: RfpLocationUpdateComponent,
        resolve: {
            rfpLocation: RfpLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpLocation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rfpLocationPopupRoute: Routes = [
    {
        path: 'rfp-location/:id/delete',
        component: RfpLocationDeletePopupComponent,
        resolve: {
            rfpLocation: RfpLocationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpLocation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
