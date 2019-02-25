import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RfpUser } from 'app/shared/model/rfp-user.model';
import { RfpUserService } from './rfp-user.service';
import { RfpUserComponent } from './rfp-user.component';
import { RfpUserDetailComponent } from './rfp-user-detail.component';
import { RfpUserUpdateComponent } from './rfp-user-update.component';
import { RfpUserDeletePopupComponent } from './rfp-user-delete-dialog.component';
import { IRfpUser } from 'app/shared/model/rfp-user.model';

@Injectable({ providedIn: 'root' })
export class RfpUserResolve implements Resolve<IRfpUser> {
    constructor(private service: RfpUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RfpUser> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RfpUser>) => response.ok),
                map((rfpUser: HttpResponse<RfpUser>) => rfpUser.body)
            );
        }
        return of(new RfpUser());
    }
}

export const rfpUserRoute: Routes = [
    {
        path: 'rfp-user',
        component: RfpUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-user/:id/view',
        component: RfpUserDetailComponent,
        resolve: {
            rfpUser: RfpUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-user/new',
        component: RfpUserUpdateComponent,
        resolve: {
            rfpUser: RfpUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'rfp-user/:id/edit',
        component: RfpUserUpdateComponent,
        resolve: {
            rfpUser: RfpUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rfpUserPopupRoute: Routes = [
    {
        path: 'rfp-user/:id/delete',
        component: RfpUserDeletePopupComponent,
        resolve: {
            rfpUser: RfpUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'rfpApp.rfpUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
