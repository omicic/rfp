import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRfpEvent } from 'app/shared/model/rfp-event.model';

@Component({
    selector: 'jhi-rfp-event-detail',
    templateUrl: './rfp-event-detail.component.html'
})
export class RfpEventDetailComponent implements OnInit {
    rfpEvent: IRfpEvent;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rfpEvent }) => {
            this.rfpEvent = rfpEvent;
        });
    }

    previousState() {
        window.history.back();
    }
}
