package com.wp.common;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by WangPing on 2018/1/17.
 */

public interface LoginService {

    @POST("getAllCustOpenOrg.spring")
    Observable<String> getCustomerOrg();
}
