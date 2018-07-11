package com.blueoptima.bean;

import java.util.Map;

/**
 * Created by Suranjay on 11/07/18.
 */
public class HttpRequest {
    private String urlString;
    private Map<String, String> headerMap;
    private MethodType methodType;

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public MethodType getMethodType() {
        return methodType;
    }

    public void setMethodType(MethodType methodType) {
        this.methodType = methodType;
    }

    @Override
    public String toString() {
        return "Request{" +
                "urlString='" + urlString + '\'' +
                ", headerMap=" + headerMap +
                ", methodType=" + methodType +
                '}';
    }
}
