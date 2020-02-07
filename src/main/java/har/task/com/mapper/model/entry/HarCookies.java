package har.task.com.mapper.model.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class HarCookies {

    private String name;
    private String value;
    private String path;
    private String domain;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date expires;

    private Boolean httpOnly;
    private Boolean secure;

    private String comment;
}
