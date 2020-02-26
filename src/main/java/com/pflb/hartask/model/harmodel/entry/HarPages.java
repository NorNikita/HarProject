package com.pflb.hartask.model.harmodel.entry;

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
class HarPages {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date startedDateTime;

    private String id;
    private String title;
    private HarPageTimings pageTimings;
    private String comment;
}
