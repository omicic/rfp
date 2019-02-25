import { Moment } from 'moment';
import { IRfpEventAttendence } from 'app/shared/model//rfp-event-attendence.model';
import { IRfpLocation } from 'app/shared/model//rfp-location.model';

export interface IRfpEvent {
    id?: number;
    eventDate?: Moment;
    eventCode?: string;
    rfpEventAttendances?: IRfpEventAttendence[];
    rfpLocation?: IRfpLocation;
}

export class RfpEvent implements IRfpEvent {
    constructor(
        public id?: number,
        public eventDate?: Moment,
        public eventCode?: string,
        public rfpEventAttendances?: IRfpEventAttendence[],
        public rfpLocation?: IRfpLocation
    ) {}
}
