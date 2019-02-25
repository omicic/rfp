/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RfpTestModule } from '../../../test.module';
import { RfpEventAttendenceUpdateComponent } from 'app/entities/rfp-event-attendence/rfp-event-attendence-update.component';
import { RfpEventAttendenceService } from 'app/entities/rfp-event-attendence/rfp-event-attendence.service';
import { RfpEventAttendence } from 'app/shared/model/rfp-event-attendence.model';

describe('Component Tests', () => {
    describe('RfpEventAttendence Management Update Component', () => {
        let comp: RfpEventAttendenceUpdateComponent;
        let fixture: ComponentFixture<RfpEventAttendenceUpdateComponent>;
        let service: RfpEventAttendenceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpEventAttendenceUpdateComponent]
            })
                .overrideTemplate(RfpEventAttendenceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RfpEventAttendenceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RfpEventAttendenceService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RfpEventAttendence(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rfpEventAttendence = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RfpEventAttendence();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rfpEventAttendence = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
