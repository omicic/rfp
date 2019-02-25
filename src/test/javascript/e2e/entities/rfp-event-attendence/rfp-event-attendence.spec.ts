/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    RfpEventAttendenceComponentsPage,
    RfpEventAttendenceDeleteDialog,
    RfpEventAttendenceUpdatePage
} from './rfp-event-attendence.page-object';

const expect = chai.expect;

describe('RfpEventAttendence e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let rfpEventAttendenceUpdatePage: RfpEventAttendenceUpdatePage;
    let rfpEventAttendenceComponentsPage: RfpEventAttendenceComponentsPage;
    let rfpEventAttendenceDeleteDialog: RfpEventAttendenceDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load RfpEventAttendences', async () => {
        await navBarPage.goToEntity('rfp-event-attendence');
        rfpEventAttendenceComponentsPage = new RfpEventAttendenceComponentsPage();
        expect(await rfpEventAttendenceComponentsPage.getTitle()).to.eq('rfpApp.rfpEventAttendence.home.title');
    });

    it('should load create RfpEventAttendence page', async () => {
        await rfpEventAttendenceComponentsPage.clickOnCreateButton();
        rfpEventAttendenceUpdatePage = new RfpEventAttendenceUpdatePage();
        expect(await rfpEventAttendenceUpdatePage.getPageTitle()).to.eq('rfpApp.rfpEventAttendence.home.createOrEditLabel');
        await rfpEventAttendenceUpdatePage.cancel();
    });

    it('should create and save RfpEventAttendences', async () => {
        const nbButtonsBeforeCreate = await rfpEventAttendenceComponentsPage.countDeleteButtons();

        await rfpEventAttendenceComponentsPage.clickOnCreateButton();
        await promise.all([
            rfpEventAttendenceUpdatePage.setAttendenceDateInput('attendenceDate'),
            rfpEventAttendenceUpdatePage.rfpUserSelectLastOption(),
            rfpEventAttendenceUpdatePage.rfpEventSelectLastOption()
        ]);
        expect(await rfpEventAttendenceUpdatePage.getAttendenceDateInput()).to.eq('attendenceDate');
        await rfpEventAttendenceUpdatePage.save();
        expect(await rfpEventAttendenceUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await rfpEventAttendenceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last RfpEventAttendence', async () => {
        const nbButtonsBeforeDelete = await rfpEventAttendenceComponentsPage.countDeleteButtons();
        await rfpEventAttendenceComponentsPage.clickOnLastDeleteButton();

        rfpEventAttendenceDeleteDialog = new RfpEventAttendenceDeleteDialog();
        expect(await rfpEventAttendenceDeleteDialog.getDialogTitle()).to.eq('rfpApp.rfpEventAttendence.delete.question');
        await rfpEventAttendenceDeleteDialog.clickOnConfirmButton();

        expect(await rfpEventAttendenceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
