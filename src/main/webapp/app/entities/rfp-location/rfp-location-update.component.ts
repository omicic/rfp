import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRfpLocation } from 'app/shared/model/rfp-location.model';
import { RfpLocationService } from './rfp-location.service';

@Component({
    selector: 'jhi-rfp-location-update',
    templateUrl: './rfp-location-update.component.html'
})
export class RfpLocationUpdateComponent implements OnInit {
    rfpLocation: IRfpLocation;
    isSaving: boolean;

    constructor(protected rfpLocationService: RfpLocationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rfpLocation }) => {
            this.rfpLocation = rfpLocation;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rfpLocation.id !== undefined) {
            this.subscribeToSaveResponse(this.rfpLocationService.update(this.rfpLocation));
        } else {
            this.subscribeToSaveResponse(this.rfpLocationService.create(this.rfpLocation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRfpLocation>>) {
        result.subscribe((res: HttpResponse<IRfpLocation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
