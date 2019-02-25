import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRfpEventAttendence } from 'app/shared/model/rfp-event-attendence.model';

type EntityResponseType = HttpResponse<IRfpEventAttendence>;
type EntityArrayResponseType = HttpResponse<IRfpEventAttendence[]>;

@Injectable({ providedIn: 'root' })
export class RfpEventAttendenceService {
    public resourceUrl = SERVER_API_URL + 'api/rfp-event-attendences';

    constructor(protected http: HttpClient) {}

    create(rfpEventAttendence: IRfpEventAttendence): Observable<EntityResponseType> {
        return this.http.post<IRfpEventAttendence>(this.resourceUrl, rfpEventAttendence, { observe: 'response' });
    }

    update(rfpEventAttendence: IRfpEventAttendence): Observable<EntityResponseType> {
        return this.http.put<IRfpEventAttendence>(this.resourceUrl, rfpEventAttendence, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRfpEventAttendence>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRfpEventAttendence[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
