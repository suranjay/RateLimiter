package com.blueoptima.controller;

import com.blueoptima.bean.HttpRequest;
import com.blueoptima.bean.HttpResponse;
import com.blueoptima.common.ApplicationException;
import com.blueoptima.util.HttpUrlDataFetcher;

import java.util.Date;

/**
 * Created by Suranjay on 11/07/18.
 */
public class DataFetcherWithRateLimit {

    private static DataFetcherWithRateLimit INSTANCE = null;

    public static DataFetcherWithRateLimit getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataFetcherWithRateLimit();
        }
        return INSTANCE;
    }

    private DataFetcherWithRateLimit() {
    }

    //TODO make singleton
    private Integer limitRemaining = 1;
    private long limitReset = 0L;

    public HttpResponse fetchDataFromUrl(HttpRequest request) throws ApplicationException {
        HttpResponse response = null;
        boolean flag = false;

        if (limitRemaining > 0 ) {

            flag = true;

        } else {
                while (System.currentTimeMillis()/1000<limitReset) {
//                    System.out.print(".");
                }
                flag = true;
        }
        if (flag) {
            response = HttpUrlDataFetcher.getDataFromURL(request);
            if (response != null && response.getHeaderMap() != null) {
                limitRemaining = Integer.valueOf(response.getHeaderMap().get("X-RateLimit-Remaining").get(0));
                limitReset = Long.valueOf(response.getHeaderMap().get("X-RateLimit-Reset").get(0));
            }
        }
        return response;
    }
}
