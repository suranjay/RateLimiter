package com.blueoptima.controller;

import com.blueoptima.bean.InputDetail;
import com.blueoptima.common.ApplicationException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Suranjay on 12/07/18.
 */
public class UserUrlCreator {

    private static String BASE_URL = "https://api.github.com/search/users?q=";
    private static String NAME_SUFFIX = "\"%s\"+in:fullname";
    private static String LOCATION_SUFFIX = "location:\"%s\"";
    private static String PAGE_SUFFIX = "&per_page=100";

    public static String createUserSearchUrl(InputDetail inputDetail) throws ApplicationException {

        StringBuffer sb = new StringBuffer(BASE_URL);
        if (inputDetail.getFirstName() != null || inputDetail.getLastName() != null) {
            try {
                sb.append(URLEncoder.encode(inputDetail.getFirstName() + " " + inputDetail.getLastName(), "UTF-8")).append("+in:fullname");
            } catch (UnsupportedEncodingException e) {
                throw new ApplicationException("Error while encoding name suffix", e);
            }
        }

        if (inputDetail.getLastName() != null) {
            try {
                sb.append("+").append(URLEncoder.encode(String.format(LOCATION_SUFFIX, inputDetail.getLocation()),  "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new ApplicationException("Error while encoding location suffix", e);
            }
        }

        sb.append(PAGE_SUFFIX);
        return sb.toString();

    }
}
