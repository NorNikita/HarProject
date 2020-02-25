package com.pflb.hartask.datamodel.innermodel;

import com.pflb.hartask.datamodel.harmodel.entry.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Request implements Serializable {

    private String url;
    private String body;
    private Map<String, String> headers;
    private Map<String, String> params;
    private HttpMethod method;
    private Double perc;
}
