import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRfpLocation } from 'app/shared/model/rfp-location.model';

@Component({
    selector: 'jhi-rfp-location-detail',
    templateUrl: './rfp-location-detail.component.html'
})
export class RfpLocationDetailComponent implements OnInit {
    rfpLocation: IRfpLocation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rfpLocation }) => {
            this.rfpLocation = rfpLocation;
        });
    }

    previousState() {
        window.history.back();
    }
}
