import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRfpEventAttendence } from 'app/shared/model/rfp-event-attendence.model';

@Component({
    selector: 'jhi-rfp-event-attendence-detail',
    templateUrl: './rfp-event-attendence-detail.component.html'
})
export class RfpEventAttendenceDetailComponent implements OnInit {
    rfpEventAttendence: IRfpEventAttendence;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rfpEventAttendence }) => {
            this.rfpEventAttendence = rfpEventAttendence;
        });
    }

    previousState() {
        window.history.back();
    }
}
