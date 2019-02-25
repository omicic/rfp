/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RfpTestModule } from '../../../test.module';
import { RfpUserComponent } from 'app/entities/rfp-user/rfp-user.component';
import { RfpUserService } from 'app/entities/rfp-user/rfp-user.service';
import { RfpUser } from 'app/shared/model/rfp-user.model';

describe('Component Tests', () => {
    describe('RfpUser Management Component', () => {
        let comp: RfpUserComponent;
        let fixture: ComponentFixture<RfpUserComponent>;
        let service: RfpUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpUserComponent],
                providers: []
            })
                .overrideTemplate(RfpUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RfpUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RfpUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RfpUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.rfpUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
