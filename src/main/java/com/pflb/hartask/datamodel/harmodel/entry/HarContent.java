package com.pflb.hartask.datamodel.harmodel.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class HarContent {

    private Long size;
    private Long compression;
    private String mimeType;
    private String text;
    private String comment;
}
