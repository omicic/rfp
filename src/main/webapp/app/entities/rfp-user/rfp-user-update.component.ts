import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRfpUser } from 'app/shared/model/rfp-user.model';
import { RfpUserService } from './rfp-user.service';
import { IRfpLocation } from 'app/shared/model/rfp-location.model';
import { RfpLocationService } from 'app/entities/rfp-location';

@Component({
    selector: 'jhi-rfp-user-update',
    templateUrl: './rfp-user-update.component.html'
})
export class RfpUserUpdateComponent implements OnInit {
    rfpUser: IRfpUser;
    isSaving: boolean;

    locations: IRfpLocation[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rfpUserService: RfpUserService,
        protected rfpLocationService: RfpLocationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rfpUser }) => {
            this.rfpUser = rfpUser;
        });
        this.rfpLocationService.query({ filter: 'rfpuser-is-null' }).subscribe(
            (res: HttpResponse<IRfpLocation[]>) => {
                if (!this.rfpUser.location || !this.rfpUser.location.id) {
                    this.locations = res.body;
                } else {
                    this.rfpLocationService.find(this.rfpUser.location.id).subscribe(
                        (subRes: HttpResponse<IRfpLocation>) => {
                            this.locations = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rfpUser.id !== undefined) {
            this.subscribeToSaveResponse(this.rfpUserService.update(this.rfpUser));
        } else {
            this.subscribeToSaveResponse(this.rfpUserService.create(this.rfpUser));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRfpUser>>) {
        result.subscribe((res: HttpResponse<IRfpUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRfpLocationById(index: number, item: IRfpLocation) {
        return item.id;
    }
}
