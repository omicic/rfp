import { IRfpLocation } from 'app/shared/model//rfp-location.model';
import { IRfpEventAttendence } from 'app/shared/model//rfp-event-attendence.model';

export interface IRfpUser {
    id?: number;
    userName?: string;
    location?: IRfpLocation;
    rfpEventAttendances?: IRfpEventAttendence[];
}

export class RfpUser implements IRfpUser {
    constructor(
        public id?: number,
        public userName?: string,
        public location?: IRfpLocation,
        public rfpEventAttendances?: IRfpEventAttendence[]
    ) {}
}
