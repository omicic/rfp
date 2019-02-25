import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRfpUser } from 'app/shared/model/rfp-user.model';

type EntityResponseType = HttpResponse<IRfpUser>;
type EntityArrayResponseType = HttpResponse<IRfpUser[]>;

@Injectable({ providedIn: 'root' })
export class RfpUserService {
    public resourceUrl = SERVER_API_URL + 'api/rfp-users';

    constructor(protected http: HttpClient) {}

    create(rfpUser: IRfpUser): Observable<EntityResponseType> {
        return this.http.post<IRfpUser>(this.resourceUrl, rfpUser, { observe: 'response' });
    }

    update(rfpUser: IRfpUser): Observable<EntityResponseType> {
        return this.http.put<IRfpUser>(this.resourceUrl, rfpUser, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRfpUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRfpUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
