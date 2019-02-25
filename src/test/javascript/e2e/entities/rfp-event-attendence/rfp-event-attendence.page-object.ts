import { element, by, ElementFinder } from 'protractor';

export class RfpEventAttendenceComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-rfp-event-attendence div table .btn-danger'));
    title = element.all(by.css('jhi-rfp-event-attendence div h2#page-heading span')).first();

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

export class RfpEventAttendenceUpdatePage {
    pageTitle = element(by.id('jhi-rfp-event-attendence-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    attendenceDateInput = element(by.id('field_attendenceDate'));
    rfpUserSelect = element(by.id('field_rfpUser'));
    rfpEventSelect = element(by.id('field_rfpEvent'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAttendenceDateInput(attendenceDate) {
        await this.attendenceDateInput.sendKeys(attendenceDate);
    }

    async getAttendenceDateInput() {
        return this.attendenceDateInput.getAttribute('value');
    }

    async rfpUserSelectLastOption() {
        await this.rfpUserSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async rfpUserSelectOption(option) {
        await this.rfpUserSelect.sendKeys(option);
    }

    getRfpUserSelect(): ElementFinder {
        return this.rfpUserSelect;
    }

    async getRfpUserSelectedOption() {
        return this.rfpUserSelect.element(by.css('option:checked')).getText();
    }

    async rfpEventSelectLastOption() {
        await this.rfpEventSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async rfpEventSelectOption(option) {
        await this.rfpEventSelect.sendKeys(option);
    }

    getRfpEventSelect(): ElementFinder {
        return this.rfpEventSelect;
    }

    async getRfpEventSelectedOption() {
        return this.rfpEventSelect.element(by.css('option:checked')).getText();
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

export class RfpEventAttendenceDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-rfpEventAttendence-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-rfpEventAttendence'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
