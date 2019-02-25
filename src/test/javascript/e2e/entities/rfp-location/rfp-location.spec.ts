/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RfpLocationComponentsPage, RfpLocationDeleteDialog, RfpLocationUpdatePage } from './rfp-location.page-object';

const expect = chai.expect;

describe('RfpLocation e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let rfpLocationUpdatePage: RfpLocationUpdatePage;
    let rfpLocationComponentsPage: RfpLocationComponentsPage;
    let rfpLocationDeleteDialog: RfpLocationDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load RfpLocations', async () => {
        await navBarPage.goToEntity('rfp-location');
        rfpLocationComponentsPage = new RfpLocationComponentsPage();
        expect(await rfpLocationComponentsPage.getTitle()).to.eq('rfpApp.rfpLocation.home.title');
    });

    it('should load create RfpLocation page', async () => {
        await rfpLocationComponentsPage.clickOnCreateButton();
        rfpLocationUpdatePage = new RfpLocationUpdatePage();
        expect(await rfpLocationUpdatePage.getPageTitle()).to.eq('rfpApp.rfpLocation.home.createOrEditLabel');
        await rfpLocationUpdatePage.cancel();
    });

    it('should create and save RfpLocations', async () => {
        const nbButtonsBeforeCreate = await rfpLocationComponentsPage.countDeleteButtons();

        await rfpLocationComponentsPage.clickOnCreateButton();
        await promise.all([rfpLocationUpdatePage.setLocationNameInput('locationName'), rfpLocationUpdatePage.setRunDayOfWeekInput('5')]);
        expect(await rfpLocationUpdatePage.getLocationNameInput()).to.eq('locationName');
        expect(await rfpLocationUpdatePage.getRunDayOfWeekInput()).to.eq('5');
        await rfpLocationUpdatePage.save();
        expect(await rfpLocationUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await rfpLocationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last RfpLocation', async () => {
        const nbButtonsBeforeDelete = await rfpLocationComponentsPage.countDeleteButtons();
        await rfpLocationComponentsPage.clickOnLastDeleteButton();

        rfpLocationDeleteDialog = new RfpLocationDeleteDialog();
        expect(await rfpLocationDeleteDialog.getDialogTitle()).to.eq('rfpApp.rfpLocation.delete.question');
        await rfpLocationDeleteDialog.clickOnConfirmButton();

        expect(await rfpLocationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
