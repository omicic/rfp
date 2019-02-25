import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRfpEventAttendence } from 'app/shared/model/rfp-event-attendence.model';
import { RfpEventAttendenceService } from './rfp-event-attendence.service';
import { IRfpUser } from 'app/shared/model/rfp-user.model';
import { RfpUserService } from 'app/entities/rfp-user';
import { IRfpEvent } from 'app/shared/model/rfp-event.model';
import { RfpEventService } from 'app/entities/rfp-event';

@Component({
    selector: 'jhi-rfp-event-attendence-update',
    templateUrl: './rfp-event-attendence-update.component.html'
})
export class RfpEventAttendenceUpdateComponent implements OnInit {
    rfpEventAttendence: IRfpEventAttendence;
    isSaving: boolean;

    rfpusers: IRfpUser[];

    rfpevents: IRfpEvent[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected rfpEventAttendenceService: RfpEventAttendenceService,
        protected rfpUserService: RfpUserService,
        protected rfpEventService: RfpEventService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rfpEventAttendence }) => {
            this.rfpEventAttendence = rfpEventAttendence;
        });
        this.rfpUserService.query().subscribe(
            (res: HttpResponse<IRfpUser[]>) => {
                this.rfpusers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.rfpEventService.query().subscribe(
            (res: HttpResponse<IRfpEvent[]>) => {
                this.rfpevents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rfpEventAttendence.id !== undefined) {
            this.subscribeToSaveResponse(this.rfpEventAttendenceService.update(this.rfpEventAttendence));
        } else {
            this.subscribeToSaveResponse(this.rfpEventAttendenceService.create(this.rfpEventAttendence));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRfpEventAttendence>>) {
        result.subscribe((res: HttpResponse<IRfpEventAttendence>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRfpUserById(index: number, item: IRfpUser) {
        return item.id;
    }

    trackRfpEventById(index: number, item: IRfpEvent) {
        return item.id;
    }
}
