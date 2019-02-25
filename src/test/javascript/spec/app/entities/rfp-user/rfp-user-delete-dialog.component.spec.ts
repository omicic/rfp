/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RfpTestModule } from '../../../test.module';
import { RfpUserDeleteDialogComponent } from 'app/entities/rfp-user/rfp-user-delete-dialog.component';
import { RfpUserService } from 'app/entities/rfp-user/rfp-user.service';

describe('Component Tests', () => {
    describe('RfpUser Management Delete Component', () => {
        let comp: RfpUserDeleteDialogComponent;
        let fixture: ComponentFixture<RfpUserDeleteDialogComponent>;
        let service: RfpUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpUserDeleteDialogComponent]
            })
                .overrideTemplate(RfpUserDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RfpUserDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RfpUserService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
