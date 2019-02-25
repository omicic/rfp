import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRfpUser } from 'app/shared/model/rfp-user.model';
import { AccountService } from 'app/core';
import { RfpUserService } from './rfp-user.service';

@Component({
    selector: 'jhi-rfp-user',
    templateUrl: './rfp-user.component.html'
})
export class RfpUserComponent implements OnInit, OnDestroy {
    rfpUsers: IRfpUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected rfpUserService: RfpUserService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.rfpUserService.query().subscribe(
            (res: HttpResponse<IRfpUser[]>) => {
                this.rfpUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRfpUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRfpUser) {
        return item.id;
    }

    registerChangeInRfpUsers() {
        this.eventSubscriber = this.eventManager.subscribe('rfpUserListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
