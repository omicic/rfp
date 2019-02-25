import { IRfpEvent } from 'app/shared/model//rfp-event.model';

export interface IRfpLocation {
    id?: number;
    locationName?: string;
    runDayOfWeek?: number;
    rfpEvents?: IRfpEvent[];
}

export class RfpLocation implements IRfpLocation {
    constructor(public id?: number, public locationName?: string, public runDayOfWeek?: number, public rfpEvents?: IRfpEvent[]) {}
}
