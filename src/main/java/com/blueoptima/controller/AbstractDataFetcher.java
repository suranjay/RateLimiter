package com.blueoptima.controller;

import com.blueoptima.bean.*;
import com.blueoptima.common.ApplicationException;

import java.util.*;

/**
 * Created by Suranjay on 11/07/18.
 */
public abstract class AbstractDataFetcher<T> {

    protected T data;

    public T getData(HttpRequest request) throws ApplicationException {
        HttpResponse response = DataFetcherWithRateLimit.getInstance().fetchDataFromUrl(request);
        parseResponse(response);
        Map<String, List<String>> headerMap = response.getHeaderMap();
        String nextLink = null;
        if (headerMap != null) {
            List<String> links = headerMap.get("Link");
            if (links != null) {
                for (String link : links) {
                    if (link.contains("; rel=\"next\"")) {
//                    System.out.println(link);
                        int endIndex = link.indexOf("; rel=\"next\"");
                        link = link.substring(0, endIndex);
                        nextLink = link.substring(link.lastIndexOf('<') + 1, link.lastIndexOf('>'));
//                    System.out.println(nextLink);
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
        /*httpRequest.setUrlString("https://api.github.com/search/commits?q=committer%3AKirinDave&per_page=5");
        httpRequest.setMethodType(MethodType.GET);
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Accept", "application/vnd.github.cloak-preview");
        httpRequest.setHeaderMap(headerMap);*/
        InputDetail inputDetail1 = new InputDetail("Chunky", "Garg", "Gurgaon");
        InputDetail inputDetail2 = new InputDetail("Sharvi", "Verma", "India");
        InputDetail inputDetail3 = new InputDetail("Rahul", "Gaur", "India");
        InputDetail inputDetail4= new InputDetail("Abhishek", "Parikh", null);
        InputDetail inputDetail5 = new InputDetail("Anubhav", "Saxena", null);
        InputDetail inputDetail6 = new InputDetail("Shiva", "Tiwari", "Bangalore");
        InputDetail inputDetail7 = new InputDetail("Sumit", "Yadav", "New Delhi");
        InputDetail inputDetail8 = new InputDetail("Varun", "Arora", "India");
        InputDetail inputDetail9 = new InputDetail("Anshika", "Singh", "India");
        InputDetail inputDetail10 = new InputDetail("Salman", "Kagzi", null);
        InputDetail inputDetail11 = new InputDetail("James", "Golick", "New York");
        InputDetail inputDetail12 = new InputDetail("Dave", "Fayram", "San Francisco, CA");
        InputDetail inputDetail13 = new InputDetail("Nishan", "Parera", "London");
        List<InputDetail> details = Arrays.asList(inputDetail1, inputDetail2, inputDetail3, inputDetail4, inputDetail5, inputDetail6, inputDetail7, inputDetail8, inputDetail9, inputDetail10, inputDetail11, inputDetail12, inputDetail13);
        Map<String, Map<String, Integer>> maps = new HashMap<>();

        for (InputDetail detail : details) {


            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setMethodType(MethodType.GET);
            httpRequest.setUrlString(UserUrlCreator.createUserSearchUrl(detail));
            httpRequest.setRequestType(HttpRequest.RequestType.SEARCH);
            AbstractDataFetcher<List<UserDetail>> dataFetcher1 = new UserDetailFetcher();
            List<UserDetail> data = dataFetcher1.getData(httpRequest);
            System.out.println(data);
            for (UserDetail userDetail : data) {
                AbstractDataFetcher<Map<String, Integer>> repoDetailFetcher = new RepoDetailFetcher();
                httpRequest = new HttpRequest();
                httpRequest.setUrlString("https://api.github.com/search/commits?q=committer%3AUSERNAME&per_page=100".replace("USERNAME", userDetail.getLoginId()));
                httpRequest.setMethodType(MethodType.GET);
                httpRequest.setRequestType(HttpRequest.RequestType.SEARCH);
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Accept", "application/vnd.github.cloak-preview");
                httpRequest.setHeaderMap(headerMap);
                maps.put(userDetail.getLoginId(), repoDetailFetcher.getData(httpRequest));
            }
        }
        System.out.println(maps);
    }

}
