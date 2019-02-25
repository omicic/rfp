/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RfpTestModule } from '../../../test.module';
import { RfpLocationDetailComponent } from 'app/entities/rfp-location/rfp-location-detail.component';
import { RfpLocation } from 'app/shared/model/rfp-location.model';

describe('Component Tests', () => {
    describe('RfpLocation Management Detail Component', () => {
        let comp: RfpLocationDetailComponent;
        let fixture: ComponentFixture<RfpLocationDetailComponent>;
        const route = ({ data: of({ rfpLocation: new RfpLocation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpLocationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RfpLocationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RfpLocationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rfpLocation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
