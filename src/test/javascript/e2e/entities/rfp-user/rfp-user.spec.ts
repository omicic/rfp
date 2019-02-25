/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RfpUserComponentsPage, RfpUserDeleteDialog, RfpUserUpdatePage } from './rfp-user.page-object';

const expect = chai.expect;

describe('RfpUser e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let rfpUserUpdatePage: RfpUserUpdatePage;
    let rfpUserComponentsPage: RfpUserComponentsPage;
    let rfpUserDeleteDialog: RfpUserDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load RfpUsers', async () => {
        await navBarPage.goToEntity('rfp-user');
        rfpUserComponentsPage = new RfpUserComponentsPage();
        expect(await rfpUserComponentsPage.getTitle()).to.eq('rfpApp.rfpUser.home.title');
    });

    it('should load create RfpUser page', async () => {
        await rfpUserComponentsPage.clickOnCreateButton();
        rfpUserUpdatePage = new RfpUserUpdatePage();
        expect(await rfpUserUpdatePage.getPageTitle()).to.eq('rfpApp.rfpUser.home.createOrEditLabel');
        await rfpUserUpdatePage.cancel();
    });

    it('should create and save RfpUsers', async () => {
        const nbButtonsBeforeCreate = await rfpUserComponentsPage.countDeleteButtons();

        await rfpUserComponentsPage.clickOnCreateButton();
        await promise.all([rfpUserUpdatePage.setUserNameInput('userName'), rfpUserUpdatePage.locationSelectLastOption()]);
        expect(await rfpUserUpdatePage.getUserNameInput()).to.eq('userName');
        await rfpUserUpdatePage.save();
        expect(await rfpUserUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await rfpUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last RfpUser', async () => {
        const nbButtonsBeforeDelete = await rfpUserComponentsPage.countDeleteButtons();
        await rfpUserComponentsPage.clickOnLastDeleteButton();

        rfpUserDeleteDialog = new RfpUserDeleteDialog();
        expect(await rfpUserDeleteDialog.getDialogTitle()).to.eq('rfpApp.rfpUser.delete.question');
        await rfpUserDeleteDialog.clickOnConfirmButton();

        expect(await rfpUserComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
