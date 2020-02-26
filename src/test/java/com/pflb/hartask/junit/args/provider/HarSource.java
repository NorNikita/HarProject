package com.pflb.hartask.junit.args.provider;

import com.pflb.hartask.model.harmodel.Har;
import com.pflb.hartask.model.harmodel.entry.*;

import java.util.*;
import java.util.stream.Collectors;

public class HarSource {

    public static Har getHar() {
        Har har = new Har();
        HarLog log = new HarLog();
        log.setVersion("1.5");

        HarBrowser browser = new HarBrowser();
        browser.setName("Firefox");
        log.setBrowser(browser);

        List<HarHeaders> headers = getHeaders().entrySet().stream().map(e -> new HarHeaders(e.getKey(), e.getValue(), "")).collect(Collectors.toList());
        List<HarQueryString> queryStrings = getParams().entrySet().stream().map(q -> new HarQueryString(q.getKey(), q.getValue(), "")).collect(Collectors.toList());

        List<HarEntries> listEntries = new ArrayList<>();

        HarEntries entries1 = new HarEntries();
        entries1.setPageref("page_4");

        HarRequest request1 = new HarRequest();
        request1.setMethod(HttpMethod.GET);
        request1.setUrl("http://some/resourse/id=243");
        request1.setHeaders(headers);

        entries1.setRequest(request1);


        HarEntries entries2 = new HarEntries();
        entries2.setPageref("page_5");

        HarRequest request2 = new HarRequest();
        request2.setMethod(HttpMethod.POST);
        request2.setUrl("http://some/resourse/create");
        request2.setHeaders(headers);
        request2.setQueryString(queryStrings);

        HarPostData harPostData = new HarPostData();
        harPostData.setText("some text");

        request2.setPostData(harPostData);
        entries2.setRequest(request2);

        listEntries.add(entries1);
        listEntries.add(entries2);

        log.setEntries(listEntries);
        har.setLog(log);
        return har;
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put("Host", "some.host.com");
        headers.put("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:72.0) Gecko/20100101 Firefox/72.0");
        headers.put("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        headers.put("Connection", "keep-alive");

        return headers;
    }

    private static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();

        params.put("mimeType", "multipart/form-data");
        params.put("text", "text");

        return params;
    }
}
