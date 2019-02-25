/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RfpTestModule } from '../../../test.module';
import { RfpEventUpdateComponent } from 'app/entities/rfp-event/rfp-event-update.component';
import { RfpEventService } from 'app/entities/rfp-event/rfp-event.service';
import { RfpEvent } from 'app/shared/model/rfp-event.model';

describe('Component Tests', () => {
    describe('RfpEvent Management Update Component', () => {
        let comp: RfpEventUpdateComponent;
        let fixture: ComponentFixture<RfpEventUpdateComponent>;
        let service: RfpEventService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpEventUpdateComponent]
            })
                .overrideTemplate(RfpEventUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RfpEventUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RfpEventService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RfpEvent(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rfpEvent = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RfpEvent();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rfpEvent = entity;
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
