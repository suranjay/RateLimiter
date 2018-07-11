package com.blueoptima.controller;

import com.blueoptima.bean.HttpRequest;
import com.blueoptima.bean.HttpResponse;
import com.blueoptima.bean.MethodType;
import com.blueoptima.common.ApplicationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Suranjay on 11/07/18.
 */
public abstract class AbstractDataFetcher<T> {

    protected T data;

    public T getData(HttpRequest request) throws ApplicationException {
        System.out.println("Fetching for:"+request.getUrlString());
        HttpResponse response = DataFetcherWithRateLimit.getInstance().fetchDataFromUrl(request);
        parseResponse(response);
        Map<String, List<String>> headerMap = response.getHeaderMap();
        String nextLink = null;
        if (headerMap != null) {
            List<String> links = headerMap.get("Link");
            if (links != null) {
                for (String link : links) {
                    if (link.contains("; rel=\"next\"")) {
                        int endIndex = link.indexOf("; rel=\"next\"");
                        link = link.substring(0, endIndex);
                        nextLink = link.substring(link.lastIndexOf('<') + 1, link.lastIndexOf('>'));
                        break;
                    }
                }
            }
        }
        if (nextLink != null) {
            request.setUrlString(nextLink);
            getData(request);
        }
        return data;
    }

    protected abstract void parseResponse(HttpResponse response) throws ApplicationException;

    public static void main(String[] args) throws ApplicationException {
        AbstractDataFetcher<Map<String, Integer>> dataFetcher = new RepoDetailFetcher();
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUrlString("https://api.github.com/search/commits?q=committer%3Asuranjay&per_page=100");
        httpRequest.setMethodType(MethodType.GET);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github.cloak-preview");
        httpRequest.setHeaderMap(headerMap);
        Map<String, Integer> data = dataFetcher.getData(httpRequest);
        Integer count = 0;
        for (Integer integer : data.values()) {
            count = count+integer;
        }

        System.out.println(data);
    }

}
