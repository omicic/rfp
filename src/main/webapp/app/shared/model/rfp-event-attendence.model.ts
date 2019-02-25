import { IRfpUser } from 'app/shared/model//rfp-user.model';
import { IRfpEvent } from 'app/shared/model//rfp-event.model';

export interface IRfpEventAttendence {
    id?: number;
    attendenceDate?: string;
    rfpUser?: IRfpUser;
    rfpEvent?: IRfpEvent;
}

export class RfpEventAttendence implements IRfpEventAttendence {
    constructor(public id?: number, public attendenceDate?: string, public rfpUser?: IRfpUser, public rfpEvent?: IRfpEvent) {}
}
