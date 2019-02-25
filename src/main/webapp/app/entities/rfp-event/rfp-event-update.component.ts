import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IRfpEvent } from 'app/shared/model/rfp-event.model';
import { RfpEventService } from './rfp-event.service';
import { IRfpLocation } from 'app/shared/model/rfp-location.model';
import { RfpLocationService } from 'app/entities/rfp-location';

@Component({
    selector: 'jhi-rfp-event-update',
    templateUrl: './rfp-event-update.component.html'
})
export class RfpEventUpdateComponent implements OnInit {
    rfpEvent: IRfpEvent;
    isSaving: boolean;

    rfplocations: IRfpLocation[];
    eventDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rfpEventService: RfpEventService,
        protected rfpLocationService: RfpLocationService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rfpEvent }) => {
            this.rfpEvent = rfpEvent;
        });
        this.rfpLocationService.query().subscribe(
            (res: HttpResponse<IRfpLocation[]>) => {
                this.rfplocations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rfpEvent.id !== undefined) {
            this.subscribeToSaveResponse(this.rfpEventService.update(this.rfpEvent));
        } else {
            this.subscribeToSaveResponse(this.rfpEventService.create(this.rfpEvent));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRfpEvent>>) {
        result.subscribe((res: HttpResponse<IRfpEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
