/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RfpTestModule } from '../../../test.module';
import { RfpEventDetailComponent } from 'app/entities/rfp-event/rfp-event-detail.component';
import { RfpEvent } from 'app/shared/model/rfp-event.model';

describe('Component Tests', () => {
    describe('RfpEvent Management Detail Component', () => {
        let comp: RfpEventDetailComponent;
        let fixture: ComponentFixture<RfpEventDetailComponent>;
        const route = ({ data: of({ rfpEvent: new RfpEvent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpEventDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RfpEventDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RfpEventDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rfpEvent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
