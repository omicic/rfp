/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RfpTestModule } from '../../../test.module';
import { RfpEventAttendenceDeleteDialogComponent } from 'app/entities/rfp-event-attendence/rfp-event-attendence-delete-dialog.component';
import { RfpEventAttendenceService } from 'app/entities/rfp-event-attendence/rfp-event-attendence.service';

describe('Component Tests', () => {
    describe('RfpEventAttendence Management Delete Component', () => {
        let comp: RfpEventAttendenceDeleteDialogComponent;
        let fixture: ComponentFixture<RfpEventAttendenceDeleteDialogComponent>;
        let service: RfpEventAttendenceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpEventAttendenceDeleteDialogComponent]
            })
                .overrideTemplate(RfpEventAttendenceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RfpEventAttendenceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RfpEventAttendenceService);
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
