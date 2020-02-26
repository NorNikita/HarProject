package com.pflb.hartask.model.harmodel.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class HarResponse {

    private Long status;
    private String statusText;
    private String httpVersion;
    private List<HarCookies> cookies = new ArrayList<>();
    private List<HarHeaders> headers = new ArrayList<>();
    private HarContent content;
    private String redirectURL;
    private Long headersSize;
    private Long bodySize;
    private String comment;
}
