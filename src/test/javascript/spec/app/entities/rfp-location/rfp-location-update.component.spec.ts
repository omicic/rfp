/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RfpTestModule } from '../../../test.module';
import { RfpLocationUpdateComponent } from 'app/entities/rfp-location/rfp-location-update.component';
import { RfpLocationService } from 'app/entities/rfp-location/rfp-location.service';
import { RfpLocation } from 'app/shared/model/rfp-location.model';

describe('Component Tests', () => {
    describe('RfpLocation Management Update Component', () => {
        let comp: RfpLocationUpdateComponent;
        let fixture: ComponentFixture<RfpLocationUpdateComponent>;
        let service: RfpLocationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpLocationUpdateComponent]
            })
                .overrideTemplate(RfpLocationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RfpLocationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RfpLocationService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RfpLocation(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rfpLocation = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RfpLocation();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rfpLocation = entity;
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
