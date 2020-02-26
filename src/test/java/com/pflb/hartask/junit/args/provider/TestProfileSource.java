package com.pflb.hartask.junit.args.provider;

import com.pflb.hartask.model.harmodel.entry.HttpMethod;
import com.pflb.hartask.model.innermodel.Request;
import com.pflb.hartask.model.innermodel.TestProfile;

import java.util.*;

public class TestProfileSource {

    public static TestProfile getTestProfile() {
        TestProfile testProfile = new TestProfile();
        List<Request> requests = new ArrayList<>();

        Request request1 = new Request("/some/url", null, getHeaders(), getParams(), HttpMethod.GET, 0.0);
        Request request2 = new Request("/some/url", "some body", getHeaders(), getParams(), HttpMethod.POST, 0.0);
        requests.add(request1);
        requests.add(request2);

        testProfile.setRequests(requests);
        return testProfile;
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put("Host", "some.host.com");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:72.0) Gecko/20100101 Firefox/72.0");
        headers.put("Accept", "text/css,*/*;q=0.1");
        headers.put("Connection", "keep-alive");

        return headers;
    }

    private static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();

        params.put("mimeType", "multipart/form-data");
        params.put("text", "some random text");

        return params;
    }
}
