/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RfpTestModule } from '../../../test.module';
import { RfpEventDeleteDialogComponent } from 'app/entities/rfp-event/rfp-event-delete-dialog.component';
import { RfpEventService } from 'app/entities/rfp-event/rfp-event.service';

describe('Component Tests', () => {
    describe('RfpEvent Management Delete Component', () => {
        let comp: RfpEventDeleteDialogComponent;
        let fixture: ComponentFixture<RfpEventDeleteDialogComponent>;
        let service: RfpEventService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpEventDeleteDialogComponent]
            })
                .overrideTemplate(RfpEventDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RfpEventDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RfpEventService);
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
