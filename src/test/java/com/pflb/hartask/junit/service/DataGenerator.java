package com.pflb.hartask.junit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pflb.hartask.datamodel.harmodel.Har;
import com.pflb.hartask.datamodel.harmodel.entry.*;
import com.pflb.hartask.datamodel.innermodel.Request;
import com.pflb.hartask.datamodel.innermodel.TestProfile;
import com.pflb.hartask.entity.HarFile;
import com.pflb.hartask.entity.InnerModelData;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class DataGenerator {

    private static ObjectMapper mapper = new ObjectMapper();

    static HarFile getHarFile(Har har) {
        HarFile harFile = null;

        try {
            harFile = new HarFile(har.getLog().getBrowser() == null ? null : har.getLog().getBrowser().getName(), har.getLog().getVersion(), mapper.writeValueAsString(har));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return harFile;
    }

    static TestProfile getTestProfile() {
        TestProfile testProfile = new TestProfile();
        List<Request> requests = new ArrayList<>();

        Request request1 = new Request("/some/url", null, getHeaders(), getParams(), HttpMethod.GET, 0.0);
        Request request2 = new Request("/some/url", UUID.randomUUID().toString(), getHeaders(), getParams(), HttpMethod.POST, 0.0);
        requests.add(request1);
        requests.add(request2);

        testProfile.setRequests(requests);
        return testProfile;
    }

    static InnerModelData getInnerModelData(TestProfile testProfile) {
        InnerModelData innerModelData = null;

        try {
            innerModelData = new InnerModelData((long) testProfile.getRequests().size(), mapper.writeValueAsString(testProfile));
        } catch(IOException e) {
            e.printStackTrace();
        }

        return innerModelData;
    }

    static Har generateHar() {
        Har har = new Har();
        HarLog log = new HarLog();
        log.setVersion("1.5");

        HarBrowser browser = (int)(Math.random()*10) % 2 == 0 ? new HarBrowser() : null;
        if(browser != null) {
            browser.setName("Firefox");
            log.setBrowser(browser);
        }

        List<HarHeaders> headers = getHeaders().entrySet().stream().map(e -> new HarHeaders(e.getKey(), e.getValue(), "")).collect(Collectors.toList());
        List<HarQueryString> queryStrings = getParams().entrySet().stream().map(q -> new HarQueryString(q.getKey(), q.getValue(), "")).collect(Collectors.toList());

        List<HarEntries> listEntries = new ArrayList<>();

        HarEntries entries1 = new HarEntries();
        entries1.setPageref("page_1");
        entries1.setStartedDateTime(new Date(System.currentTimeMillis()));

        HarRequest request1 = new HarRequest();
        request1.setMethod(HttpMethod.GET);
        request1.setUrl("http://some/resourse/id=243");
        request1.setHeaders(headers);

        entries1.setRequest(request1);


        HarEntries entries2 = new HarEntries();
        entries2.setPageref("page_2");
        entries2.setStartedDateTime(new Date(System.currentTimeMillis()));

        HarRequest request2 = new HarRequest();
        request2.setMethod(HttpMethod.POST);
        request2.setUrl("http://some/resourse/create");
        request2.setHeaders(headers);
        request2.setQueryString(queryStrings);

        HarPostData harPostData = new HarPostData();
        harPostData.setText(UUID.randomUUID().toString());

        request2.setPostData(harPostData);
        entries2.setRequest(request2);

        listEntries.add(entries1);
        listEntries.add(entries2);

        log.setEntries(listEntries);
        har.setLog(log);
        return har;
    }

    static Long randomLong() {
        return (long) (Math.random() * 100000 + 4);
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put("Host", "some.host.com");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:72.0) Gecko/20100101 Firefox/72.0");
        headers.put("Accept", "text/css,*/*;q=0.1");
        headers.put("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        headers.put("Connection", "keep-alive");

        return headers;
    }

    private static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();

        params.put("mimeType", "multipart/form-data");
        params.put("text", UUID.randomUUID().toString());

        return params;
    }


}
