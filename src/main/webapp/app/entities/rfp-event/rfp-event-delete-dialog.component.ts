import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRfpEvent } from 'app/shared/model/rfp-event.model';
import { RfpEventService } from './rfp-event.service';

@Component({
    selector: 'jhi-rfp-event-delete-dialog',
    templateUrl: './rfp-event-delete-dialog.component.html'
})
export class RfpEventDeleteDialogComponent {
    rfpEvent: IRfpEvent;

    constructor(protected rfpEventService: RfpEventService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rfpEventService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rfpEventListModification',
                content: 'Deleted an rfpEvent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rfp-event-delete-popup',
    template: ''
})
export class RfpEventDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rfpEvent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RfpEventDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.rfpEvent = rfpEvent;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
