import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRfpEvent } from 'app/shared/model/rfp-event.model';

type EntityResponseType = HttpResponse<IRfpEvent>;
type EntityArrayResponseType = HttpResponse<IRfpEvent[]>;

@Injectable({ providedIn: 'root' })
export class RfpEventService {
    public resourceUrl = SERVER_API_URL + 'api/rfp-events';

    constructor(protected http: HttpClient) {}

    create(rfpEvent: IRfpEvent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rfpEvent);
        return this.http
            .post<IRfpEvent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(rfpEvent: IRfpEvent): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(rfpEvent);
        return this.http
            .put<IRfpEvent>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRfpEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRfpEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(rfpEvent: IRfpEvent): IRfpEvent {
        const copy: IRfpEvent = Object.assign({}, rfpEvent, {
            eventDate: rfpEvent.eventDate != null && rfpEvent.eventDate.isValid() ? rfpEvent.eventDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.eventDate = res.body.eventDate != null ? moment(res.body.eventDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((rfpEvent: IRfpEvent) => {
                rfpEvent.eventDate = rfpEvent.eventDate != null ? moment(rfpEvent.eventDate) : null;
            });
        }
        return res;
    }
}
