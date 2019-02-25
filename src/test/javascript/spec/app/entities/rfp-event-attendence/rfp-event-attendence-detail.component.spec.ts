/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RfpTestModule } from '../../../test.module';
import { RfpEventAttendenceDetailComponent } from 'app/entities/rfp-event-attendence/rfp-event-attendence-detail.component';
import { RfpEventAttendence } from 'app/shared/model/rfp-event-attendence.model';

describe('Component Tests', () => {
    describe('RfpEventAttendence Management Detail Component', () => {
        let comp: RfpEventAttendenceDetailComponent;
        let fixture: ComponentFixture<RfpEventAttendenceDetailComponent>;
        const route = ({ data: of({ rfpEventAttendence: new RfpEventAttendence(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpEventAttendenceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RfpEventAttendenceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RfpEventAttendenceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rfpEventAttendence).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
