package com.pflb.hartask.datamodel.harmodel.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HarEntries {

    private String pageref;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date startedDateTime;

    private Long time;

    private HarRequest request;
    private HarResponse response;
    private HarCache cache;
    private HarTimings timings;

    private String serverIPAddress;
    private String connection;
    private String comment;

}
