package com.blueoptima.controller;

import com.blueoptima.bean.HttpResponse;
import com.blueoptima.common.ApplicationException;
import com.blueoptima.util.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suranjay on 11/07/18.
 */
public class RepoDetailFetcher extends AbstractDataFetcher<Map<String, Integer>> {

    private Long totalCount;

    public RepoDetailFetcher() {
        super();
        data = new HashMap<>();
    }

    @Override
    protected void parseResponse(HttpResponse response) throws ApplicationException {
        if (totalCount == null) {
            totalCount = JsonReader.<Long>getValueFromJson(response.getBody(), "total_count");
        }
        JSONArray items = JsonReader.getJsonArrayFromJson(response.getBody(), "items");
        items.stream().forEach(item-> {
            String o = (String) ((JSONObject) ((JSONObject) item).get("repository")).get("full_name");
            Integer count = data.get(o);
            if (count == null) {
                data.put(o, 1);
            } else {
                data.put(o, ++count);

            }
        });
    }

    public Long getTotalCount() {
        return totalCount;
    }
}
