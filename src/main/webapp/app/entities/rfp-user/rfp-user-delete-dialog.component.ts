import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRfpUser } from 'app/shared/model/rfp-user.model';
import { RfpUserService } from './rfp-user.service';

@Component({
    selector: 'jhi-rfp-user-delete-dialog',
    templateUrl: './rfp-user-delete-dialog.component.html'
})
export class RfpUserDeleteDialogComponent {
    rfpUser: IRfpUser;

    constructor(protected rfpUserService: RfpUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rfpUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'rfpUserListModification',
                content: 'Deleted an rfpUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rfp-user-delete-popup',
    template: ''
})
export class RfpUserDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rfpUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RfpUserDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.rfpUser = rfpUser;
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
