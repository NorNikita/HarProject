package com.pflb.hartask.model.harmodel.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class HarCache {

    private HarBeforeRequest beforeRequest;
    private HarAfterRequest afterRequest;
    private String comment;
}
