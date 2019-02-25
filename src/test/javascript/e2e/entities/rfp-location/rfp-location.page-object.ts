import { element, by, ElementFinder } from 'protractor';

export class RfpLocationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-rfp-location div table .btn-danger'));
    title = element.all(by.css('jhi-rfp-location div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class RfpLocationUpdatePage {
    pageTitle = element(by.id('jhi-rfp-location-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    locationNameInput = element(by.id('field_locationName'));
    runDayOfWeekInput = element(by.id('field_runDayOfWeek'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setLocationNameInput(locationName) {
        await this.locationNameInput.sendKeys(locationName);
    }

    async getLocationNameInput() {
        return this.locationNameInput.getAttribute('value');
    }

    async setRunDayOfWeekInput(runDayOfWeek) {
        await this.runDayOfWeekInput.sendKeys(runDayOfWeek);
    }

    async getRunDayOfWeekInput() {
        return this.runDayOfWeekInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class RfpLocationDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-rfpLocation-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-rfpLocation'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
