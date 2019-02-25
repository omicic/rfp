import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRfpUser } from 'app/shared/model/rfp-user.model';

@Component({
    selector: 'jhi-rfp-user-detail',
    templateUrl: './rfp-user-detail.component.html'
})
export class RfpUserDetailComponent implements OnInit {
    rfpUser: IRfpUser;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rfpUser }) => {
            this.rfpUser = rfpUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
