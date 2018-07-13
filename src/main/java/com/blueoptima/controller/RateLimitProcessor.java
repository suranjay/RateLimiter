package com.blueoptima.controller;

import com.blueoptima.bean.HttpRequest;
import com.blueoptima.bean.InputDetail;
import com.blueoptima.bean.UserDetail;
import com.blueoptima.common.ApplicationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Suranjay on 13/07/18.
 */
public class RateLimitProcessor {

    private InputReader inputReader;

    public RateLimitProcessor(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    public Map<String, Map<String, Integer>> process(String filePath) throws ApplicationException {
        List<InputDetail> inputDetails = inputReader.parseInput(filePath);
        Map<String, Map<String, Integer>> userRepoCommitMap = new HashMap<>();

        for (InputDetail detail : inputDetails) {
            AbstractDataFetcher<List<UserDetail>> dataFetcher1 = new UserDetailFetcher();
            HttpRequest httpRequest = UserUrlCreator.createUserSearchRequest(detail);
            List<UserDetail> data = dataFetcher1.getData(httpRequest);
            System.out.println(data);
            for (UserDetail userDetail : data) {
                AbstractDataFetcher<Map<String, Integer>> repoDetailFetcher = new RepoDetailFetcher();
                httpRequest = CommitSearchUrlCreator.getCommitSearchRequest(userDetail.getLoginId());
                userRepoCommitMap.put(userDetail.getLoginId(), repoDetailFetcher.getData(httpRequest));
            }
        }
        return userRepoCommitMap;

    }
}
