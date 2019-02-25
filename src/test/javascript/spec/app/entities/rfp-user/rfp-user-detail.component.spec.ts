/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RfpTestModule } from '../../../test.module';
import { RfpUserDetailComponent } from 'app/entities/rfp-user/rfp-user-detail.component';
import { RfpUser } from 'app/shared/model/rfp-user.model';

describe('Component Tests', () => {
    describe('RfpUser Management Detail Component', () => {
        let comp: RfpUserDetailComponent;
        let fixture: ComponentFixture<RfpUserDetailComponent>;
        const route = ({ data: of({ rfpUser: new RfpUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RfpTestModule],
                declarations: [RfpUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RfpUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RfpUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rfpUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
