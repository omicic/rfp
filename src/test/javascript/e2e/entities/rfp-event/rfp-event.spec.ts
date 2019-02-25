/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RfpEventComponentsPage, RfpEventDeleteDialog, RfpEventUpdatePage } from './rfp-event.page-object';

const expect = chai.expect;

describe('RfpEvent e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let rfpEventUpdatePage: RfpEventUpdatePage;
    let rfpEventComponentsPage: RfpEventComponentsPage;
    let rfpEventDeleteDialog: RfpEventDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load RfpEvents', async () => {
        await navBarPage.goToEntity('rfp-event');
        rfpEventComponentsPage = new RfpEventComponentsPage();
        expect(await rfpEventComponentsPage.getTitle()).to.eq('rfpApp.rfpEvent.home.title');
    });

    it('should load create RfpEvent page', async () => {
        await rfpEventComponentsPage.clickOnCreateButton();
        rfpEventUpdatePage = new RfpEventUpdatePage();
        expect(await rfpEventUpdatePage.getPageTitle()).to.eq('rfpApp.rfpEvent.home.createOrEditLabel');
        await rfpEventUpdatePage.cancel();
    });

    it('should create and save RfpEvents', async () => {
        const nbButtonsBeforeCreate = await rfpEventComponentsPage.countDeleteButtons();

        await rfpEventComponentsPage.clickOnCreateButton();
        await promise.all([
            rfpEventUpdatePage.setEventDateInput('2000-12-31'),
            rfpEventUpdatePage.setEventCodeInput('eventCode'),
            rfpEventUpdatePage.rfpLocationSelectLastOption()
        ]);
        expect(await rfpEventUpdatePage.getEventDateInput()).to.eq('2000-12-31');
        expect(await rfpEventUpdatePage.getEventCodeInput()).to.eq('eventCode');
        await rfpEventUpdatePage.save();
        expect(await rfpEventUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await rfpEventComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last RfpEvent', async () => {
        const nbButtonsBeforeDelete = await rfpEventComponentsPage.countDeleteButtons();
        await rfpEventComponentsPage.clickOnLastDeleteButton();

        rfpEventDeleteDialog = new RfpEventDeleteDialog();
        expect(await rfpEventDeleteDialog.getDialogTitle()).to.eq('rfpApp.rfpEvent.delete.question');
        await rfpEventDeleteDialog.clickOnConfirmButton();

        expect(await rfpEventComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
