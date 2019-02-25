import { element, by, ElementFinder } from 'protractor';

export class RfpEventComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-rfp-event div table .btn-danger'));
    title = element.all(by.css('jhi-rfp-event div h2#page-heading span')).first();

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

export class RfpEventUpdatePage {
    pageTitle = element(by.id('jhi-rfp-event-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    eventDateInput = element(by.id('field_eventDate'));
    eventCodeInput = element(by.id('field_eventCode'));
    rfpLocationSelect = element(by.id('field_rfpLocation'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setEventDateInput(eventDate) {
        await this.eventDateInput.sendKeys(eventDate);
    }

    async getEventDateInput() {
        return this.eventDateInput.getAttribute('value');
    }

    async setEventCodeInput(eventCode) {
        await this.eventCodeInput.sendKeys(eventCode);
    }

    async getEventCodeInput() {
        return this.eventCodeInput.getAttribute('value');
    }

    async rfpLocationSelectLastOption() {
        await this.rfpLocationSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async rfpLocationSelectOption(option) {
        await this.rfpLocationSelect.sendKeys(option);
    }

    getRfpLocationSelect(): ElementFinder {
        return this.rfpLocationSelect;
    }

    async getRfpLocationSelectedOption() {
        return this.rfpLocationSelect.element(by.css('option:checked')).getText();
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

export class RfpEventDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-rfpEvent-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-rfpEvent'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
