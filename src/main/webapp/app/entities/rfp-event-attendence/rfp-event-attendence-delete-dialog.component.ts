import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRfpEventAttendence } from 'app/shared/model/rfp-event-attendence.model';
import { RfpEventAttendenceService } from './rfp-event-attendence.service';

@Component({
    selector: 'jhi-rfp-event-attendence-delete-dialog',
    templateUrl: './rfp-event-attendence-delete-dialog.component.html'
})
export class RfpEventAttendenceDeleteDialogComponent {
    rfpEventAttendence: IRfpEventAttendence;

    constructor(
        protected rfpEventAttendenceService: RfpEventAttendenceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rfpEventAttendenceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rfpEventAttendenceListModification',
                content: 'Deleted an rfpEventAttendence'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rfp-event-attendence-delete-popup',
    template: ''
})
export class RfpEventAttendenceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rfpEventAttendence }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RfpEventAttendenceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.rfpEventAttendence = rfpEventAttendence;
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
