/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RfpTestModule } from '../../../test.module';
import { RfpUserUpdateComponent } from 'app/entities/rfp-user/rfp-user-update.component';
import { RfpUserService } from 'app/entities/rfp-user/rfp-user.service';
import { RfpUser } from 'app/shared/model/rfp-user.model';

describe('Component Tests', () => {
    describe('RfpUser Management Update Component', () => {
        let comp: RfpUserUpdateComponent;
        let fixture: ComponentFixture<RfpUserUpdateComponent>;
        let service: RfpUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpUserUpdateComponent]
            })
                .overrideTemplate(RfpUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RfpUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RfpUserService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RfpUser(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rfpUser = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RfpUser();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.rfpUser = entity;
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
