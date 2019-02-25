import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRfpLocation } from 'app/shared/model/rfp-location.model';

type EntityResponseType = HttpResponse<IRfpLocation>;
type EntityArrayResponseType = HttpResponse<IRfpLocation[]>;

@Injectable({ providedIn: 'root' })
export class RfpLocationService {
    public resourceUrl = SERVER_API_URL + 'api/rfp-locations';

    constructor(protected http: HttpClient) {}

    create(rfpLocation: IRfpLocation): Observable<EntityResponseType> {
        return this.http.post<IRfpLocation>(this.resourceUrl, rfpLocation, { observe: 'response' });
    }

    update(rfpLocation: IRfpLocation): Observable<EntityResponseType> {
        return this.http.put<IRfpLocation>(this.resourceUrl, rfpLocation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRfpLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRfpLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
